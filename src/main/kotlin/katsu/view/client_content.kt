package katsu.view

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Client
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextField

class ClientMasterPanel(
        bus: EventBus
) : JPanel(BorderLayout()) {
    private val clientDetailPanel = ClientDetailPanel(bus)
    init {
        val buttonPanel = JPanel()
        val btnSave = JButton("Save")
        btnSave.addActionListener {
            bus.post(ClientSaveEvent(readClient()))
        }
        buttonPanel.add(btnSave)
        add(buttonPanel, BorderLayout.NORTH)
        add(clientDetailPanel, BorderLayout.CENTER)
        add(JLabel("...treatments..."), BorderLayout.SOUTH)
    }

    private fun readClient() = Client(
            id = clientDetailPanel.clientId,
            firstName = clientDetailPanel.inpFirstName.text
    )
}

class ClientDetailPanel(
        bus: EventBus
) : JPanel(GridBagLayout()) {

    var clientId = Client.NO_ID
    val inpFirstName = JTextField(20)

    init {
        bus.register(this)
        val c = GridBagConstraints()
        c.gridx = 0
        c.gridy = 0
        add(JLabel("First Name:"), c)
        c.gridx++
        add(inpFirstName, c)
    }

    @Subscribe
    fun onClientSelectedEvent(event: ClientSelectedEvent) {
        val client = event.client
        clientId = client.id
        inpFirstName.text = client.firstName
    }

    @Subscribe
    fun onClientCreateEvent(event: ClientCreateEvent) {
        clientId = Client.NO_ID
        inpFirstName.text = ""
        inpFirstName.requestFocus()
    }

    @Subscribe
    fun onClientSavedEvent(event: ClientSavedEvent) {
        clientId = event.client.id
    }
}