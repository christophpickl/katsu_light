package katsu.logic

import katsu.model.Client

interface DataLoader {
    fun load(): Data
    fun save(data: Data)
}

data class Data(
        val clients: List<Client>
) {
    companion object
}
