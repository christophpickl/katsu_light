package katsu.model

import java.util.*

data class Client(
        val id: UUID,
        val firstName: String,
        val treatments: List<Treatment>,
        val text: String
) {
    val isUnsaved = id == NO_ID

    companion object {
        val NO_ID: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")
    }
}
