package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.ClientAddedModelEvent
import katsu.model.Model
import katsu.view.ClientCreateRequestUIEvent
import katsu.view.ClientList
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
    fun onClientsLoadedEvent(event: ClientsLoadedEvent) {
        log.trace { "on $event" }
        clientList.clientsModel.addAll(model.clients)
    }

    @Subscribe
    fun onClientCreateRequestUIEvent(event: ClientCreateRequestUIEvent) {
        log.trace { "on $event" }
        clientList.clearSelection()
    }

    @Subscribe
    fun onClientAddedModelEvent(event: ClientAddedModelEvent) {
        log.trace { "on $event" }
        clientList.clientsModel.add(event.position, event.client)
        clientList.selectionModel.setSelectionInterval(event.position, event.position)
    }

}
