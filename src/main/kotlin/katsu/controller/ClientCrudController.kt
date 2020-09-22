package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Model
import katsu.view.JClientDetail
import katsu.view.ClientSaveRequestUIEvent
import mu.KotlinLogging.logger
import javax.swing.JOptionPane

class ClientCrudController(
        bus: EventBus,
        private val model: Model,
        private val clientDetail: JClientDetail,
        private val treatmentCrud: TreatmentCrudController
) {

    private val log = logger {}

    init {
        bus.register(this)
    }

    @Subscribe
    fun onClientSaveRequestEvent(event: ClientSaveRequestUIEvent) {
        log.debug { "onClientSaveRequestEvent" }
        if (clientDetail.inpFirstName.text.isEmpty()) {
            JOptionPane.showMessageDialog(null, "First name must not be empty!")
            return
        }

        model.currentClient.firstName = clientDetail.inpFirstName.text
        model.currentClient.text = clientDetail.inpText.text

        treatmentCrud.updateModel()

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
