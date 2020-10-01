package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Model
import katsu.view.ClientSaveRequestUIEvent
import katsu.view.ClientSelectedUIEvent
import katsu.view.ClosingUIEvent
import katsu.view.JClientDetail
import mu.KotlinLogging.logger

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
