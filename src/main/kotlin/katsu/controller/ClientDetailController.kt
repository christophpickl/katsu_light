package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Client
import katsu.model.CurrentClientModelEvent
import katsu.model.Model
import katsu.view.ClientCreateEvent
import katsu.view.ClientDetailPanel
import mu.KotlinLogging.logger

class ClientDetailController(
        bus: EventBus,
        private val model: Model,
        private val clientDetailPanel: ClientDetailPanel) {

    private val log = logger {}
    init {
        bus.register(this)
    }

    @Subscribe
    fun onCurrentClientChangedEvent(event: CurrentClientModelEvent) {
        log.debug { "onCurrentClientChangedEvent: $event" }
        clientDetailPanel.updateUi(model.currentClient)
        clientDetailPanel.inpFirstName.requestFocus()
    }

    @Subscribe
    fun onClientCreateEvent(event: ClientCreateEvent) {
        log.debug { "onClientCreateEvent" }
        model.currentClient = Client.prototype()
    }

}