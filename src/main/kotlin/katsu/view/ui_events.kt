package katsu.view

import katsu.model.Client
import katsu.model.Treatment

class ClientsLoadedEvent

data class ClientSelectedUIEvent(val client: Client)

class ClientCreateRequestUIEvent

class ClientSaveRequestUIEvent

data class TreatmentSelectedUIEvent(val treatment: Treatment)