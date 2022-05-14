package com.hmathsan.desafio.views.components

import com.hmathsan.desafio.model.ImportHistory
import com.hmathsan.desafio.useCases.ImportTransactionUseCase
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.Label
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.upload.Upload
import com.vaadin.flow.component.upload.receivers.MemoryBuffer

class UploadFile(
    importTransactionUseCase: ImportTransactionUseCase,
) : VerticalLayout() {
    var buffer = MemoryBuffer()
    val grid = Grid(ImportHistory::class.java, false)

    init {
        setHeightFull()
        setWidthFull()

        val importarLabel = H1("IMPORTAR TRANSAÇÕES")

        add(importarLabel)
        val upload = Upload(buffer)

        upload.isDropAllowed = true
        upload.setAcceptedFileTypes("application/csv", ".csv")
        upload.width = "65%"

        upload.dropLabel = Label("Arraste e solte o arquivo aqui")
        upload.uploadButton = Button("Escolher arquivo")

        upload.addFileRejectedListener {
            val notification = Notification.show("File type not accepted")
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR)
            notification.position = Notification.Position.BOTTOM_CENTER
        }

        upload.addSucceededListener {
            updateGridContent(importTransactionUseCase.processFileAndSaveToDatabase(buffer.inputStream))

            val notification = Notification.show("Arquivo " + it.fileName + " processado com sucesso")
            notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS)
            notification.position = Notification.Position.BOTTOM_CENTER
        }

        upload.addAllFinishedListener {
            upload.clearFileList()
            buffer = MemoryBuffer()
            upload.receiver = buffer
        }

        add(upload)

        val importacoesRealizadasLabel = H1("IMPORTAÇÕES REALIZADAS")

        add(importacoesRealizadasLabel)

        grid.setWidthFull()
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES)
        grid.addColumn(ImportHistory::dataTrasacao).setHeader("DATA TRANSAÇÃO")
        grid.addColumn(ImportHistory::dataImportacao).setHeader("DATA IMPORTAÇÃO")

        grid.setItems(importTransactionUseCase.getAllImportedTransactionHistory())

        add(grid)

        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER
    }

    fun updateGridContent(importHistory: List<ImportHistory>) {
        grid.setItems(importHistory)
    }
}