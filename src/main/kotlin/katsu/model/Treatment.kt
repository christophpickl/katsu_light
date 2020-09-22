package katsu.model

import java.time.LocalDateTime

data class Treatment(
        val number: Int,
        val date: LocalDateTime,
        val text: String
) : Comparable<Treatment> {
    override fun compareTo(other: Treatment) = other.number - number
}
