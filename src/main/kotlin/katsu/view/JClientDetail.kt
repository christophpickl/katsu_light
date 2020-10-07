package katsu.view

import katsu.model.Client
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea
import javax.swing.JTextField

class JClientDetail : JPanel(GridBagLayout()) {

    val inpFirstName = JTextField()
    val inpBirthday = JTextField(10)
    val inpText = JTextArea()

    init {
        val c = GridBagConstraints()
        c.gridx = 0
        c.gridy = 0

        c.weightx = 0.0
        c.weighty = 0.0
        add(JLabel("First Name:"), c)

        c.gridx++
        c.weightx = 1.0
        c.fill = GridBagConstraints.HORIZONTAL
        add(inpFirstName, c)

        c.gridx++
        c.weightx = 0.0
        add(inpBirthday, c)

        c.gridx = 0
        c.gridy++
        c.weightx = 1.0
        c.weighty = 1.0
        c.gridwidth = 3
        c.fill = GridBagConstraints.BOTH
        add(JScrollPane(inpText).hScrollOnly(), c)
    }

    fun updateUi(client: Client) {
        inpFirstName.text = client.firstName
        println("update: $client")
        inpBirthday.text = client.birthdayFormatted ?: ""
        inpText.text = client.note
    }
}
