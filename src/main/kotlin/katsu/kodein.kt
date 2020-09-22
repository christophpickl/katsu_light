package katsu

import com.google.common.eventbus.EventBus
import katsu.Env.*
import katsu.controller.ClientCrudController
import katsu.controller.ClientListController
import katsu.controller.ClientDetailController
import katsu.controller.MainController
import katsu.controller.TreatmentCrudController
import katsu.controller.TreatmentDetailController
import katsu.controller.TreatmentListController
import katsu.logic.DataLoader
import katsu.logic.JsonDataLoader
import katsu.model.Client
import katsu.model.Model
import katsu.model.Treatment
import katsu.view.JClientDetail
import katsu.view.ClientList
import katsu.view.JClientList
import katsu.view.JClientMaster
import katsu.view.JMainPanel
import katsu.view.JMainWindow
import katsu.view.JTreatmentDetail
import katsu.view.JTreatmentList
import katsu.view.JTreatmentMaster
import mu.KotlinLogging
import mu.KotlinLogging.logger
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import java.io.File
import java.lang.IllegalArgumentException

private val log = logger {}

fun applicationKodein() = Kodein.Module("Application Module") {
    val env = Env.readFromSystemProperties()
    log.info { "Environment: $env" }

    // generic
    bind<EventBus>() with singleton { EventBus() }

    // logic
//    bind<DataLoader>() with singleton { InMemoryDataLoader() }
    bind<DataLoader>() with singleton { JsonDataLoader(
            targetFile = File(env.appDirectory, "data.json")
    ) }

    bind<Model>() with singleton { Model(instance(), arrayListOf(), Client.prototype(), Treatment.prototype()) }

    // view
    bind<JMainWindow>() with singleton { JMainWindow(instance()) }
    bind<JMainPanel>() with singleton { JMainPanel(instance(), instance()) }
    bind<JClientMaster>() with singleton { JClientMaster(instance(), instance(), instance()) }
    bind<JClientList>() with singleton { JClientList(instance()) }
    bind<ClientList>() with singleton { ClientList(instance()) }
    bind<JClientDetail>() with singleton { JClientDetail() }

    bind<JTreatmentList>() with singleton { JTreatmentList(instance()) }
    bind<JTreatmentMaster>() with singleton { JTreatmentMaster(instance(), instance(), instance()) }
    bind<JTreatmentDetail>() with singleton { JTreatmentDetail() }

    // controller
    bind<MainController>() with eagerSingleton { MainController(instance(), instance(), instance(), instance()) }
    bind<ClientListController>() with eagerSingleton { ClientListController(instance(), instance(), instance()) }
    bind<ClientDetailController>() with eagerSingleton { ClientDetailController(instance(), instance(), instance()) }
    bind<ClientCrudController>() with eagerSingleton { ClientCrudController(instance(), instance(), instance(), instance()) }

    bind<TreatmentListController>() with eagerSingleton { TreatmentListController(instance(), instance(), instance()) }
    bind<TreatmentDetailController>() with eagerSingleton { TreatmentDetailController(instance(), instance(), instance()) }
    bind<TreatmentCrudController>() with eagerSingleton { TreatmentCrudController(instance(), instance()) }

    // app
    bind<ApplicationStarter>() with singleton { ApplicationStarter(instance()) }
}
