package katsu.model

import java.util.UUID

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


    fun nextTreatment() = Treatment.prototype().copy(
            number = treatments.size + 1
    )

    private val lazyString by lazy {
        "Client[$firstName, treatments: ${treatments.size}]"
    }

    override fun toString() = lazyString
}

class Picture {
    // AWT (buffered) Image
}
