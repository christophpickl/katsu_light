package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.model.CurrentClientModelEvent
import katsu.model.CurrentTreatmentModelEvent
import katsu.model.Model
import katsu.model.Treatment
import katsu.view.ClientCreateRequestUIEvent
import katsu.view.JTreatmentDetail
import katsu.view.dateFormattedLong
import mu.KotlinLogging

class TreatmentDetailController(
        bus: EventBus,
        private val treatmentDetail: JTreatmentDetail,
        private val model: Model
) {
    private val log = KotlinLogging.logger {}
    init {
        bus.register(this)
    }

    @Subscribe
    fun onClientCreateRequestUIEvent(event: ClientCreateRequestUIEvent) {
        model.currentTreatment = Treatment.prototype()
    }

    @Subscribe
    fun onCurrentTreatmentModelEvent(event: CurrentTreatmentModelEvent) {
        uiInit(event.currentTreatment)
    }

    @Subscribe
    fun onCurrentClientModelEvent(event: CurrentClientModelEvent) {
        log.debug { "onCurrentClientChangedEvent: $event" }

        if (event.currentClient.treatments.isNotEmpty()) {
            model.currentTreatment = event.currentClient.treatments.first()
        } else {
            model.currentTreatment = Treatment.prototype()
        }
    }

    private fun uiInit(treatment: Treatment) {
        if(treatment.isPrototype) {
            uiReset()
        } else {
            treatmentDetail.txtTitle.text = treatment.longTitle
            treatmentDetail.inpText.text = treatment.note
        }
    }

    private fun uiReset() {
        treatmentDetail.txtTitle.text = "N/A"
        treatmentDetail.inpText.text = ""
    }

    private val Treatment.longTitle get() = "Treatment $number on $dateFormattedLong"
}

