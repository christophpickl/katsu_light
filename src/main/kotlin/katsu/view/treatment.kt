package katsu.view

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.Treatment
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JScrollPane
import javax.swing.JTextArea

private val dateFormatter = DateTimeFormatter.ofPattern("d.M.")
val Treatment.dateFormatted: String get() = dateFormatter.format(date)
private val dateFormatterLong = DateTimeFormatter.ofPattern("EEE, d.M., HH:mm")
val Treatment.dateFormattedLong: String get() = dateFormatterLong.format(date)

class TreatmentPanel(bus: EventBus) : JPanel(BorderLayout()) {

    private val nothingSelected = "-"
    private val txtTitle = JLabel(nothingSelected)
    private val inpText = JTextArea()
    val treatmentList = TreatmentListPanel(bus)
    private var currentTreatment: Treatment? = null

    init {
        bus.register(this)
        add(JScrollPane(treatmentList), BorderLayout.WEST)
        val detailPanel = JPanel(GridBagLayout())

        val c = GridBagConstraints()
        c.gridx = 0
        c.gridy = 0
        c.weightx = 1.0
        c.weighty = 0.0
        detailPanel.add(txtTitle, c)

        c.gridy++
        c.weightx = 1.0
        c.weighty = 1.0
        detailPanel.add(inpText, c)

        add(detailPanel, BorderLayout.CENTER)
    }

    @Subscribe
    fun onTreatmentSelectedEvent(event: TreatmentSelectedEvent) {
        uiInit(event.treatment)
    }

    @Subscribe
    fun onClientCreateEvent(event: ClientCreateEvent) {
        uiReset()
    }

    @Subscribe
    fun onClientSelectedEvent(event: ClientSelectedEvent) {
        if (event.client.treatments.isEmpty()) {
            uiReset()
        } else {
            uiInit(event.client.treatments.first())
        }
    }

    private fun uiInit(treatment: Treatment) {
        currentTreatment = treatment
        txtTitle.text = "Treatment ${treatment.number} on ${treatment.dateFormattedLong}"
        inpText.text = treatment.text
    }

    private fun uiReset() {
        currentTreatment = null
        txtTitle.text = nothingSelected
        inpText.text = ""
    }

    fun readTreatment(): Treatment? = currentTreatment?.copy(
            text = inpText.text
    )
}
