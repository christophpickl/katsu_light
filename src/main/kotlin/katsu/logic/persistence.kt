package katsu.logic

import katsu.model.Client
import katsu.model.Treatment
import java.time.LocalDateTime
import java.util.*

interface DataLoader {
    fun load(): Data
    fun save(data: Data)
}

class InMemoryDataLoader : DataLoader {
    override fun load() = Data(
            version = 99,
            clients = listOf(
                    Client(
                            id = UUID.fromString("00000000-0000-0000-0000-000000000001"),
                            firstName = "Max",
                            text = "super ruhiger typ",
                            treatments = arrayListOf(
                                    Treatment(2, LocalDateTime.now().minusDays(1), "fuehlt sich gut"),
                                    Treatment(1, LocalDateTime.now().minusDays(2), "hab ich Bl und Due gemacht"),
                            ),
                    ),
                    Client(
                            id = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                            firstName = "Anna",
                            text = "hektisch; schulterbeschwerde",
                            treatments = arrayListOf(),
                    ),
            ),
    )

    override fun save(data: Data) {
        // no op
    }

}

data class Data(
        val version: Int,
        val clients: List<Client>
)