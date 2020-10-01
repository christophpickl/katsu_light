package katsu.logic

import assertk.assertThat
import assertk.assertions.isEqualTo
import io.mockk.mockk
import katsu.fullInstance
import org.skyscreamer.jsonassert.JSONAssert
import org.testng.annotations.Test
import java.io.File

@Test
class JsonDataLoaderTest {

    private val pictureService = mockk<PictureService>()

    fun `Given no file existing When load Then return empty`() {
        val loader = JsonDataLoader(File("not-existing"), pictureService)

        val model = loader.load()

        assertThat(model).isEqualTo(Data(clients = emptyList()))
    }

    fun `When save Then persist some JSON`() {
        val target = File.createTempFile("katsu", "test")
        val loader = JsonDataLoader(target, pictureService)
        val data = Data.fullInstance()

        loader.save(data)

        JSONAssert.assertEquals(
                """{"version":1,"clients":[{"id":"ae4903fd-dedf-4c58-8555-5e7ccaf5dc52","firstName":"first","note":"client note","category":"normal","treatments":[{"number":1,"date":"2020-09-25","note":"treatment note"}]}]}""",
                target.readText(),
                true
        )
    }
}
