package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.logic.FilterAndSort
import katsu.view.KatsuMenuBar
import mu.KotlinLogging.logger

class MenuBarController(
        bus: EventBus,
        private val menuBar: KatsuMenuBar,
        private val filter: FilterAndSort,
) {
    private val log = logger {}

    init {
        bus.register(this)
    }

    @Subscribe
    fun onFilterSortChangedEvent(event: FilterSortChangedEvent) {
        log.trace { "on $event" }
        menuBar.enableShowInactiveClients(filter.showInactiveClients)
    }
}