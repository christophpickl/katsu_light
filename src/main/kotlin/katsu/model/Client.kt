package katsu.model

import katsu.logic.Images
import java.awt.Image
import java.util.UUID
import javax.swing.ImageIcon

data class Client(
        val id: UUID,
        var firstName: String,
        val treatments: MutableList<Treatment>,
        var note: String,
        var category: ClientCategory,
        var picture: Picture,
) {
    companion object {
        fun prototype() = Client(
                id = UUID.randomUUID(),
                firstName = "",
                treatments = arrayListOf(),
                note = "",
                category = ClientCategory.Normal,
                picture = Picture.DefaultPicture,
        )
    }

    fun nextTreatment() = Treatment.prototype().copy(
            number = treatments.size + 1
    )

    private val lazyString by lazy {
        "Client[$firstName, treatments: ${treatments.size}]"
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
