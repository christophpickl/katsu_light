package katsu.service

import katsu.controller.size
import katsu.model.Client
import katsu.model.Picture
import katsu.view.toBufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import javax.swing.JOptionPane

class PictureService(
        private val baseFolder: File
) {

    fun savePicture(client: Client, pngFile: File) {
        val imageIcon = ImageIcon(ImageIO.read(pngFile))
        val size = imageIcon.size()
        if (size.width != size.height) {
            JOptionPane.showMessageDialog(null, "Profile picture must have same width/height, but was: ${size.width}/${size.height}!")
            return
        }
        saveToFile(client, imageIcon)
        client.picture = Picture.ImageIconPicture(imageIcon)
    }

    fun load(client: Client): Picture {
        val target = client.targetFile
        if (!target.exists()) return Picture.DefaultPicture
        return Picture.ImageIconPicture(ImageIcon(ImageIO.read(target)))
    }

    private fun saveToFile(client: Client, imageIcon: ImageIcon) {
        ensureBaseFolderExists()
        val bufferedImage = imageIcon.toBufferedImage()
        // will overwrite existing file
        ImageIO.write(bufferedImage, "png", client.targetFile)
    }

    private fun ensureBaseFolderExists() {
        if (baseFolder.exists()) return
        if (!baseFolder.mkdirs()) error("Base folder could not be created at: \${baseFolder.absolutePath}")
    }

    private val Client.targetFile get() = File(baseFolder, "$id.png")
}