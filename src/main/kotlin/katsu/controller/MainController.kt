package katsu.controller

import com.google.common.eventbus.EventBus
import katsu.logic.Data
import katsu.logic.DataLoader
import katsu.model.Model
import katsu.view.ClientsLoadedEvent
import katsu.view.JMainWindow
import mu.KotlinLogging.logger

class MainController(
        private val bus: EventBus,
        private val model: Model,
        private val dataLoader: DataLoader,
        private val mainWindow: JMainWindow
) {
    private val log = logger {}

    init {
        bus.register(this)
    }

    fun show() {
        log.debug { "show()" }
        val clients = dataLoader.load().clients
        model.load(clients)
        bus.post(ClientsLoadedEvent())

        mainWindow.prepareAndShow()
    }

    fun persistData() {
        log.info { "persistData()" }
        dataLoader.save(Data(
                clients = model.clients
        ))
    }
}
