package katsu.view

import com.google.common.eventbus.EventBus
import katsu.model.Client
import mu.KotlinLogging.logger
import java.awt.BorderLayout
import java.awt.Component
import java.awt.Dimension
import java.util.*
import javax.swing.DefaultListCellRenderer
import javax.swing.DefaultListModel
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.ListCellRenderer
import javax.swing.ListSelectionModel

class JClientList(
        clientList: ClientList
) : JPanel(BorderLayout()) {
    init {
        add(JScrollPane(clientList), BorderLayout.CENTER)
    }
}

class ClientList(
        bus: EventBus
) : JList<Client>() {

    private val log = logger {}
    val clientsModel = DefaultListModel<Client>()

    init {
        model = clientsModel
        cellRenderer = ClientCellRenderer()
        preferredSize = Dimension(200, preferredSize.height)
        selectionMode = ListSelectionModel.SINGLE_SELECTION
        addListSelectionListener { e ->
            if (!e.valueIsAdjusting && selectedIndex != -1) { // if clearSelection() => index is -1
                val selectedClient = clientsModel.elementAt(selectedIndex)
                log.debug { "List selection changed to: $selectedClient" }
                bus.post(ClientSelectedUIEvent(selectedClient))
            }
        }
    }

//    @Subscribe
//    fun onClientSavedEvent(event: ClientSavedEvent) {
//        if (event.wasCreated) {
//            clientsModel.add(0, event.client)
//        } else {
//            val clientIndex = clientsModel.findClientIndexFor(event.client.id)
//            clientsModel.setElementAt(event.client, clientIndex)
//        }
//    }
}

private fun DefaultListModel<Client>.findClientIndexFor(searchId: UUID): Int {
    for (i in 0 until size()) {
        if (get(i).id == searchId) {
            return i
        }
    }
    return -1
}

private class ClientCellRenderer : ListCellRenderer<Client> {
    private val delegate = DefaultListCellRenderer()
    override fun getListCellRendererComponent(list: JList<out Client>, value: Client, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
        val label = delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus) as JLabel
        label.text = value.firstName
        return label
    }
}