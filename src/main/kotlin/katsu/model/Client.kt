package katsu.model

import java.util.*

data class Client(
        val id: UUID,
        var firstName: String,
        val treatments: MutableList<Treatment>,
        var text: String
) {
    companion object {
        fun prototype() = Client(
                id = UUID.randomUUID(),
                firstName = "",
                treatments = arrayListOf(),
                text = ""
        )
    }
}
