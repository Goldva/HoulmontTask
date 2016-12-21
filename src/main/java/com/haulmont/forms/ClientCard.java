package com.haulmont.forms;

import com.haulmont.datarows.Client;
import com.haulmont.utils.MyContainer;
import com.vaadin.ui.*;

public class ClientCard {
    private UI myUI;
    private TextField firstNameField;
    private TextField surNameField;
    private TextField middleNameField;
    private TextField telephoneField;
    private Button okButton;
    private Button cancelButton;

    public ClientCard(UI myUI) {
        this.myUI = myUI;
        this.firstNameField = new TextField("First Name");
        this.surNameField = new TextField("Surname");
        this.middleNameField = new TextField("Middle Name");
        this.telephoneField = new TextField("Telephone");
        this.okButton = new Button("OK");
        this.cancelButton = new Button("Cancel");
    }

    public void addClient(MyContainer container){                              //TODO: Delete Container
        Window subWindow = createWindow();
        okButton.addClickListener(e -> {
            Client client = new Client();
            client.setFirstName(firstNameField.getValue());
            client.setSurName(surNameField.getValue());
            client.setMiddleName(middleNameField.getValue());
            client.setTelephone(telephoneField.getValue());
            container.addClient(client);
            subWindow.close();
        });
    }

    public void editorClient(MyContainer container, Client client){                              //TODO: Delete Container
        Window subWindow = createWindow();
        firstNameField.setValue(client.getFirstName());
        surNameField.setValue(client.getSurName());
        middleNameField.setValue(client.getMiddleName());
        telephoneField.setValue(client.getTelephone());
        okButton.addClickListener(e -> {
            client.setFirstName(firstNameField.getValue());
            client.setSurName(surNameField.getValue());
            client.setMiddleName(middleNameField.getValue());
            client.setTelephone(telephoneField.getValue());
            container.updateClient(client);
            subWindow.close();
        });
    }

    private Window createWindow(){
        Window subWindow = new Window("Client card");
        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        formLayout.setMargin(true);
        subWindow.setContent(formLayout);
        subWindow.setWidth("350px");

        formLayout.addComponent(firstNameField);
        formLayout.addComponent(surNameField);
        formLayout.addComponent(middleNameField);
        formLayout.addComponent(telephoneField);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        formLayout.addComponent(horizontalLayout);

        horizontalLayout.addComponent(okButton);
        cancelButton.addClickListener(e -> subWindow.close());
        horizontalLayout.addComponent(cancelButton);

        subWindow.center();
        myUI.addWindow(subWindow);
        return subWindow;
    }

}
