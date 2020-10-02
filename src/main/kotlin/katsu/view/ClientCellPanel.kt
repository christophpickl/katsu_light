package katsu.view

import katsu.model.Client
import katsu.model.ClientCategoryImage
import java.awt.Color
import java.awt.Dimension
import java.awt.Graphics
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.BorderFactory
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.UIManager

class ClientCellPanel : JPanel(GridBagLayout()) {

    companion object {
        private const val verticalGap = 6
        private const val horizontalGap = 8
        private val cellBorder = BorderFactory.createEmptyBorder(verticalGap, horizontalGap, verticalGap, horizontalGap)
        private val pictureSize = Dimension(60, 60)
        private val zeroInsets = Insets(0, 0, 0, 0)
        private val rightInsets = Insets(0, 0, 0, 10)
    }

    private val picturePanel = JImagePanel(pictureSize, Client.prototype())
    private val lblName = JLabel()

    init {
        border = cellBorder
        val c = GridBagConstraints()
        c.gridx = 0
        c.gridy = 0
        c.weightx = 0.0
        c.fill = GridBagConstraints.NONE
        c.insets = rightInsets
        add(picturePanel, c)

        c.gridx++
        c.weightx = 1.0
        c.fill = GridBagConstraints.BOTH
        c.insets = zeroInsets
        add(lblName, c)
    }

    fun updateUi(client: Client, isSelected: Boolean) {
        background = UIManager.getColor(if (isSelected) "List.selectionBackground" else "List.background")
        lblName.foreground = UIManager.getColor(if (isSelected) "List.selectionForeground" else "List.foreground").let {
            if (!client.active) Color.GRAY else it
        }
        lblName.text = client.firstName
        picturePanel.client = client
        picturePanel.repaint()
    }
}

class JImagePanel(
        size: Dimension,
        var client: Client
) : JPanel() {
    init {
        background = Color.RED
        preferredSize = size
        minimumSize = size
    }

    override fun paint(g: Graphics) {
        g.drawImage(client.picture.image, 0, 0, size.width, size.height, null)
        val clientImage = client.category.image
        if (clientImage is ClientCategoryImage.Defined) {
            g.drawImage(clientImage.imageIcon.image, 0, 0, null)
        }
    }
}
