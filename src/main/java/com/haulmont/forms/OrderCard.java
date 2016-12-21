package com.haulmont.forms;

import com.haulmont.datarows.Client;
import com.haulmont.datarows.Order;
import com.haulmont.utils.MyContainer;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.*;
import com.vaadin.ui.*;

import java.util.*;

public class OrderCard {
    private UI myUI;
    private TextArea aboutOrderField;
    private ComboBox clientsBox;
    private DateField createDate;
    private DateField endDate;
    private TextField price;
    private ComboBox status;

    private Button okButton;
    private Button cancelButton;

    public OrderCard(UI myUI) {
        this.myUI = myUI;
        this.aboutOrderField = new TextArea("About order");
        this.clientsBox = new ComboBox("Clients");
        this.createDate = new DateField("Date create order", new ObjectProperty(new GregorianCalendar().getTime()));
        this.endDate = new DateField("Date end order");
        this.price = new TextField("Price", new ObjectProperty<>(0.0));
        this.status = new ComboBox("Status");
        this.okButton = new Button("OK");
        this.cancelButton = new Button("Cancel");
    }

    public void addOrder(MyContainer container){
        Collection<Client> clientsList = container.getListClients();
        Window subWindow = createWindow(clientsList);
        okButton.addClickListener(e -> {
            Client client = null;
            for (Client nextClient : clientsList) {
                String selectClientName =  clientsBox.getValue().toString();
                if (selectClientName.equals(nextClient.toString()))
                    client = nextClient;
            }

            Order newOrder = new Order(client, createDate.getValue());
            newOrder.setAboutOrder(aboutOrderField.getValue());
            newOrder.setEndDate(endDate.getValue());
            newOrder.setPrice(Double.parseDouble(price.getValue()));
            newOrder.setStatus(status.getValue().toString());

            container.addOrder(newOrder);
            subWindow.close();
        });
    }

    public void editorOrder(MyContainer container, Order order){
        clientsBox.setEnabled(false);
        Collection<Client> clientsList = container.getListClients();
        Window subWindow = createWindow(clientsList);
        aboutOrderField.setValue(order.getAboutOrder());
        clientsBox.select(order.getClient());
        createDate.setValue(new Date(order.getMillisecondCreateDate()));
        endDate.setValue(new Date(order.getMillisecondEndDate()));
        price.setValue(String.valueOf(order.getPrice()).replaceAll("\\.", ","));
//        status.selectselect(1);
        okButton.addClickListener(e -> {
//            client.setFirstName(firstNameField.getValue());
//            client.setSurName(surNameField.getValue());
//            client.setMiddleName(middleNameField.getValue());
//            client.setTelephone(telephoneField.getValue());
//            container.updateClient(client);
            subWindow.close();
        });
    }

    private Window createWindow(Collection<Client> clientsList){
        Window subWindow = new Window("Order card");
        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        formLayout.setMargin(true);
        subWindow.setContent(formLayout);
        subWindow.setWidth("350px");

        clientsBox.addValidator(new NullValidator("Field should not be empty", false));
        price.addValidator(new DoubleRangeValidator("Enter the numbers", 0.0, Double.MAX_VALUE));
        status.addValidator(new NullValidator("Field should not be empty", false));

//        createDate.addValidator(new AbstractValidator<Date>("aaaaa") {
//            @Override
//            protected boolean isValidValue(Date date) {
//                Calendar s = new GregorianCalendar();
//                s.add(Calendar.DAY_OF_YEAR, -1);
//                boolean a = s.after(date);
//                return a;
//            }
//
//            @Override
//            public Class<Date> getType() {
//                return Date.class;
//            }
//        });


        clientsBox.addItems(clientsList);
        clientsBox.setItemCaptionMode(AbstractSelect.ItemCaptionMode.ID);
        status.addItems(new String[]{"Запланирован", "Выполнен", "Принят клиентом"});

        formLayout.addComponent(aboutOrderField);
        formLayout.addComponent(clientsBox);
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
