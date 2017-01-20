package com.haulmont.forms.mvc;

import com.haulmont.forms.Card;
import com.haulmont.forms.Form;
import com.vaadin.ui.AbstractComponent;

public class ViewsControl {
    public void buttonOkEnabled(Card card) {
        for (AbstractComponent component : card.getAllElements()) {
            if (component.getErrorMessage() != null) {
                card.disableOkButton();
                break;
            } else
                card.enableOkButton();
        }
    }

    public void formButtonEnable(Form form, int countRowsSelect){
        if (countRowsSelect == 1) {
            form.enableUpdateButton();
        } else {
            form.disableUpdateButton();
        }
        if (countRowsSelect > 0) {
            form.enableDeleteButton();
        } else {
            form.disableDeleteButton();
        }

    }
}
