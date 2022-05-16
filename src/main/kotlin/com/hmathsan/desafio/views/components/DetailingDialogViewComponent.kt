package com.hmathsan.desafio.views.components

import com.hmathsan.desafio.entities.Transaction
import com.hmathsan.desafio.entities.TransactionImportHistory
import com.hmathsan.desafio.model.ImportHistory
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import java.time.format.DateTimeFormatter

class DetailingDialogViewComponent(private val importHistory: TransactionImportHistory) : Dialog() {

    private val formatterImportacao = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")
    private val formatterTransacao = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    init {
        add(createHeader())
        add(importInformation())
        add(transactionGrid())

        setWidthFull()
        width = "70%"

        open()
    }

    fun createHeader() : VerticalLayout {
        val title = H3("Detalhes da Importação")
        title.style
            .set("left", "var(--lumo-space-l)")
            .set("margin", "0")
            .set("position", "absolute")

        val closeButton = Button(VaadinIcon.CLOSE.create()) { close() }
        closeButton.style
            .set("right", "var(--lumo-space-l)")
            .set("margin", "0")
            .set("position", "absolute")

        val header = HorizontalLayout(
            title,
            closeButton
        )
        header.style.set("margin", "auto")

        val verticalLayout = VerticalLayout()
        verticalLayout.add(header)
        return verticalLayout
    }

    fun importInformation() : HorizontalLayout {
        val importadoEmTextField = TextField(
            "Importado em",
            importHistory.dataImportacao.format(formatterImportacao).toString(),
            "dd/mm/yyyy - hh:mm"
        )
        importadoEmTextField.isReadOnly = true

        val importadoPorTextField = TextField(
            "Importado por",
            importHistory.importadoPor.name,
            "Nome"
        )
        importadoPorTextField.isReadOnly = true

        val dataTransacaoTextField = TextField(
            "Data transacao",
            importHistory.dataTransacao.format(formatterTransacao).toString(),
            "dd/mm/yyyy"
        )
        dataTransacaoTextField.isReadOnly = true

        val horizontalLayout = HorizontalLayout(importadoEmTextField, importadoPorTextField, dataTransacaoTextField)
        horizontalLayout.style.set("margin-top", "30px")

        return horizontalLayout
    }

    fun transactionGrid() : Grid<Transaction> {
        val transactionGrid = Grid(Transaction::class.java, false)

        val bancoOrigemColumn = transactionGrid.addColumn(Transaction::bancoOrigem).setHeader("BANCO")
        val agenciaOrigemColumn = transactionGrid.addColumn(Transaction::agenciaOrigem).setHeader("AGENCIA")
        val contaOrigemColumn = transactionGrid.addColumn(Transaction::contaOrigem).setHeader("CONTA")

        val bancoDestinoColumn = transactionGrid.addColumn(Transaction::bancoDestino).setHeader("BANCO")
        val agenciaDestinoColumn = transactionGrid.addColumn(Transaction::agenciaDestino).setHeader("AGENCIA")
        val contaDestinoColumn = transactionGrid.addColumn(Transaction::contaDestino).setHeader("CONTA")

        transactionGrid.addColumn(Transaction::valorTransacao).setHeader("VALOR")

        val headerRow = transactionGrid.prependHeaderRow()
        headerRow.join(bancoOrigemColumn, agenciaOrigemColumn, contaOrigemColumn).setText("ORIGEM")
        headerRow.join(bancoDestinoColumn, agenciaDestinoColumn, contaDestinoColumn).setText("DESTINO")

        transactionGrid.setItems(importHistory.transaction)

        transactionGrid.style
            .set("margin-top", "30px")
            .set("margin-bottom", "10px")
        transactionGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES)

        return transactionGrid
    }
}