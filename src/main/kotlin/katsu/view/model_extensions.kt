package katsu.view

import katsu.model.Treatment
import java.time.format.DateTimeFormatter

private val dateFormatter = DateTimeFormatter.ofPattern("d.M.")
val Treatment.dateFormatted: String get() = dateFormatter.format(date)

private val dateFormatterLong = DateTimeFormatter.ofPattern("EEE, d.M.yy")
val Treatment.dateFormattedLong: String get() = dateFormatterLong.format(date)
