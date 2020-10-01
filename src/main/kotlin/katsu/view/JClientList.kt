package katsu.view

import com.google.common.eventbus.EventBus
import katsu.model.Client
import katsu.model.ClientCategory
import mu.KotlinLogging.logger
import java.awt.Component
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.DefaultListModel
import javax.swing.JList
import javax.swing.JMenu
import javax.swing.JMenuItem
import javax.swing.JPopupMenu
import javax.swing.ListCellRenderer
import javax.swing.ListSelectionModel

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
