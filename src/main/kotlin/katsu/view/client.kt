package katsu.view

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Client
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JTextArea
import javax.swing.JTextField

class ClientMasterPanel(
        bus: EventBus
) : JPanel(GridBagLayout()) {

    private val clientDetailPanel = ClientDetailPanel(bus)
    private val treatmentPanel = TreatmentPanel(bus)

    init {
        val buttonPanel = JPanel()
        val btnSave = JButton("Save")
        btnSave.addActionListener {
            bus.post(ClientSaveEvent(readClient()))
        }
        buttonPanel.add(btnSave)


        val c = GridBagConstraints()
        c.gridx = 0
        c.gridy = 0
        c.weightx = 1.0
        c.weighty = 0.0
        add(buttonPanel, c)
        c.gridy++
        c.weighty = 0.5
        add(clientDetailPanel, c)
        c.gridy++
        c.weighty = 0.5
        add(treatmentPanel, c)
    }

    private fun readClient() = Client(
            id = clientDetailPanel.clientId,
            firstName = clientDetailPanel.inpFirstName.text,
            text = clientDetailPanel.inpText.text,
            treatments = treatmentPanel.treatmentList.treatmentsModel.elements().toList().sorted()
    ).enhanceTreatments()

    private fun Client.enhanceTreatments(): Client {
        if (isUnsaved) {
            return this
        }
        // have to change treatment list model as well!
        val updatedTreatments = treatments.associateBy { it.number }.toMutableMap()
        val currentTreatment = treatmentPanel.readTreatment()
        if (currentTreatment != null) {
            updatedTreatments[currentTreatment.number] = currentTreatment
        }
        return copy(treatments = updatedTreatments.values.toList().sorted())
    }
}

class ClientDetailPanel(
        bus: EventBus
) : JPanel(GridBagLayout()) {

    var clientId = Client.NO_ID
    val inpFirstName = JTextField(20)
    val inpText = JTextArea()

    init {
        bus.register(this)
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

    @Subscribe
    fun onClientSelectedEvent(event: ClientSelectedEvent) {
        updateUi(event.client)
    }

    @Subscribe
    fun onClientCreateEvent(event: ClientCreateEvent) {
        clientId = Client.NO_ID
        updateUi(null)
    }

    @Subscribe
    fun onClientSavedEvent(event: ClientSavedEvent) {
        clientId = event.client.id
    }

    private fun updateUi(client: Client?) {
        if (client == null) {
            inpFirstName.text = ""
            inpText.text = ""
            inpFirstName.requestFocus()
        } else {
            clientId = client.id
            inpFirstName.text = client.firstName
            inpText.text = client.text
        }
    }
}