package katsu

import katsu.controller.ClientCrudController
import katsu.controller.MainController
import mu.KotlinLogging.logger
import org.kodein.di.Kodein
import org.kodein.di.generic.instance
import javax.swing.SwingUtilities

object Katsu {

    private val log = logger {}

    @JvmStatic
    fun main(args: Array<String>) {
        log.info { "Katsu main entry point running." }
        val env = Env.loadCurrent()
        log.info { "Starting kodein with environment: $env (v${MetaInfo.properties.appVersion})" }
        log.info { "=============================================" }

        val kodein = Kodein {
            import(applicationKodein(env))
        }
        val starter by kodein.instance<ApplicationStarter>()
        log.debug { "Starting application" }
        starter.startUp()
    }
}

class ApplicationStarter(
        private val mainController: MainController,
        private val crudController: ClientCrudController
) {

    private val log = logger {}

    fun startUp() {
        SwingUtilities.invokeLater {
            mainController.show()
        }
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {
                log.info { "Shutdown hook running." }
                crudController.saveCurrentClient()
                mainController.persistData()
            }
        })
    }
}
