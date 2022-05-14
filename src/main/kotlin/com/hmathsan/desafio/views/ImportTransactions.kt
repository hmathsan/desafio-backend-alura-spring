package com.hmathsan.desafio.views

import com.hmathsan.desafio.repositories.TransactionImportHistoryRepository
import com.hmathsan.desafio.repositories.TransactionRepository
import com.hmathsan.desafio.useCases.ImportTransactionUseCase
import com.hmathsan.desafio.views.components.UploadFile
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route

@Route
class ImportTransactions(
    importTransactionUseCase: ImportTransactionUseCase
) : HorizontalLayout() {

    init {
        setWidthFull()
        setHeightFull()
        val uploadFile = UploadFile(importTransactionUseCase)

        val verticalLayout = VerticalLayout()
        verticalLayout.add(uploadFile)

        verticalLayout.maxWidth = "85%"

        add(verticalLayout)

        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER
    }
}