package katsu.logic

import katsu.model.Client
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
                            firstName = "Max"
                    ),
                    Client(
                            id = UUID.fromString("00000000-0000-0000-0000-000000000002"),
                            firstName = "Anna"
                    )
            )
    )

    override fun save(data: Data) {

    }

}

data class Data(
        val version: Int,
        val clients: List<Client>
)