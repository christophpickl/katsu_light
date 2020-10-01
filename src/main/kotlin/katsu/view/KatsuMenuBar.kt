package katsu.view

import com.google.common.eventbus.EventBus
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem

class KatsuMenuBar(
        bus: EventBus
) : JMenuBar() {
    init {
        add(JMenu("Client").apply {
            add(JMenuItem("New").apply {
                addActionListener {
                    bus.post(ClientSaveRequestUIEvent())
                    bus.post(ClientCreateRequestUIEvent())
                }
            })
        })
    }
}