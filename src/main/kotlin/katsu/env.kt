package katsu

import java.io.File

private const val SYSTEM_PROPERTY_ENV = "katsu.env"

private fun homeDirectory(child: String) = File(File(System.getProperty("user.home")), child)

enum class Env(
        val systemProperty: String,
        val appDirectory: File
) {
    Dev("dev", homeDirectory(".katsu.dev")),
    Prod("prod", homeDirectory(".katsu"));

    companion object {
        fun loadCurrent() = fromStringOrNull(MetaInfo.properties.env)
                ?: fromString(System.getProperty(SYSTEM_PROPERTY_ENV, Dev.systemProperty))

        private val mapped by lazy { values().associateBy { it.systemProperty } }

        private fun fromString(search: String) = fromStringOrNull(search)
                ?: throw IllegalArgumentException("unknown env: '$search'!")

        private fun fromStringOrNull(search: String) = mapped[search.toLowerCase()]

    }
}
