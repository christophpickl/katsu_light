package katsu.view

import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.BorderFactory
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants

class JMainWindow(
        title: String,
        mainPanel: JMainPanel,
) : JFrame() {

    init {
        super.setTitle(title)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        contentPane.add(mainPanel)
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
