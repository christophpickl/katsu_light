package katsu.model

import java.time.LocalDate

data class Treatment(
        val number: Int,
        var date: LocalDate,
        var note: String
) : Comparable<Treatment> {
    override fun compareTo(other: Treatment) = other.number - number

    val isPrototype = number == -1

    companion object {
        fun prototype() = Treatment(
                number = -1,
                date = LocalDate.now(),
                note = ""
        )
    }
}
