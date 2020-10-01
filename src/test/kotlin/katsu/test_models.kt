package katsu

import katsu.logic.Data
import katsu.model.Client
import katsu.model.ClientCategory
import katsu.model.Picture
import katsu.model.Treatment
import java.time.LocalDate
import java.util.UUID

fun Data.Companion.fullInstance() = Data(
        clients = listOf(
                Client.fullInstance()
        )
)

fun Client.Companion.fullInstance() = Client(
        id = UUID.fromString("ae4903fd-dedf-4c58-8555-5e7ccaf5dc52"),
        firstName = "first",
        treatments = mutableListOf(
                Treatment.fullInstance()
        ),
        note = "client note",
        category = ClientCategory.Normal,
        picture = Picture.DefaultPicture,
)

fun Treatment.Companion.fullInstance() = Treatment(
        number = 1,
        date = LocalDate.parse("2020-09-25"),
        note = "treatment note"
)