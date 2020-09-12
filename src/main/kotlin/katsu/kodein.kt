package katsu

import com.google.common.eventbus.EventBus
import katsu.controller.MainController
import katsu.logic.DataLoader
import katsu.logic.InMemoryDataLoader
import katsu.view.MainWindow
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun applicationKodein() = Kodein.Module("Application Module") {
    // generic
    bind<EventBus>() with singleton { EventBus() }
    // logic
    bind<DataLoader>() with singleton { InMemoryDataLoader() }
    // view
    bind<MainWindow>() with singleton { MainWindow(instance()) }
    // controller
    bind<MainController>() with singleton { MainController(instance(), instance(), instance()) }
    // app
    bind<ApplicationStarter>() with singleton { ApplicationStarter(instance()) }
}
