package katsu.model

import katsu.Katsu
import java.awt.Image
import java.util.UUID
import javax.imageio.ImageIO
import javax.swing.ImageIcon

data class Client(
        val id: UUID,
        var firstName: String,
        val treatments: MutableList<Treatment>,
        var note: String,
        var picture: Picture
) {
    companion object {
        fun prototype() = Client(
                id = UUID.randomUUID(),
                firstName = "",
                treatments = arrayListOf(),
                note = "",
                picture = Picture.DefaultPicture
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

    // AWT (buffered) Image
    companion object {
        private fun loadDefaultImage(): ImageIcon {
            val pictureClasspath = "/katsu/images/profile_pic_default.png"
            val imageResource = Katsu::class.java.getResource(pictureClasspath)
                    ?: error("Could not find resource: '$pictureClasspath'!")
            return ImageIcon(ImageIO.read(imageResource))
        }
    }

    object DefaultPicture : Picture(loadDefaultImage())

    class ImageIconPicture(imageIcon: ImageIcon) : Picture(imageIcon)
}
