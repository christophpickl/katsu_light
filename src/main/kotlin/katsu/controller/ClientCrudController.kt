package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Client
import katsu.model.Model
import katsu.service.PictureService
import katsu.view.ClientSaveRequestUIEvent
import katsu.view.ClientSelectedUIEvent
import katsu.view.ClosingUIEvent
import katsu.view.DeleteClientRequestUiEvent
import katsu.view.JClientDetail
import mu.KotlinLogging.logger
import javax.swing.JOptionPane

class ClientCrudController(
        private val bus: EventBus,
        private val model: Model,
        private val clientDetail: JClientDetail,
        private val treatmentCrud: TreatmentCrudController,
        private val pictureService: PictureService
) {

    private val log = logger {}

    init {
        bus.register(this)
    }

    @Subscribe
    fun onClientSelectedUIEvent(event: ClientSelectedUIEvent) {
        log.trace { "on $event" }
        saveCurrentClient()
        model.currentClient = event.client
    }

    @Subscribe
    fun onClientSaveRequestEvent(event: ClientSaveRequestUIEvent) {
        log.trace { "on $event" }
        saveCurrentClient()
    }

    @Subscribe
    fun onClosingUIEvent(event: ClosingUIEvent) {
        log.trace { "on $event" }
        saveCurrentClient()
    }

    @Subscribe
    fun onDeleteClientRequestUiEvent(event: DeleteClientRequestUiEvent) {
        log.trace { "on $event" }
        val client = model.currentClient
        if (JOptionPane.showConfirmDialog(null, "Really delete '${client.firstName}'?", "Confirm", JOptionPane.WARNING_MESSAGE) != JOptionPane.OK_OPTION) {
            return
        }

        // FIXME implement delete
        model.delete(client)
        pictureService.delete(client)
        model.currentClient = Client.prototype()
        bus.post(ClientDeletedEvent(client))
    }

    fun saveCurrentClient() {
        log.info { "saveCurrentClient()" }
        if (model.isCurrentClientUnsaved && clientDetail.inpFirstName.text.isEmpty()) {
            log.debug { "Ignoring unsaved empty client" }
            return
        }

        model.currentClient.firstName = clientDetail.inpFirstName.text
        model.currentClient.note = clientDetail.inpText.text

        treatmentCrud.updateModel()

        if (model.isCurrentClientUnsaved) {
            model.addCurrentClient()
        }
    }
}
