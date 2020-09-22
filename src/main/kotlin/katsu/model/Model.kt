package katsu.model

import com.google.common.eventbus.EventBus
import kotlin.properties.Delegates

//data class Model(
//     val clients: MutableList<Client>,
//     var currentClient: Client?,
//     var currentTreatment: Treatment?
//) {
//
//    private val log = logger {}
//
//    fun reset(newClients: MutableList<Client>) {
//        log.debug { "reset($newClients)" }
//        clients.clear()
//        clients.addAll(newClients)
//    }
//}

class Model(
        private val bus: EventBus,
        clients: MutableList<Client>,
        currentClient: Client,
        currentTreatment: Treatment?
) {

    val isCurrentClientUnsaved: Boolean get() = !_clients.contains(currentClient)

    val clients: List<Client> get() = _clients

    fun addCurrentClient() {
        // TODO calculate proper index based on firstname
        val position = 0
        _clients.add(position, currentClient)
        bus.post(ClientAddedModelEvent(currentClient, position))
    }

    fun load(clients: List<Client>) {
        _clients.addAll(clients)
    }

    private val _clients: MutableList<Client> = clients

    var currentClient: Client by Delegates.observable(currentClient) { _, old, new ->
        println("currentClient changed from $old to $new")
        bus.post(CurrentClientModelEvent(new))
    }

    var currentTreatment: Treatment? by Delegates.observable(currentTreatment) { _, old, new ->
        println("currentTreatment changed from $old to $new")
    }
}

data class CurrentClientModelEvent(
        val currentClient: Client
)
data class ClientAddedModelEvent(
        val client: Client,
        val position: Int
)
