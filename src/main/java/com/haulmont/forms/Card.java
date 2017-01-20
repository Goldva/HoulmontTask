package com.haulmont.forms;

import com.vaadin.ui.AbstractComponent;

import java.util.Collection;

public interface Card {
    Collection<AbstractComponent> getAllElements();

    void enableOkButton();

    void disableOkButton();

}
