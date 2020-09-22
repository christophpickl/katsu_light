package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Client
import katsu.model.CurrentClientModelEvent
import katsu.model.Model
import katsu.view.ClientCreateEvent
import katsu.view.ClientDetailPanel
import katsu.view.ClientSaveEvent
import javax.swing.JOptionPane

class ClientDetailController(
        bus: EventBus,
        private val model: Model,
        private val clientDetailPanel: ClientDetailPanel) {
    init {
        bus.register(this)
    }

    @Subscribe
    fun onCurrentClientChangedEvent(@Suppress("unused") event: CurrentClientModelEvent) {
        clientDetailPanel.updateUi(model.currentClient)
        clientDetailPanel.inpFirstName.requestFocus()
    }

    @Subscribe
    fun onClientCreateEvent(event: ClientCreateEvent) {
        model.currentClient = Client.prototype()
    }

    @Subscribe
    fun onClientSaveEvent(event: ClientSaveEvent) {
        if (clientDetailPanel.inpFirstName.text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "First name must not be empty!")
            return
        }

        model.currentClient.firstName = clientDetailPanel.inpFirstName.text

        model.addCurrentClient()

//        val client = event.client
//        val clientToPost = if (client.isUnsaved) {
//            val client2 = client.copy(id = UUID.randomUUID())
//            data.add(client2)
//            client2
//        } else {
//            data.update(client)
//            client
//        }
//        bus.post(ClientSavedEvent(clientToPost, wasCreated = client.isUnsaved))
    }
}