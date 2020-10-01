package katsu.controller

import katsu.model.Model
import katsu.view.JTreatmentDetail
import mu.KotlinLogging.logger

class TreatmentCrudController(
        private val model: Model,
        private val treatment: JTreatmentDetail
) {

    private val log = logger {}

    fun updateModel() {
        log.debug { "updateModel" }
        if (model.currentTreatment.isPrototype) {
            log.trace { "do nothing with treatment prototype" }
        } else {
            model.currentTreatment.note = treatment.inpText.text
        }
    }
}