package katsu.view

import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.BorderFactory
import javax.swing.JPanel
import javax.swing.JSplitPane

class JClientMaster(
        clientDetail: JClientDetail,
        treatments: JTreatmentMaster
) : JPanel(GridBagLayout()) {

    init {
        val c = GridBagConstraints()
        c.gridx = 0
        c.gridy = 0
        c.weightx = 1.0
        c.weighty = 1.0
        c.fill = GridBagConstraints.BOTH

        val splitPane = JSplitPane(JSplitPane.VERTICAL_SPLIT, clientDetail, treatments).apply {
            border = BorderFactory.createEmptyBorder()
            isOneTouchExpandable = true
//            dividerLocation = 150
            resizeWeight = 0.8
        }
        add(splitPane, c)
    }
}
