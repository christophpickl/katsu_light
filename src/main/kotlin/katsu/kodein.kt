package katsu

import com.google.common.eventbus.EventBus
import katsu.Env.Dev
import katsu.Env.Prod
import katsu.controller.ClientCrudController
import katsu.controller.ClientDetailController
import katsu.controller.ClientListController
import katsu.controller.MainController
import katsu.controller.MenuBarController
import katsu.controller.TreatmentCrudController
import katsu.controller.TreatmentDetailController
import katsu.controller.TreatmentListController
import katsu.logic.DataLoader
import katsu.logic.FilterAndSort
import katsu.logic.JsonDataLoader
import katsu.logic.PictureService
import katsu.model.Client
import katsu.model.Model
import katsu.model.Treatment
import katsu.view.JClientDetail
import katsu.view.JClientList
import katsu.view.JClientMaster
import katsu.view.JMainPanel
import katsu.view.JMainWindow
import katsu.view.JTreatmentDetail
import katsu.view.JTreatmentList
import katsu.view.JTreatmentMaster
import katsu.view.KatsuMenuBar
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.io.File

private val Env.windowTitle
    get() = when (this) {
        Dev -> "Katsu DEV"
        Prod -> "Katsu PROD"
    }

fun applicationKodein(env: Env) = Kodein.Module("Application Module") {
    // generic
    bind<EventBus>() with singleton { EventBus() }

    // logic
//    bind<DataLoader>() with singleton { InMemoryDataLoader() }
    bind<DataLoader>() with singleton {
        JsonDataLoader(targetFile = File(env.appDirectory, "data.json"), instance())
    }
    bind<Model>() with singleton { Model(instance(), arrayListOf(), Client.prototype(), Treatment.prototype()) }
    bind<PictureService>() with singleton { PictureService(File(env.appDirectory, "pictures")) }
    bind<FilterAndSort>() with singleton { FilterAndSort() }

    // view
    bind<JMainWindow>() with singleton { JMainWindow("${env.windowTitle} - v${MetaInfo.properties.appVersion}", instance(), instance(), instance()) }
    bind<KatsuMenuBar>() with singleton { KatsuMenuBar(instance()) }
    bind<JMainPanel>() with singleton { JMainPanel(instance(), instance()) }
    bind<JClientMaster>() with singleton { JClientMaster(instance(), instance()) }
    bind<JClientList>() with singleton { JClientList(instance(), instance()) }
    bind<JClientDetail>() with singleton { JClientDetail() }
    bind<JTreatmentList>() with singleton { JTreatmentList(instance()) }
    bind<JTreatmentMaster>() with singleton { JTreatmentMaster(instance(), instance(), instance()) }
    bind<JTreatmentDetail>() with singleton { JTreatmentDetail() }

    // controller
    bind<MainController>() with eagerSingleton { MainController(instance(), instance(), instance(), instance()) }
    bind<MenuBarController>() with eagerSingleton { MenuBarController(instance(), instance(), instance()) }

    bind<ClientListController>() with eagerSingleton { ClientListController(instance(), instance(), instance(), instance(), instance()) }
    bind<ClientDetailController>() with eagerSingleton { ClientDetailController(instance(), instance(), instance()) }
    bind<ClientCrudController>() with eagerSingleton { ClientCrudController(instance(), instance(), instance(), instance(), instance()) }

    bind<TreatmentListController>() with eagerSingleton { TreatmentListController(instance(), instance(), instance(), instance()) }
    bind<TreatmentDetailController>() with eagerSingleton { TreatmentDetailController(instance(), instance(), instance()) }
    bind<TreatmentCrudController>() with eagerSingleton { TreatmentCrudController(instance(), instance()) }

    // app
    bind<ApplicationStarter>() with singleton { ApplicationStarter(instance(), instance()) }
}
