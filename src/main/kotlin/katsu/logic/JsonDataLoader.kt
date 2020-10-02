package katsu.logic

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import katsu.model.Client
import katsu.model.ClientCategory
import katsu.model.Picture
import katsu.model.Treatment
import mu.KotlinLogging.logger
import java.io.File
import java.time.LocalDate
import java.util.UUID

class JsonDataLoader(
        private val targetFile: File,
        private val pictureService: PictureService
) : DataLoader {

    private val log = logger {}
    private val jackson = jacksonObjectMapper().apply {
        registerModule(JavaTimeModule())
        disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    override fun load(): Data {
        if (!targetFile.exists()) {
            log.info { "No data file existing (at ${targetFile.absolutePath}), starting from scratch." }
            return Data(clients = emptyList())
        }
        log.info { "Loading data JSON from: ${targetFile.absolutePath}" }
        val jsonTxt = targetFile.readText()
        val jsonTree = jackson.readTree(jsonTxt)
        val fileVersion = jsonTree["version"].intValue()
        if (fileVersion == Migrator.currentVersion) {
            log.debug { "Most current data v$fileVersion stored, no migration needed." }
            return jackson.readData(jsonTxt).enhancePictures()
        }
        migrate(fileVersion, jsonTree)
        return jackson.readData(targetFile.readText()).enhancePictures()
    }

    private fun migrate(fileVersion: Int, jsonTree: JsonNode) {
        log.info { "Migrating from v${fileVersion} to v${Migrator.currentVersion} ..." }
        Migrator.migrate(fileVersion, Migrator.currentVersion, jsonTree as ObjectNode)
        targetFile.writeText(jsonTree.toString())
    }

    override fun save(data: Data) {
        log.info { "Saving data JSON to: ${targetFile.absolutePath}" }
        if (!targetFile.parentFile.exists()) {
            log.debug { "Creating directory at: ${targetFile.parentFile.absolutePath}" }
            targetFile.parentFile.mkdirs()
        }
        targetFile.writeText(jackson.writeValueAsString(JsonVersionedData(
                version = Migrator.currentVersion,
                clients = data.clients.map { it.toClientJson() }.sortedBy { it.firstName }
        )))
    }

    private fun Data.enhancePictures() = apply {
        clients.forEach { client ->
            client.picture = pictureService.load(client)
        }
    }
}


private fun ObjectMapper.readData(json: String) = readValue<JsonVersionedData>(json).toData()

private fun Client.toClientJson() = ClientJson(
        id = id.toString(),
        firstName = firstName,
        note = note,
        treatments = treatments.map { it.toTreatmentJson() }.toMutableList(),
        category = category.jsonValue,
        active = active,
)

private fun Treatment.toTreatmentJson() = TreatmentJson(
        number = number,
        date = date,
        note = note,
)

private data class JsonVersionedData(
        val version: Int,
        val clients: List<ClientJson>,
) {
    fun toData() = Data(
            clients = clients.map { it.toClient() }
    )
}

private data class ClientJson(
        val id: String,
        val firstName: String,
        val note: String,
        val category: String,
        val active: Boolean,
        val treatments: MutableList<TreatmentJson>,
) {
    fun toClient() = Client(
            id = UUID.fromString(id),
            firstName = firstName,
            note = note,
            treatments = treatments.map { it.toTreatment() }.toMutableList(),
            category = ClientCategory.byJsonValue(category),
            picture = Picture.DefaultPicture,
            active = active
    )
}

private data class TreatmentJson(
        val number: Int,
        val date: LocalDate,
        val note: String,
) {
    fun toTreatment() = Treatment(
            number = number,
            date = date,
            note = note,
    )
}
