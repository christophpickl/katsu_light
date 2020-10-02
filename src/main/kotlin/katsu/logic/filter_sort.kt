package katsu.logic

import katsu.model.Client

class FilterAndSort {

    var showInactiveClients = false
        private set

    fun toggleShowInactiveClients() {
        showInactiveClients = !showInactiveClients
    }

    fun filter(allClients: List<Client>): List<Client> {
        return allClients.let { clients ->
            if (showInactiveClients) clients else clients.filter { it.active }
        }
    }
}