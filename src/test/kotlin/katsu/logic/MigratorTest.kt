package katsu.logic

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.testng.annotations.Test

@Test
class MigratorTest {

    private val jackson = jacksonObjectMapper()

    fun `When migrate 2 Then add dummy newField`() {
        val json = jackson.readTree("""{"version":1,"clients":[{}]}""") as ObjectNode

        Migrator.migrate(1, 2, json)

        assertThat(json["version"].intValue()).isEqualTo(2)
        assertThat((json["clients"] as ArrayNode)[0]["newField"].textValue()).isEqualTo("")
    }
}
