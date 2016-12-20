package com.haulmont.forms;

import com.vaadin.data.Validator;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.*;
import com.vaadin.ui.*;

import java.sql.Date;
import java.util.*;
import java.util.Calendar;

public class OrderCard {
    private UI myUI;
    private TextArea aboutOrderField;

    private DateField createDate;
    private DateField endDate;
    private TextField price;
    private ComboBox status;

    private Button okButton;
    private Button cancelButton;

    public OrderCard(UI myUI) {
        this.myUI = myUI;
        this.aboutOrderField = new TextArea("About order");
        this.createDate = new DateField("Date create order", new ObjectProperty(new GregorianCalendar().getTime()));
        this.endDate = new DateField("Date end order");
        this.price = new TextField("Price", new ObjectProperty<>(0));
        this.status = new ComboBox("Status");
        this.okButton = new Button("OK");
        this.cancelButton = new Button("Cancel");
        createWindow();
    }

//    public void addClient(MyContainer container){
//        Window subWindow = createWindow();
//        okButton.addClickListener(e -> {
//            Client client = new Client();
//            client.setFirstName(firstNameField.getValue());
//            client.setSurName(surNameField.getValue());
//            client.setMiddleName(middleNameField.getValue());
//            client.setTelephone(telephoneField.getValue());
//            container.addClient(client);
//            subWindow.close();
//        });
//    }
//
//    public void editorClient(MyContainer container, Client client){
//        Window subWindow = createWindow();
//        firstNameField.setValue(client.getFirstName());
//        surNameField.setValue(client.getSurName());
//        middleNameField.setValue(client.getMiddleName());
//        telephoneField.setValue(client.getTelephone());
//        okButton.addClickListener(e -> {
//            client.setFirstName(firstNameField.getValue());
//            client.setSurName(surNameField.getValue());
//            client.setMiddleName(middleNameField.getValue());
//            client.setTelephone(telephoneField.getValue());
//            container.updateClient(client);
//            subWindow.close();
//        });
//    }

    private Window createWindow(){
        Window subWindow = new Window("Order card");
        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        formLayout.setMargin(true);
        subWindow.setContent(formLayout);
        subWindow.setWidth("350px");

        price.addValidator(new IntegerRangeValidator("Enter the numbers", 0, Integer.MAX_VALUE));
        createDate.addValidator(new AbstractValidator<Date>("aaaaa") {
            @Override
            protected boolean isValidValue(Date date) {
                Calendar s = new GregorianCalendar();
                s.add(Calendar.DAY_OF_YEAR, -1);
                boolean a = s.after(date);
                return a;
            }

            @Override
            public Class<Date> getType() {
                return Date.class;
            }
        });

        formLayout.addComponent(aboutOrderField);
        formLayout.addComponent(createDate);
        formLayout.addComponent(endDate);
        formLayout.addComponent(price);
        formLayout.addComponent(status);

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
