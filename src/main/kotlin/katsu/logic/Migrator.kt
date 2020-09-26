package katsu.logic

import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import mu.KotlinLogging.logger

private class MigrationStep(
        val migrationCode: (ObjectNode) -> Unit
)

object Migrator {

    const val currentVersion = 1

    private val log = logger {}
    private val steps = mapOf(
            2 to MigrationStep { json ->
                log.debug { "Step 2: adding new field to client" }
                (json["clients"] as ArrayNode).forEach { client ->
                    (client as ObjectNode).put("newField", "")
                }
            }
    )

    fun migrate(fromVersion: Int, toVersion: Int, json: ObjectNode) {
        require(fromVersion > 0)
        require(fromVersion < toVersion)
        require(toVersion <= steps.size + 1)
        log.info { "Migrating from $fromVersion to $toVersion ..." }
        json.put("version", toVersion)

        (fromVersion + 1).rangeTo(toVersion).forEach {
            log.debug { "Executing migration step ${it - fromVersion} / ${toVersion - fromVersion}" }
            (steps[it] ?: error("Invalid migration step: $it!")).migrationCode(json)
        }

    }

}