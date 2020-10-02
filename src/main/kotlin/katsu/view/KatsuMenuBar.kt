package katsu.view

import com.google.common.eventbus.EventBus
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class KatsuMenuBar(
        bus: EventBus
) : JMenuBar() {

    private val labelShowActiveOnly = "Hide inactive clients"
    private val labelShowInactiveAlso = "Show inactive clients"
    private val itemShowActive = JMenuItem(labelShowInactiveAlso)

    init {
        add(JMenu("Client").apply {
            add(JMenuItem("New").apply {
                addActionListener {
                    bus.post(ClientSaveRequestUIEvent())
                    bus.post(ClientCreateRequestUIEvent())
                }
            })
        })
        add(JMenu("View").apply {
            add(itemShowActive.apply {
                addActionListener {
                    bus.post(ShowActiveClientsUIEvent())
                }
            })
        })
    }

    fun enableShowInactiveClients(enable: Boolean) {
        itemShowActive.text = if (enable) labelShowActiveOnly else labelShowInactiveAlso
    }
}