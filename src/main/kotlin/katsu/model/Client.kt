package katsu.model

import java.util.*

data class Client(
        val id: UUID,
        var firstName: String,
        val treatments: MutableList<Treatment>,
        var note: String,
        var picture: Picture? = null
) {
    companion object {
        fun prototype() = Client(
                id = UUID.randomUUID(),
                firstName = "",
                treatments = arrayListOf(),
                note = ""
        )
    }

    val debugString get() = "Client[$firstName, treatments: ${treatments.size}]"

    fun nextTreatment() = Treatment.prototype().copy(
            number = treatments.size + 1
    )
}

class Picture {
    // AWT (buffered) Image
}
