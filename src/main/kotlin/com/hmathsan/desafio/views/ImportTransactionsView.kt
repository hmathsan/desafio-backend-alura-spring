package com.hmathsan.desafio.views

import com.hmathsan.desafio.useCases.ImportTransactionUseCase
import com.hmathsan.desafio.views.components.UploadFileViewComponent
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import javax.annotation.security.PermitAll

@Route(value = "", layout = MainLayoutView::class)
@PermitAll
class ImportTransactionsView(
    importTransactionUseCase: ImportTransactionUseCase
) : HorizontalLayout() {

    init {
        setWidthFull()
        setHeightFull()
        val uploadFile = UploadFileViewComponent(importTransactionUseCase)

        val verticalLayout = VerticalLayout()
        verticalLayout.add(uploadFile)

        verticalLayout.maxWidth = "75%"

        add(verticalLayout)

        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER
    }
}