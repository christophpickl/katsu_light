package katsu.model

import java.util.*

data class Client(
        val id: UUID,
        var firstName: String,
        val treatments: MutableList<Treatment>,
        var text: String
) {
//    val isUnsaved = id == NO_ID

    companion object {
        fun prototype() = Client(
                id = NO_ID,
                firstName = "",
                treatments = arrayListOf(),
                text = ""
        )

        val NO_ID: UUID = UUID.fromString("00000000-0000-0000-0000-000000000000")
    }
}
