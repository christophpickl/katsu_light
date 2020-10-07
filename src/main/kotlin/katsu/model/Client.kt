package katsu.model

import katsu.logic.Images
import java.awt.Image
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.swing.ImageIcon

data class Client(
        val id: UUID,
        var firstName: String,
        val treatments: MutableList<Treatment>,
        var note: String,
        var birthday: LocalDate?,
        var category: ClientCategory,
        var active: Boolean,
        var picture: Picture,
) {
    companion object {
        val birthdayFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("d.M.yyyy")

        fun prototype() = Client(
                id = UUID.randomUUID(),
                firstName = "",
                treatments = arrayListOf(),
                note = """
                    FACTS:
                    * Origin/Living: 
                    * Marital Status/Children: 
                    * Work: 
                    * Hobbies: 
                    * Main Issues: 
                    * Expectations: 
                    
                    MEDICAL (complaints, accidents, injuries, treatments):
                    * 
                    
                    TCM (temp, eat, sleep, digest, mens):
                    * 
                    
                    LIFESTYLE (drugs, sports, personality, work, family, friends, love):
                    * 
                    
                    BO SHIN (constituion, posture, behavior, voice, skin, face):
                    * 
                """.trimIndent(),
                category = ClientCategory.Normal,
                picture = Picture.DefaultPicture,
                birthday = null,
                active = true,
        )
    }

    fun nextTreatment() = Treatment.prototype().copy(
            number = treatments.size + 1
    )

    val birthdayFormatted get() = birthday?.let { birthdayFormat.format(it) }

    private val lazyString by lazy {
        "Client[$firstName, treatments: ${treatments.size}, birthday: $birthday]"
    }

    override fun toString() = lazyString
}

sealed class Picture(
        val imageIcon: ImageIcon
) {

    val image: Image = imageIcon.image

    object DefaultPicture : Picture(Images.load("profile_pic_default.png"))

    class ImageIconPicture(imageIcon: ImageIcon) : Picture(imageIcon)
}

enum class ClientCategory(
        val label: String,
        val jsonValue: String,
        val image: ClientCategoryImage,
) {
    High("High", "high", ClientCategoryImage.Defined(Images.load("clientcategory_high.png"))),
    Normal("Normal", "normal", ClientCategoryImage.None),
    Low("Low", "low", ClientCategoryImage.Defined(Images.load("clientcategory_low.png"))),
    ;

    companion object {
        private val categoryByJsonValue by lazy { values().associateBy { it.jsonValue } }
        fun byJsonValue(value: String) = categoryByJsonValue[value] ?: error("Invalid JSON value: '$value'!")
    }
}

sealed class ClientCategoryImage {
    object None : ClientCategoryImage()
    class Defined(val imageIcon: ImageIcon) : ClientCategoryImage()
}
