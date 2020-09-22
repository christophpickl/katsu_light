package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.logic.DataLoader
import katsu.model.Client
import katsu.view.ClientSaveEvent
import katsu.view.ClientSavedEvent
import katsu.view.ClientSelectedEvent
import katsu.view.ClientsLoadedEvent
import katsu.view.MainWindow
import mu.KotlinLogging.logger
import java.util.*
import javax.swing.JOptionPane

class MainController(
        private val bus: EventBus,
        private val dataLoader: DataLoader,
        private val mainWindow: MainWindow
) {
    private val log = logger {}
    private val data = TemporaryData()
    init {
        bus.register(this)
    }

    fun show() {
        val clients = dataLoader.load().clients
        data.load(clients)
        bus.post(ClientsLoadedEvent(clients))
        mainWindow.prepareAndShow()
    }

    @Subscribe
    fun onClientSaveEvent(event: ClientSaveEvent) {
        val client = event.client
        if (client.firstName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "First name must not be empty!")
            return
        }
        val clientToPost = if (client.isUnsaved) {
            val client2 = client.copy(id = UUID.randomUUID())
            data.add(client2)
            client2
        } else {
            data.update(client)
            client
        }
        bus.post(ClientSavedEvent(clientToPost, wasCreated = client.isUnsaved))
    }
}


private class TemporaryData {
    private val clientsById = mutableMapOf<UUID, Client>()

    fun load(clients: List<Client>) {
        clientsById.putAll(clients.map { it.id to it })
    }

    fun add(client: Client) {
        require(!client.isUnsaved)
        require(clientsById[client.id] == null)
        clientsById[client.id] = client
    }

    fun update(client: Client) {
        require(clientsById[client.id] != null)
        clientsById[client.id] = client
    }
}
