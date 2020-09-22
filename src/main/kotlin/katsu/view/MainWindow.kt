package katsu.view

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Model
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextField
import javax.swing.WindowConstants

class MainWindow(
        val mainPanel: MainPanel
) : JFrame() {

    init {
        title = "Katsu Light"
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        contentPane.add(mainPanel)
    }

    fun prepareAndShow() {
        size = Dimension(800, 500)
        setLocationRelativeTo(null)
        isVisible = true
    }
}

class MainPanel(
        val clientMaster: ClientMasterPanel,
        val clientList: ClientListPanel
) : JPanel(BorderLayout()) {
    init {
        add(clientList, BorderLayout.WEST)
        add(clientMaster, BorderLayout.CENTER)
    }
}
