package katsu

import com.google.common.eventbus.EventBus
import katsu.controller.ClientListController
import katsu.controller.ClientDetailController
import katsu.controller.MainController
import katsu.logic.DataLoader
import katsu.logic.InMemoryDataLoader
import katsu.model.Client
import katsu.model.Model
import katsu.view.ClientDetailPanel
import katsu.view.ClientList
import katsu.view.ClientListPanel
import katsu.view.ClientMasterPanel
import katsu.view.MainPanel
import katsu.view.MainWindow
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

fun applicationKodein() = Kodein.Module("Application Module") {
    // generic
    bind<EventBus>() with singleton { EventBus() }

    // logic
    bind<DataLoader>() with singleton { InMemoryDataLoader() }
    bind<Model>() with singleton { Model(instance(), arrayListOf(), Client.prototype(), null) }

    // view
    bind<MainWindow>() with singleton { MainWindow(instance()) }
    bind<MainPanel>() with singleton { MainPanel(instance(), instance()) }
    bind<ClientMasterPanel>() with singleton { ClientMasterPanel(instance()) }
    bind<ClientListPanel>() with singleton { ClientListPanel(instance(), instance()) }
    bind<ClientList>() with singleton { ClientList(instance()) }
    bind<ClientDetailPanel>() with singleton { ClientDetailPanel() }

    // controller
    bind<MainController>() with eagerSingleton { MainController(instance(), instance(), instance(), instance()) }
    bind<ClientListController>() with eagerSingleton { ClientListController(instance(), instance(), instance()) }
    bind<ClientDetailController>() with eagerSingleton { ClientDetailController(instance(), instance(), instance()) }

    // app
    bind<ApplicationStarter>() with singleton { ApplicationStarter(instance()) }
}
