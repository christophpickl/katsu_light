package katsu

import katsu.controller.MainController
import katsu.logic.DataLoader
import katsu.logic.InMemoryDataLoader
import mu.KotlinLogging
import mu.KotlinLogging.logger
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

object Katsu {

    private val log = logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Katsu starting up ..." }
        val kodein = Kodein {
            import(applicationKodein())
        }
        val starter by kodein.instance<ApplicationStarter>()
        starter.startUp()
    }
}

class ApplicationStarter(
        private val mainController: MainController
) {
    fun startUp() {
        mainController.show()
    }
}
