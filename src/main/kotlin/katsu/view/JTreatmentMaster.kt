package katsu.view

import com.google.common.eventbus.EventBus
import katsu.Debug
import katsu.model.Treatment
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.time.format.DateTimeFormatter
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea

class JTreatmentMaster(
        bus: EventBus,
        treatmentList: JTreatmentList,
        private val treatmentDetail: JTreatmentDetail
) : JPanel(BorderLayout()) {

    init {
        bus.register(this)
        if(Debug.bgColors) background = Color.BLUE

        add(JScrollPane(treatmentList), BorderLayout.WEST)
        add(treatmentDetail, BorderLayout.CENTER)
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
