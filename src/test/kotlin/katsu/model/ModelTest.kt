package katsu.model

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

@Test
class ModelTest {
    @DataProvider
    fun `provide params`() = arrayOf(
            arrayOf(emptyList<String>(), "x", 0),
            arrayOf(listOf("a"), "x", 1),
            arrayOf(listOf("x"), "a", 0),
            arrayOf(listOf("a", "c"), "b", 1),
    )

    @Test(dataProvider = "provide params")
    fun `calculate position`(strings: List<String>, search: String, expectedPosition: Int) {
        assertThat(Model.calculateAddPosition(strings, search)).isEqualTo(expectedPosition)
    }
}