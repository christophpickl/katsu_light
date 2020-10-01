package katsu.model

import java.time.LocalDate

data class Treatment(
        val number: Int,
        var date: LocalDate,
        var note: String
) : Comparable<Treatment> {

    companion object {
        private const val PROTOTYPE_NUMBER = -42

        fun prototype() = Treatment(
                number = PROTOTYPE_NUMBER,
                date = LocalDate.now(),
                note = ""
        )
    }

    val isPrototype = number == PROTOTYPE_NUMBER

    override fun compareTo(other: Treatment) = other.number - number

    private val lazyString by lazy {
        "Treatment[number=$number, date=$date]"
    }

    override fun toString() = lazyString
}
