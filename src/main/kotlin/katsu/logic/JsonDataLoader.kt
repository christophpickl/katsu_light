package katsu.logic

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import mu.KotlinLogging
import mu.KotlinLogging.logger
import java.io.File

class JsonDataLoader(
        private val targetFile: File
) : DataLoader {

    private val log = logger {}
    private val currentVersion = 1
    private val jackson = jacksonObjectMapper().apply {
        registerModule(JavaTimeModule())
    }

    override fun load(): Data {
        if(!targetFile.exists()) {
            log.info { "No data file existing (at ${targetFile.absolutePath}), starting from scratch." }
            return Data(
                    clients = emptyList()
            )
        }

        log.info { "Loading data JSON from: ${targetFile.absolutePath}" }
        val jsonTxt = targetFile.readText()
        val fileVersion = jackson.readTree(jsonTxt)["version"].intValue()
        if (fileVersion == currentVersion) {
            return jackson.readValue<JsonVersionedData>(jsonTxt).data
        }

        log.info { "Migrating from v${fileVersion} to v${currentVersion} ..." }
        // FIXME migrate

        return jackson.readValue<JsonVersionedData>(targetFile.readText()).data
    }

    override fun save(data: Data) {
        log.info { "Saving data JSON to: ${targetFile.absolutePath}" }
        if(!targetFile.parentFile.exists()) {
            log.debug { "Creating directory at: ${targetFile.parentFile.absolutePath}" }
            targetFile.parentFile.mkdirs()
        }
        targetFile.writeText(jackson.writeValueAsString(JsonVersionedData(
                version = currentVersion,
                data = data
        )))
    }

}

data class JsonVersionedData(
        val version: Int,
        val data: Data
)
