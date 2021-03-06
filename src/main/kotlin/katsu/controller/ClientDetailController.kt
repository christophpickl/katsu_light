package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Client
import katsu.model.CurrentClientModelEvent
import katsu.model.Model
import katsu.view.ClientCreateRequestUIEvent
import katsu.view.JClientDetail
import mu.KotlinLogging.logger

class ClientDetailController(
        bus: EventBus,
        private val model: Model,
        private val clientDetail: JClientDetail) {

    private val log = logger {}
    init {
        bus.register(this)
    }

    @Subscribe
    fun onCurrentClientChangedEvent(event: CurrentClientModelEvent) {
        log.trace { "on $event" }
        clientDetail.updateUi(model.currentClient)
    }

    @Subscribe
    fun onClientCreateEvent(event: ClientCreateRequestUIEvent) {
        log.trace { "on $event" }
        model.currentClient = Client.prototype()
    }
}