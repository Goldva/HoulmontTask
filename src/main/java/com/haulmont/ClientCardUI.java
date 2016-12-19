package com.haulmont;

import com.haulmont.DataFromTable.Client;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.ui.*;

/**
 * Created by ermakov on 19.12.2016.
 */
public class ClientCardUI {
    private UI myUI;
    private TextField firstNameField;
    private TextField surNameField;
    private TextField middleNameField;
    private TextField telephoneField;
    private Button okButton;
    private Button cancelButton;

    public ClientCardUI(UI myUI) {
        this.myUI = myUI;
        this.firstNameField = new TextField("First Name");
        this.surNameField = new TextField("Surname");
        this.middleNameField = new TextField("Middle Name");
        this.telephoneField = new TextField("Telephone");
        this.okButton = new Button("OK");
        this.cancelButton = new Button("Cancel");
    }

    public void addClient(){
        Window subWindow = createWindow();
        myUI.addWindow(subWindow);
        final Client client = new Client();
        okButton.addClickListener(e -> {
            client.setFirstName(firstNameField.getValue());
            client.setSurName(surNameField.getValue());
            client.setMiddleName(middleNameField.getValue());
            client.setTelephone(telephoneField.getValue());
            subWindow.close();
        });
    }

    public void editorClient(){
        myUI.addWindow(createWindow());

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
        return subWindow;
    }

}
