package katsu.view

import com.google.common.eventbus.EventBus
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import javax.swing.JButton
import javax.swing.JPanel
import javax.swing.JScrollPane

class JTreatmentMaster(
        bus: EventBus,
        treatmentList: JTreatmentList,
        treatmentDetail: JTreatmentDetail
) : JPanel(GridBagLayout()) {

    init {
        bus.register(this)

        val c = GridBagConstraints()
        c.gridx = 0
        c.gridy = 0
        c.weightx = 0.1
        c.weighty = 1.0
        c.fill = GridBagConstraints.BOTH
        add(JScrollPane(treatmentList), c)

        c.gridy++
        c.weighty = 0.0
        c.fill = GridBagConstraints.NONE
        // TODO disable, if no client selected (at app startup)
        add(JButton("New Treatment").apply { addActionListener { bus.post(TreatmentNewUIEvent()) } }, c)

        c.gridy = 0
        c.gridx++
        c.gridheight = 2
        c.weightx = 1.0
        c.weighty = 1.0
        c.fill = GridBagConstraints.BOTH
        add(treatmentDetail, c)
    }

//    @Subscribe
//    fun onTreatmentSelectedEvent(event: TreatmentSelectedEvent) {
//        uiInit(event.treatment)
//    }
//
//    @Subscribe
//    fun onClientCreateEvent(event: ClientCreateEvent) {
//        uiReset()
//    }
//
//    @Subscribe
//    fun onClientSelectedEvent(event: ClientSelectedEvent) {
//        if (event.client.treatments.isEmpty()) {
//            uiReset()
//        } else {
//            uiInit(event.client.treatments.first())
//        }
//    }

}
