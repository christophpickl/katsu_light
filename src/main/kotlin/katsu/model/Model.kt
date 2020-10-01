package katsu.model

import com.google.common.eventbus.EventBus
import mu.KotlinLogging.logger
import kotlin.properties.Delegates

class Model(
        private val bus: EventBus,
        clients: MutableList<Client>,
        currentClient: Client,
        currentTreatment: Treatment
) {

    companion object {
        fun calculateAddPosition(names: List<String>, search: String): Int {
            if (names.isEmpty()) {
                return 0
            }
            var found = false
            var pos = 0
            do {
                val currentName = names[pos]
                println("$currentName < $search === ${currentName < search}")
                if (currentName > search) {
                    found = true
                } else {
                    pos++
                }
            } while (!found && (pos < names.size))
            return pos
        }
    }

    private val log = logger {}
    private val _clients: MutableList<Client> = clients
    val isCurrentClientUnsaved: Boolean get() = !_clients.contains(currentClient)

    val clients: List<Client> get() = _clients

    fun addCurrentClient() {
        val position = calculateAddPosition(_clients.map { it.firstName }, currentClient.firstName)
        _clients.add(position, currentClient)
        bus.post(ClientAddedModelEvent(currentClient, position))
    }

    fun load(clients: List<Client>) {
        _clients.addAll(clients)
    }

    fun delete(client: Client) {
        if (!_clients.remove(client)) error("Client not existing in model: $client!")
    }

    var currentClient: Client by Delegates.observable(currentClient) { _, old, new ->
        log.info { "currentClient changed from $old to $new" }
        bus.post(CurrentClientModelEvent(new))
    }

    var currentTreatment: Treatment by Delegates.observable(currentTreatment) { _, old, new ->
        log.info { "currentTreatment changed from $old to $new" }
        bus.post(CurrentTreatmentModelEvent(new))

    }
}
