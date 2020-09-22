package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Client
import katsu.model.Model
import katsu.view.ClientCreateEvent
import katsu.view.ClientList
import katsu.view.ClientSelectedEvent
import katsu.view.ClientsLoadedEvent
import mu.KotlinLogging.logger

class ClientListController(
        bus: EventBus,
        private val model: Model,
        private val clientList: ClientList
) {
    private val log = logger {}
    init {
        bus.register(this)
    }

    @Subscribe
    fun onClientsLoadedEvent(@Suppress("unused") event: ClientsLoadedEvent) {
        log.debug { "onClientsLoadedEvent: ${model.clients}" }
        clientList.clientsModel.addAll(model.clients)
    }

    @Subscribe
    fun onClientSelectedEvent(@Suppress("unused") event: ClientSelectedEvent) {
        model.currentClient = event.client
    }

    @Subscribe
    fun onClientCreateEvent(event: ClientCreateEvent) {
        clientList.clearSelection()
    }
}
