package com.hmathsan.desafio.views

import com.hmathsan.desafio.entities.User
import com.hmathsan.desafio.repositories.UserRepository
import com.hmathsan.desafio.services.SecurityService
import com.hmathsan.desafio.useCases.UsersUseCase
import com.vaadin.flow.component.ClickEvent
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.button.ButtonVariant
import com.vaadin.flow.component.dialog.Dialog
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.grid.GridVariant
import com.vaadin.flow.component.html.H1
import com.vaadin.flow.component.html.H3
import com.vaadin.flow.component.icon.Icon
import com.vaadin.flow.component.icon.VaadinIcon
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.notification.NotificationVariant
import com.vaadin.flow.component.orderedlayout.FlexComponent
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.EmailField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import javax.annotation.security.PermitAll

@Route("/users", layout = MainLayoutView::class)
@PermitAll
class UsersView(
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
    private val usersUseCase: UsersUseCase
) : VerticalLayout() {
    private val grid = Grid(User::class.java, false)

    init {
        setHeightFull()
        setWidthFull()

        val verticalLayout = createHeaderAndButton()

        createUsersGrid()
        verticalLayout.add(grid)

        verticalLayout.justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        verticalLayout.alignItems = FlexComponent.Alignment.CENTER

        verticalLayout.maxWidth = "75%"

        add(verticalLayout)

        justifyContentMode = FlexComponent.JustifyContentMode.CENTER
        alignItems = FlexComponent.Alignment.CENTER
    }

    fun createHeaderAndButton() : VerticalLayout {
        val verticalLayout = VerticalLayout()
        verticalLayout.setWidthFull()
        verticalLayout.setHeightFull()

        val header = H1("USUÁRIOS CADASTRADOS")
        verticalLayout.add(header)

        val newUserButton = Button("Novo")
        newUserButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
        newUserButton.addClickListener(this::newUserEvent)

        verticalLayout.add(newUserButton)

        return verticalLayout
    }

    fun createUsersGrid() {
        val authenticatedUser = securityService.getAuthenticatedUser()

        grid.setWidthFull()
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES)
        grid.addColumn(User::id).setHeader("ID")
        grid.addColumn(User::name).setHeader("NOME")
        grid.addColumn(User::email).setHeader("EMAIL")
        grid.addComponentColumn { user ->
            val horizontalLayout = HorizontalLayout()

            val editButton = Button(Icon(VaadinIcon.EDIT))
            editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY)
            editButton.addClickListener { usersUseCase.editUserListener(user) }

            horizontalLayout.add(editButton)

            if (user.id != authenticatedUser!!.id) {
                val deleteButton = Button(Icon(VaadinIcon.TRASH))
                deleteButton.addThemeVariants(
                    ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR
                )
                deleteButton.addClickListener {
                    usersUseCase.removeUserListener(user)

                    val notification = Notification.show("Usuário deletado com sucesso")
                    notification.position = Notification.Position.BOTTOM_CENTER
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS)

                    grid.setItems(userRepository.findByIsDeletedFalseAndEmailIsNot("admin@email.com.br"))
                }
                horizontalLayout.add(deleteButton)
            }

            horizontalLayout
        }

        grid.setItems(userRepository.findByIsDeletedFalseAndEmailIsNot("admin@email.com.br"))
    }

    private fun newUserEvent(event: ClickEvent<Button>) {
        val dialog = Dialog()
        dialog.width = "19rem"

        dialog.add(createDialogHeader(dialog))

        val nameTextField = TextField("Nome")
        nameTextField.isClearButtonVisible = true
        val emailTextField = EmailField("Email")
        emailTextField.element.setAttribute("name", "email")
        emailTextField.errorMessage = "Por favor insira um email válido"
        emailTextField.isClearButtonVisible = true

        val verticalLayout = VerticalLayout(nameTextField, emailTextField)
        verticalLayout.isPadding = false
        verticalLayout.isSpacing = false
        verticalLayout.alignItems = FlexComponent.Alignment.STRETCH
        verticalLayout.style.set("width", "18rem").set("max-width", "100%")

        dialog.add(verticalLayout)

        val cadastrarButton = Button("Cadastrar")
        cadastrarButton.style.set("margin-top", "15px")
        cadastrarButton.addClickListener {
            if (nameTextField.isEmpty || emailTextField.isEmpty) {
                val notification = Notification.show("Por favor preencha o campo nome e email")
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR)
                notification.position = Notification.Position.BOTTOM_CENTER
            } else {
                usersUseCase.createNewUserListener(nameTextField.value, emailTextField.value)

                val allUsers = userRepository.findByIsDeletedFalseAndEmailIsNot("admin@email.com.br")
                //val adminUser = allUsers.first { u -> u.email == "admin@email.com.br" }
                //allUsers.remove(adminUser)

                grid.setItems(allUsers)

                val notification = Notification.show("Usuário criado com sucesso. A senha foi enviada por email")
                notification.position = Notification.Position.BOTTOM_CENTER
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS)

                dialog.close()
            }
        }

        dialog.add(cadastrarButton)

        dialog.open()
    }

    private fun createDialogHeader(dialog: Dialog) : VerticalLayout {
        val title = H3("Novo usuário")
        title.style
            .set("left", "var(--lumo-space-l)")
            .set("margin", "0")
            .set("position", "absolute")

        val closeButton = Button(VaadinIcon.CLOSE.create()) { dialog.close() }
        closeButton.style
            .set("right", "var(--lumo-space-l)")
            .set("margin", "0")
            .set("position", "absolute")

        val header = HorizontalLayout(
            title,
            closeButton
        )
        header.style.set("margin", "auto")

        val headerVerticalLayout = VerticalLayout()
        headerVerticalLayout.add(header)
        return headerVerticalLayout
    }
}