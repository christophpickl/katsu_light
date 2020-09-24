package katsu.model

import com.fasterxml.jackson.annotation.JsonIgnore
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

    @get:JsonIgnore val debugString get() = "Client[$firstName, treatments: ${treatments.size}]"

    fun nextTreatment() = Treatment.prototype().copy(
            number = treatments.size + 1
    )
}
