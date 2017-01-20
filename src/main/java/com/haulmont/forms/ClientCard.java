package com.haulmont.forms;

import com.haulmont.datarows.Client;
import com.haulmont.forms.mvc.ClientController;
import com.haulmont.forms.mvc.ViewsControl;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.util.Arrays;
import java.util.Collection;


public class ClientCard implements Card {
    private Panel panel;
    private ClientController controller;
    private ViewsControl viewsController;
    TextField firstNameField;
     Window subWindow;
     TextField surNameField;
     TextField middleNameField;
     TextField telephoneField;
     Button okButton;
     Button cancelButton;

    public ClientCard(Panel panel, ClientController controller) {
        this.panel = panel;
        this.controller = controller;
        this.viewsController = new ViewsControl();
        this.firstNameField = new TextField("First Name");
        this.surNameField = new TextField("Surname");
        this.middleNameField = new TextField("Middle Name");
        this.telephoneField = new TextField("Telephone");
        this.okButton = new Button("OK");
        this.cancelButton = new Button("Cancel");
        this.subWindow = createWindow();
    }

    public void addClient() {
        okButton.addClickListener(e -> controller.addRow());
    }

    public void editorClient(Client client) {
        okButton.addClickListener(e -> controller.updateRow(client));
    }

    private Window createWindow() {
        Window subWindow = new Window("Client card");
        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        formLayout.setMargin(true);
        subWindow.setContent(formLayout);
        subWindow.setWidth("350px");

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);

        firstNameField.addValidator(new StringLengthValidator("Minimal lenght about order 2 symbols", 2, null, true));
        surNameField.addValidator(new StringLengthValidator("Minimal lenght about order 2 symbols", 2, null, true));
        telephoneField.addValidator(new StringLengthValidator("Minimal lenght about order 2 symbols", 2, null, true));

        formLayout.addAttachListener(attachEvent -> viewsController.buttonOkEnabled(this));
        firstNameField.addValueChangeListener(event -> viewsController.buttonOkEnabled(this));
        surNameField.addValueChangeListener(event -> viewsController.buttonOkEnabled(this));
        telephoneField.addValueChangeListener(event -> viewsController.buttonOkEnabled(this));
        cancelButton.addClickListener(e -> subWindow.close());

        formLayout.addComponent(firstNameField);
        formLayout.addComponent(surNameField);
        formLayout.addComponent(middleNameField);
        formLayout.addComponent(telephoneField);
        formLayout.addComponent(horizontalLayout);
        horizontalLayout.addComponent(okButton);
        horizontalLayout.addComponent(cancelButton);

        subWindow.center();
        subWindow.setModal(true);
        panel.getUI().addWindow(subWindow);
        return subWindow;
    }

    public Window getSubWindow() {
        return subWindow;
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getSurNameField() {
        return surNameField;
    }

    public TextField getMiddleNameField() {
        return middleNameField;
    }

    public TextField getTelephoneField() {
        return telephoneField;
    }

    @Override
    public Collection<AbstractComponent> getAllElements() {
        return Arrays.asList(firstNameField, subWindow, middleNameField, telephoneField);
    }

    @Override
    public void enableOkButton() {
        okButton.setEnabled(true);
    }

    @Override
    public void disableOkButton() {
        okButton.setEnabled(false);
    }
}
