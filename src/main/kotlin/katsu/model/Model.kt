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

    val clients: List<Client> get() = _clients

    fun addCurrentClient() {
        // TODO calculate proper index based on firstname
        _clients.add(0, currentClient)
        bus.post(ClientAddedModelEvent(currentClient))
    }

    fun load(clients: List<Client>) {
        _clients = clients.toMutableList()
    }

    private var _clients: MutableList<Client> by Delegates.observable(clients) { _, old, new ->
        println("clients changed from $old to $new")
    }

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
        val client: Client
)
