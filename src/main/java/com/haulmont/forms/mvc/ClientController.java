package com.haulmont.forms.mvc;

import com.haulmont.datarows.Client;
import com.haulmont.forms.Card;
import com.haulmont.forms.ClientCard;
import com.haulmont.forms.ClientsForm;
import com.haulmont.utils.ClientContainer;
import com.haulmont.utils.ClientDAO;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.Collection;

public class ClientController {
    private ClientContainer container;
    private Panel menuPanel;
    private ClientsForm clientsForm;
    private ClientCard clientCard;

    private static ClientController instance = null;

    public ClientController() {
        try {
            container = new ClientContainer(new ClientDAO());
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static ClientController getInstance() {
        if (instance==null) {
            instance = new ClientController();
        }
        return instance;
    }

    public void createForm(Panel menuPanel){
        this.menuPanel = menuPanel;
        this.clientsForm = new ClientsForm(menuPanel, this);
    }

    public void createAddCard() {
        clientCard = new ClientCard(menuPanel, this);
        clientCard.addClient();
    }

    public void createUpdateCard(Object object) {
        Client client = (Client) object;
        clientCard = new ClientCard(menuPanel, this);
        clientCard.getFirstNameField().setValue(client.getFirstName());
        clientCard.getSurNameField().setValue(client.getSurName());
        clientCard.getMiddleNameField().setValue(client.getMiddleName());
        clientCard.getTelephoneField().setValue(client.getTelephone());
        clientCard.editorClient(client);
    }

    public void addRow() {
        Client client = new Client();
        client.setFirstName(clientCard.getFirstNameField().getValue());
        client.setSurName(clientCard.getSurNameField().getValue());
        client.setMiddleName(clientCard.getMiddleNameField().getValue());
        client.setTelephone(clientCard.getTelephoneField().getValue());
        container.addClient(client);
        clientCard.getSubWindow().close();
    }

    public void updateRow(Object object) {
        Client client = (Client) object;
        client.setFirstName(clientCard.getFirstNameField().getValue());
        client.setSurName(clientCard.getSurNameField().getValue());
        client.setMiddleName(clientCard.getMiddleNameField().getValue());
        client.setTelephone(clientCard.getTelephoneField().getValue());
        container.updateClient(client);
        clientCard.getSubWindow().close();
    }

    public void deleteRows(Collection<Object> deleteClients) {
        container.deleteClients(deleteClients);
    }

    public void buttonOkEnabled(Card card, Button button) {
        for (AbstractComponent component : card.getAllElements()) {
            if (component.getErrorMessage() != null) {
                button.setEnabled(false);
                break;
            } else
                button.setEnabled(true);
        }
    }

    public BeanItemContainer<Client> getContainer() {
        return container.getContainerClients();
    }


}
