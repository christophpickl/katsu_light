package katsu.logic

import katsu.Katsu
import javax.imageio.ImageIO
import javax.swing.ImageIcon

object Images {
    fun load(filename: String): ImageIcon {
        val pictureClasspath = "/katsu/images/$filename"
        val imageResource = Katsu::class.java.getResource(pictureClasspath)
                ?: error("Could not find resource: '$pictureClasspath'!")
        return ImageIcon(ImageIO.read(imageResource))
    }
}