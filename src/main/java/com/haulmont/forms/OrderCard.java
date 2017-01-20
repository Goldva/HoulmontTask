package com.haulmont.forms;

import com.haulmont.datarows.Order;
import com.haulmont.forms.mvc.OrderController;
import com.haulmont.forms.mvc.ViewsControl;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.ui.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.GregorianCalendar;

public class OrderCard implements Card {
    private Panel panel;
    private OrderController controller;
    private ViewsControl viewsController;
    Window subWindow;

     TextArea aboutOrderArea;
     ComboBox clientsBox;
     DateField createDate;
     DateField endDate;
     TextField price;
     ComboBox status;

    private Button okButton;
    private Button cancelButton;

    public OrderCard(Panel panel, OrderController orderController) {
        this.panel = panel;
        this.controller = orderController;
        this.viewsController = new ViewsControl();
        this.aboutOrderArea = new TextArea("About order");
        this.clientsBox = new ComboBox("Clients");
        this.createDate = new DateField("Date create order", new ObjectProperty<>(new GregorianCalendar().getTime()));
        this.endDate = new DateField("Date end order");
        this.price = new TextField("Price", new ObjectProperty<>(0.0));
        this.status = new ComboBox("Status");
        this.okButton = new Button("OK");
        this.cancelButton = new Button("Cancel");
        this.subWindow = createWindow();
    }

    public void addOrder() {
        okButton.addClickListener(e -> controller.addRow());
    }

    public void editorOrder(Order order) {
        okButton.addClickListener(e -> controller.updateRow(order));
    }

    private Window createWindow() {
        Window subWindow = new Window("Order card");
        FormLayout formLayout = new FormLayout();
        formLayout.setSpacing(true);
        formLayout.setMargin(true);
        subWindow.setContent(formLayout);
        subWindow.setWidth("350px");

        clientsBox.setInputPrompt("Enter client");
        clientsBox.setNullSelectionAllowed(false);

        status.setInputPrompt("Enter status");
        status.setNullSelectionAllowed(false);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);

        aboutOrderArea.addValidator(
                new StringLengthValidator("Minimal lenght about order 5 symbols", 5, null, true)
        );
        clientsBox.addValidator(new NullValidator("Field should not be empty", false));
        createDate.addValidator(new NullValidator("Field should not be empty", false));
        endDate.addValidator(new NullValidator("Field should not be empty", false));
        price.addValidator(new NullValidator("Field should not be empty", false));
        status.addValidator(new NullValidator("Field should not be empty", false));

        formLayout.addAttachListener(attachEvent -> viewsController.buttonOkEnabled(this));
        aboutOrderArea.addValueChangeListener(event -> viewsController.buttonOkEnabled(this));
        clientsBox.addValueChangeListener(event -> viewsController.buttonOkEnabled(this));
        createDate.addValueChangeListener(event -> viewsController.buttonOkEnabled(this));
        endDate.addValueChangeListener(event -> viewsController.buttonOkEnabled(this));
        price.addValueChangeListener(event -> viewsController.buttonOkEnabled(this));
        status.addValueChangeListener(event -> viewsController.buttonOkEnabled(this));
        cancelButton.addClickListener(e -> controller.closeCard(subWindow));

        status.addItems(Arrays.asList("Запланирован", "Выполнен", "Принят клиентом"));

        formLayout.addComponent(aboutOrderArea);
        formLayout.addComponent(clientsBox);
        formLayout.addComponent(createDate);
        formLayout.addComponent(endDate);
        formLayout.addComponent(price);
        formLayout.addComponent(status);
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

    public TextArea getAboutOrderArea() {
        return aboutOrderArea;
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
        return Arrays.asList(aboutOrderArea, clientsBox, createDate, endDate, price, status);
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
