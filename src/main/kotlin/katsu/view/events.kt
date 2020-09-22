package katsu.view

import katsu.model.Client
import katsu.model.Treatment

data class ClientsLoadedEvent(val clients: List<Client>)

data class ClientSelectedEvent(val client: Client)

class ClientCreateEvent

class ClientSaveEvent(val client: Client)

class ClientSavedEvent(val client: Client, val wasCreated: Boolean)

data class TreatmentSelectedEvent(val treatment: Treatment)
