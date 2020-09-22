package katsu.view

import com.google.common.eventbus.EventBus
import katsu.model.Treatment
import mu.KotlinLogging.logger
import java.awt.Component
import java.awt.Dimension
import javax.swing.DefaultListCellRenderer
import javax.swing.DefaultListModel
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.ListCellRenderer
import javax.swing.ListSelectionModel

class JTreatmentList(
        bus: EventBus
) : JList<Treatment>() {

    private val log = logger {}
    val treatmentsModel = DefaultListModel<Treatment>()

    init {
        bus.register(this)
        model = treatmentsModel
        cellRenderer = TreatmentCellRenderer()
        preferredSize = Dimension(100, preferredSize.height)
//        maximumSize = Dimension(30, maximumSize.height)
        selectionMode = ListSelectionModel.SINGLE_SELECTION
        addListSelectionListener { e ->
            if (!e.valueIsAdjusting && selectedIndex != -1) { // if clearSelection() => index is -1
                val selectedTreatment = treatmentsModel.elementAt(selectedIndex)
                log.debug { "list changed to: $selectedTreatment" }
                bus.post(TreatmentSelectedUIEvent(selectedTreatment))
            }
        }
    }
}

private class TreatmentCellRenderer : ListCellRenderer<Treatment> {
    private val delegate = DefaultListCellRenderer()
    override fun getListCellRendererComponent(list: JList<out Treatment>, value: Treatment, index: Int, isSelected: Boolean, cellHasFocus: Boolean): Component {
        val label = delegate.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus) as JLabel
        label.text = "${value.number} - ${value.dateFormatted}"
        return label
    }
}