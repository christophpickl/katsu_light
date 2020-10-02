package katsu.controller

import com.google.common.eventbus.EventBus
import com.google.common.eventbus.Subscribe
import katsu.logic.FilterAndSort
import katsu.logic.PictureService
import katsu.model.Client
import katsu.model.ClientAddedModelEvent
import katsu.model.Model
import katsu.view.ChangeClientActivationUIEvent
import katsu.view.ChangePictureRequestUIEvent
import katsu.view.ClientCreateRequestUIEvent
import katsu.view.ClientsLoadedEvent
import katsu.view.JClientList
import katsu.view.ShowActiveClientsUIEvent
import mu.KotlinLogging.logger
import java.awt.Dimension
import java.io.File
import javax.swing.ImageIcon
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter
import javax.swing.filechooser.FileSystemView


class ClientListController(
        private val bus: EventBus,
        private val model: Model,
        private val clientList: JClientList,
        private val pictureService: PictureService,
        private val filter: FilterAndSort,
) {
    private val log = logger {}

    init {
        bus.register(this)
    }

    @Subscribe
    fun onClientsLoadedEvent(event: ClientsLoadedEvent) {
        log.trace { "on $event" }
        clientList.addClients(model.clients)
    }

    @Subscribe
    fun onClientCreateRequestUIEvent(event: ClientCreateRequestUIEvent) {
        log.trace { "on $event" }
        clientList.clearSelection()
    }

    @Subscribe
    fun onClientAddedModelEvent(event: ClientAddedModelEvent) {
        log.trace { "on $event" }
        clientList.addClient(event.position, event.client)
        clientList.selectionModel.setSelectionInterval(event.position, event.position)
    }

    @Subscribe
    fun onChangePictureRequestUIEvent(event: ChangePictureRequestUIEvent) {
        log.trace { "on $event" }
        val currentDirectory = FileSystemView.getFileSystemView().homeDirectory//File(System.getProperty("user.home"))
        val chooser = JFileChooser(currentDirectory)
        chooser.dialogTitle = "Select client picture"
        chooser.fileFilter = FileNameExtensionFilter("Images (*.png)", "png")
        if (chooser.showDialog(null, "Load") == JFileChooser.APPROVE_OPTION) {
            val selectedFile = chooser.selectedFile
//            prefs.clientPictureDefaultFolder = selectedFile.parentFile!!
            if (!selectedFile.isPng) {
                return
            }
            pictureService.savePicture(model.currentClient, selectedFile)
        }
    }

    @Subscribe
    fun onClientDeletedEvent(event: ClientDeletedEvent) {
        log.trace { "on $event" }
        clientList.removeClient(event.client)
    }

    @Subscribe
    fun onChangeClientActivationUIEvent(event: ChangeClientActivationUIEvent) {
        log.trace { "on $event" }
        model.currentClient.active = !model.currentClient.active
        if (!model.currentClient.active && !filter.showInactiveClients) {
            clientList.removeClient(model.currentClient)
            model.currentClient = Client.prototype()
        }
    }

    @Subscribe
    fun onShowActiveClientsUIEvent(event: ShowActiveClientsUIEvent) {
        log.trace { "on $event" }
        filter.toggleShowInactiveClients()
        clientList.applyFilter()
        bus.post(FilterSortChangedEvent())
    }
}

fun ImageIcon.size() = Dimension(image.getWidth(imageObserver), image.getHeight(imageObserver))

private val File.isPng get() = isFile && name.endsWith(".png", ignoreCase = true)
