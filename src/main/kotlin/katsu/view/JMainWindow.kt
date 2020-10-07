package katsu.view

import com.google.common.eventbus.EventBus
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.JScrollPane
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
) : JPanel(GridBagLayout()) {

    private val listWidth = 200

    init {
        border = BorderFactory.createEmptyBorder(20, 10, 20, 10)

        val c = GridBagConstraints()
        c.gridx = 0
        c.gridy = 0
        c.fill = GridBagConstraints.BOTH
        c.weightx = 0.0
        c.weighty = 1.0
        add(JScrollPane(clientList).hScrollOnly().apply {
            preferredSize = Dimension(listWidth, preferredSize.height)
        }, c)

        c.gridx++
        c.fill = GridBagConstraints.BOTH
        c.weightx = 1.0
        add(clientMaster, c)
    }
}

