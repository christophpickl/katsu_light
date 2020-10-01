package katsu.view

import com.google.common.eventbus.EventBus
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants

class JMainWindow(
        title: String,
        bus: EventBus,
        mainPanel: JMainPanel,
        menuBar: KatsuMenuBar
) : JFrame() {

    init {
        super.setTitle(title)
        defaultCloseOperation = WindowConstants.DO_NOTHING_ON_CLOSE
        contentPane.add(mainPanel)
        jMenuBar = menuBar

        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                isVisible = false
                dispose()
                bus.post(ClosingUIEvent())
            }
        })
    }

    fun prepareAndShow() {
        size = Dimension(800, 500)
        setLocationRelativeTo(null)
        isVisible = true
    }
}

class JMainPanel(
        clientMaster: JClientMaster,
        clientList: JClientList
) : JPanel(BorderLayout()) {
    init {
        border = BorderFactory.createEmptyBorder(20, 10, 20, 10)
        add(clientList, BorderLayout.WEST)
        add(clientMaster, BorderLayout.CENTER)
    }
}

