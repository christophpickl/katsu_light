package katsu

import java.util.*

object MetaInfo {
    val properties by lazy {
        val properties = Properties().apply {
            MetaInfo::class.java.getResourceAsStream("/katsu/meta-info.properties").use {
                load(it)
            }
        }
        Meta(
                appVersion = properties["version"] as String,
                env = properties["env"] as String,
        )
    }
}

data class Meta(
        val appVersion: String,
        val env: String
)