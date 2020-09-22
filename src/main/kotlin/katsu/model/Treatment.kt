package katsu.model

import java.time.LocalDateTime

data class Treatment(
        val number: Int,
        var date: LocalDateTime,
        var note: String
) : Comparable<Treatment> {
    override fun compareTo(other: Treatment) = other.number - number

    val isNew = number == -1

    companion object {
        fun prototype() = Treatment(
                number = -1,
                date = LocalDateTime.now(),
                note = ""
        )
    }
}
