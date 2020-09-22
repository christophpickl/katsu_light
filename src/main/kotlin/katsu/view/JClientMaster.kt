package katsu.view

import com.google.common.eventbus.EventBus
import katsu.Debug
import java.awt.Color
import java.awt.FlowLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JPanel
import javax.swing.JSplitPane


class JClientMaster(
        bus: EventBus,
        clientDetail: JClientDetail,
        treatments: JTreatmentMaster
) : JPanel(GridBagLayout()) {

    init {
        val buttonPanel = JPanel(FlowLayout(FlowLayout.LEFT))
        if(Debug.bgColors) buttonPanel.background = Color.PINK

        buttonPanel.addButton("Save Client") {
            bus.post(ClientSaveRequestUIEvent())
        }
        buttonPanel.addButton("New Client") {
            bus.post(ClientCreateRequestUIEvent())
        }

        val c = GridBagConstraints()
        c.gridx = 0
        c.gridy = 0

        c.weightx = 1.0
        c.weighty = 0.0
        c.anchor = GridBagConstraints.WEST
        add(buttonPanel, c)

        val splitPane = JSplitPane(JSplitPane.VERTICAL_SPLIT, clientDetail, treatments).apply {
            isOneTouchExpandable = true
//            dividerLocation = 150
            resizeWeight = 0.8
        }
        c.gridy++
        c.weighty = 1.0
        c.fill = GridBagConstraints.BOTH
        add(splitPane, c)
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
