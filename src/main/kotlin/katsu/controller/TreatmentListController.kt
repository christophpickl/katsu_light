package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.CurrentClientModelEvent
import katsu.model.CurrentTreatmentModelEvent
import katsu.model.Model
import katsu.view.ClientCreateRequestUIEvent
import katsu.view.JTreatmentList
import katsu.view.TreatmentSelectedUIEvent
import mu.KotlinLogging
import mu.KotlinLogging.logger

class TreatmentListController(
        bus: EventBus,
        private val model: Model,
        private val treatmentList: JTreatmentList
) {
    private val log = logger {}
    init {
        bus.register(this)
    }

    @Subscribe
    fun onClientCreateEvent(event: ClientCreateRequestUIEvent) {
        treatmentList.treatmentsModel.clear()
    }

    @Subscribe
    fun onCurrentClientModelEvent(event: CurrentClientModelEvent) {
        val treatments = event.currentClient.treatments

        treatmentList.treatmentsModel.clear()
        treatmentList.treatmentsModel.addAll(treatments)
//        if (treatments.isNotEmpty()) {
//            treatmentList.selectedIndex = 0
//        }
    }

    @Subscribe
    fun onTreatmentSelectedEvent(event: TreatmentSelectedUIEvent) {
        model.currentTreatment = event.treatment
    }

    @Subscribe
    fun onCurrentTreatmentModelEvent(event: CurrentTreatmentModelEvent) {
        println("preselect: ${event.currentTreatment}")
        val pos = treatmentList.treatmentsModel.indexOf(event.currentTreatment)
        log.debug { "Position in list for new current treatment: $pos" }
        if(pos != -1) {
            treatmentList.selectedIndex = pos
        }
    }
    // TODO on CurrentTreatmentModelEvent => select proper index

}