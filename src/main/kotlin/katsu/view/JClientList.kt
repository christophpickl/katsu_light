package katsu.view

import com.google.common.eventbus.EventBus
import katsu.model.Client
import katsu.model.ClientCategory
import katsu.model.ClientCategoryImage
import mu.KotlinLogging.logger
import java.awt.Color
import java.awt.Component
import java.awt.Dimension
import java.awt.Graphics
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory
import javax.swing.DefaultListModel
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JMenu
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.ListCellRenderer
import javax.swing.ListSelectionModel
import javax.swing.UIManager

class JClientList(
        bus: EventBus
) : JList<Client>() {

    private val log = logger {}
    val clientsModel = DefaultListModel<Client>()

    init {
        layoutOrientation = VERTICAL
        model = clientsModel
        cellRenderer = ClientCellRenderer()
        selectionMode = ListSelectionModel.SINGLE_SELECTION
        addListSelectionListener { e ->
            if (!e.valueIsAdjusting && selectedIndex != -1) { // if clearSelection() => index is -1
                val selectedClient = clientsModel.elementAt(selectedIndex)
                log.debug { "List selection changed to: $selectedClient" }
                bus.post(ClientSelectedUIEvent(selectedClient))
            }
        }

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 1 && e.button == MouseEvent.BUTTON3) {
                    val popup = JPopupMenu()
                    popup.add(CategoryMenu(selectedValue))
                    popup.addSeparator()
                    popup.add(JMenuItem("Delete ...").apply {
                        addActionListener {
                            bus.post(DeleteClientRequestUiEvent())
                        }
                    })
                    popup.show(this@JClientList, e.x, e.y)
                } else if (e.clickCount == 2 && e.button == MouseEvent.BUTTON1) {
                    bus.post(ChangePictureRequestUIEvent())
                }
            }
        })
    }
}

private class CategoryMenu(client: Client) : JMenu("Category") {
    init {
        ClientCategory.values().forEach { category ->
            add(JMenuItem(category.label).apply {
                if (client.category == category) {
                    isEnabled = false
                } else {
                    addActionListener {
                        client.category = category
                    }
                }
            })
        }
    }
}

private class ClientCellRenderer : ListCellRenderer<Client> {
    private val panel = ClientCellPanel()
    override fun getListCellRendererComponent(list: JList<out Client>, value: Client, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
        panel.updateUi(value, isSelected)
        return panel
    }
}

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
        lblName.foreground = UIManager.getColor(if (isSelected) "List.selectionForeground" else "List.foreground")
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
        if (clientImage is ClientCategoryImage.Set) {
            g.drawImage(clientImage.imageIcon.image, 0, 0, null)
        }
    }
}