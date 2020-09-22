package katsu

import java.io.File
import java.lang.IllegalArgumentException

private fun homeDirectory(child: String) = File(File(System.getProperty("user.home")), child)

enum class Env(
        val systemProperty: String,
        val appDirectory: File
) {
    Dev("dev", homeDirectory(".katsu_light.dev")),
    Prod("prod", homeDirectory(".katsu_light"));

    companion object {
        private const val KEY_ENV = "katsu.env"
        private val DEFAULT_ENV = Dev
        private val mapped by lazy { values().associateBy { it.systemProperty } }
        fun readFromSystemProperties() = fromString(System.getProperty(KEY_ENV, DEFAULT_ENV.systemProperty))
        private fun fromString(search: String) = mapped[search]
                ?: throw IllegalArgumentException("unknown env: '$search'!")
    }
}
