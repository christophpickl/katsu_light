package katsu.model

import java.time.LocalDateTime
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

    val debugString get() = "Client[$firstName, treatments: ${treatments.size}]"

    fun nextTreatment() = Treatment.prototype().copy(
            number = treatments.size + 1
    )
}
