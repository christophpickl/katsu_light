package katsu.view

import java.awt.event.ActionListener
import java.awt.image.BufferedImage
import java.awt.image.RenderedImage
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JPanel

fun JPanel.addButton(label: String, onClick: ActionListener) {
    add(JButton(label).apply { addActionListener(onClick) })
}

fun ImageIcon.toBufferedImage(): RenderedImage {
    val bufferedImage = BufferedImage(iconWidth, iconHeight, BufferedImage.TYPE_INT_RGB)
    val g = bufferedImage.createGraphics()
    paintIcon(null, g, 0, 0)
    g.dispose()
    return bufferedImage
}
