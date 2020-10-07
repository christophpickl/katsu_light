package katsu.logic

import katsu.model.Client
import katsu.model.ClientCategory
import katsu.model.Picture
import katsu.model.Treatment
import java.time.LocalDate
import java.util.UUID

class InMemoryDataLoader : DataLoader {
    override fun load() = Data(
            clients = listOf(
                    Client(
                            id = UUID.fromString("00000000-0000-0000-0000-000000000001"),
                            firstName = "Max",
                            note = "super ruhiger typ",
                            treatments = arrayListOf(
                                    Treatment(2, LocalDate.now().minusDays(1), "fuehlt sich gut"),
                                    Treatment(1, LocalDate.now().minusDays(2), "hab ich Bl und Due gemacht"),
                            ),
                            category = ClientCategory.High,
                            picture = Picture.DefaultPicture,
                            active = true,
                            birthday = null,
                    ),
                    Client(
                            id = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                            firstName = "Anna",
                            note = "hektisch; schulterbeschwerde",
                            treatments = arrayListOf(),
                            category = ClientCategory.Normal,
                            picture = Picture.DefaultPicture,
                            active = true,
                            birthday = null,
                    ),
            ),
    )

    override fun save(data: Data) {
        // no op
    }

}