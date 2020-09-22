package katsu.model


data class CurrentClientModelEvent(
        val currentClient: Client
)

data class CurrentTreatmentModelEvent(
        val currentTreatment: Treatment
)

data class ClientAddedModelEvent(
        val client: Client,
        val position: Int
)
