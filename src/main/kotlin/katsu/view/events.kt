package katsu.view

import katsu.model.Client
import katsu.model.Treatment

class ClientsLoadedEvent

data class ClientSelectedEvent(val client: Client)

class ClientCreateEvent

class ClientSaveRequestEvent

class ClientSavedEvent(val client: Client, val wasCreated: Boolean)

data class TreatmentSelectedEvent(val treatment: Treatment)
