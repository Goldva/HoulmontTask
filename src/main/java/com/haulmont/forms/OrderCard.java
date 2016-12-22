package com.haulmont.forms;

import com.haulmont.datarows.Order;
import com.haulmont.utils.Controller;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;

public class OrderCard implements Card {
    private UI myUI;
    private Controller controller;
    private Window subWindow;

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
        this.controller = Controller.getInstance();
        this.aboutOrderField = new TextArea("About order");
        this.clientsBox = new ComboBox("Clients");
        this.createDate = new DateField("Date create order", new ObjectProperty(new GregorianCalendar().getTime()));
        this.endDate = new DateField("Date end order");
        this.price = new TextField("Price", new ObjectProperty<>(0.0));
        this.status = new ComboBox("Status");
        this.okButton = new Button("OK");
        this.cancelButton = new Button("Cancel");
        this.subWindow = createWindow();
    }

    public void addOrder() {
        okButton.addClickListener(e -> controller.addOrder(this));
    }

    public void editorOrder(Order order) {
        okButton.addClickListener(e -> controller.updateOrder(this, order));
    }

    private Window createWindow() {
        Window subWindow = new Window("Order card");
        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        formLayout.setMargin(true);
        subWindow.setContent(formLayout);
        subWindow.setWidth("350px");

        aboutOrderField.addValidator(
                new StringLengthValidator("Minimal lenght about order 5 symbols", 5, null, true)
        );
        clientsBox.addValidator(new NullValidator("Field should not be empty", false));
        createDate.addValidator(new NullValidator("Field should not be empty", false));
//        createDate.addValidator(new DateRangeValidator(
//                "Uncorrected date", new Date(), getEndDate().getValue(), null)
//        );
        endDate.addValidator(new NullValidator("Field should not be empty", false));
//        endDate.addValidator(new DateRangeValidator(
//                        "Uncorrected date", getCreateDate().getValue(),  null, null)
//        );
        price.addValidator(new NullValidator("Field should not be empty", false));
        status.addValidator(new NullValidator("Field should not be empty", false));

        formLayout.addAttachListener(attachEvent -> controller.buttonOkEnabled(this, okButton));
        aboutOrderField.addValueChangeListener(event -> controller.buttonOkEnabled(this, okButton));
        clientsBox.addValueChangeListener(event -> controller.buttonOkEnabled(this, okButton));
        createDate.addValueChangeListener(event -> controller.buttonOkEnabled(this, okButton));
        endDate.addValueChangeListener(event -> controller.buttonOkEnabled(this, okButton));
        price.addValueChangeListener(event -> controller.buttonOkEnabled(this, okButton));
        status.addValueChangeListener(event -> controller.buttonOkEnabled(this, okButton));

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
        cancelButton.addClickListener(e -> controller.closeCard(subWindow));
        horizontalLayout.addComponent(cancelButton);

        subWindow.center();
        myUI.addWindow(subWindow);
        return subWindow;
    }

    public Window getSubWindow() {
        return subWindow;
    }

    public TextArea getAboutOrderField() {
        return aboutOrderField;
    }

    public ComboBox getClientsBox() {
        return clientsBox;
    }

    public DateField getCreateDate() {
        return createDate;
    }

    public DateField getEndDate() {
        return endDate;
    }

    public TextField getPrice() {
        return price;
    }

    public ComboBox getStatus() {
        return status;
    }

    @Override
    public Collection<AbstractComponent> getAllElements() {
        return Arrays.asList(aboutOrderField, clientsBox, createDate, endDate, price, status);
    }
}
