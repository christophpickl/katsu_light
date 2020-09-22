package katsu.view

import katsu.Debug
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea

class JTreatmentDetail() : JPanel(GridBagLayout()) {

    // TODO date
    val txtTitle = JLabel()
    val inpText = JTextArea()

    init {
        JPanel(GridBagLayout())
        if(Debug.bgColors) background = Color.YELLOW
        val c = GridBagConstraints()

        c.gridx = 0
        c.gridy = 0
        c.weightx = 1.0
        c.weighty = 0.0
        c.fill = GridBagConstraints.HORIZONTAL
        add(txtTitle, c)

        c.gridy++
        c.weightx = 1.0
        c.weighty = 1.0
        c.fill = GridBagConstraints.BOTH
        add(JScrollPane(inpText), c)
    }
}
