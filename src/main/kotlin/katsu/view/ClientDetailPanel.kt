package katsu.view

import katsu.model.Client
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JTextField

class ClientDetailPanel : JPanel(GridBagLayout()) {

    var clientId = Client.NO_ID
    val inpFirstName = JTextField(20)
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
        add(inpFirstName, c)

        c.gridx = 0
        c.weightx = 1.0
        c.weighty = 1.0
        c.gridy++
        add(inpText, c)
    }

//
//    @Subscribe
//    fun onClientSavedEvent(event: ClientSavedEvent) {
//        clientId = event.client.id
//    }

    fun updateUi(client: Client) {
        clientId = client.id
        inpFirstName.text = client.firstName
        inpText.text = client.text
    }
}