package katsu.view

import com.google.common.eventbus.EventBus
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JPanel

class ClientMasterPanel(
        bus: EventBus,
        val clientDetailPanel: ClientDetailPanel
) : JPanel(GridBagLayout()) {

//    TODO private val treatmentPanel = TreatmentPanel(bus)

    init {
        val buttonPanel = JPanel()
        val btnSave = JButton("Save")
        btnSave.addActionListener {
            bus.post(ClientSaveEvent())
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
//        c.gridy++
//        c.weighty = 0.5
//        add(treatmentPanel, c)
    }

//    private fun readClient() = Client(
//            id = clientDetailPanel.clientId,
//            firstName = clientDetailPanel.inpFirstName.text,
//            text = clientDetailPanel.inpText.text,
//            treatments = treatmentPanel.treatmentList.treatmentsModel.elements().toList().sorted()
//    ).enhanceTreatments()

//    private fun Client.enhanceTreatments(): Client {
//        if (isUnsaved) {
//            return this
//        }
//        // have to change treatment list model as well!
//        val updatedTreatments = treatments.associateBy { it.number }.toMutableMap()
//        val currentTreatment = treatmentPanel.readTreatment()
//        if (currentTreatment != null) {
//            updatedTreatments[currentTreatment.number] = currentTreatment
//        }
//        return copy(treatments = updatedTreatments.values.toList().sorted())
//    }
}

