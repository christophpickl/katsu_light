package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Model
import katsu.view.ClientDetailPanel
import katsu.view.ClientSaveRequestEvent
import mu.KotlinLogging.logger
import javax.swing.JOptionPane

class ClientCrudController(
        bus: EventBus,
        private val clientDetailPanel: ClientDetailPanel,
        private val model: Model
) {

    private val log = logger {}

    init {
        bus.register(this)
    }

    @Subscribe
    fun onClientSaveRequestEvent(event: ClientSaveRequestEvent) {
        log.debug { "onClientSaveRequestEvent" }
        if (clientDetailPanel.inpFirstName.text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "First name must not be empty!")
            return
        }

        model.currentClient.firstName = clientDetailPanel.inpFirstName.text
        model.currentClient.text = clientDetailPanel.inpText.text

        if(model.isCurrentClientUnsaved) {
            model.addCurrentClient()
        }


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
