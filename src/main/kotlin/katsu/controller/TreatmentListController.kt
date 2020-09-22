package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.CurrentClientModelEvent
import katsu.model.CurrentTreatmentModelEvent
import katsu.model.Model
import katsu.view.ClientCreateRequestUIEvent
import katsu.view.JTreatmentList
import katsu.view.TreatmentNewUIEvent
import katsu.view.TreatmentSelectedUIEvent
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
        log.debug { "on $event" }
        treatmentList.treatmentsModel.clear()
    }

    @Subscribe
    fun onTreatmentNewUIEvent(event: TreatmentNewUIEvent) {
        log.debug { "on $event" }
        val nextTreatment = model.currentClient.nextTreatment()
        treatmentList.treatmentsModel.add(0, nextTreatment)
        model.currentClient.treatments.add(0, nextTreatment)
        model.currentTreatment = nextTreatment
    }

    @Subscribe
    fun onCurrentClientModelEvent(event: CurrentClientModelEvent) {
        log.debug { "on $event" }
        val treatments = event.currentClient.treatments

        treatmentList.treatmentsModel.clear()
        treatmentList.treatmentsModel.addAll(treatments)
    }

    @Subscribe
    fun onTreatmentSelectedUIEvent(event: TreatmentSelectedUIEvent) {
        log.debug { "on $event" }
        model.currentTreatment = event.treatment
    }

    @Subscribe
    fun onCurrentTreatmentModelEvent(event: CurrentTreatmentModelEvent) {
        log.debug { "on $event" }
        val positionInList = treatmentList.treatmentsModel.indexOf(event.currentTreatment)
        log.debug { "Position in list for new current treatment: $positionInList" }
        if (positionInList != -1) {
            treatmentList.selectedIndexWithoutEvent(positionInList)
        }
    }

}