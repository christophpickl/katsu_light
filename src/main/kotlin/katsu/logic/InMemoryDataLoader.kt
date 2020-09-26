package katsu.logic

import katsu.model.Client
import katsu.model.Treatment
import java.time.LocalDate
import java.util.*

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
                    ),
                    Client(
                            id = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                            firstName = "Anna",
                            note = "hektisch; schulterbeschwerde",
                            treatments = arrayListOf(),
                    ),
            ),
    )

    override fun save(data: Data) {
        // no op
    }

}