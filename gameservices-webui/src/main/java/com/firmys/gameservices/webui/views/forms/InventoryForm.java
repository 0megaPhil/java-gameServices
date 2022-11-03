package com.firmys.gameservices.webui.views.forms;

import com.firmys.gameservices.webui.views.InventoriesView;
import com.firmys.gameservices.webui.views.InventoryView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class InventoryForm extends FormLayout {

    private TextField uuid = new TextField("UUID");
    private TextField ownedItems = new TextField("Owned Items");
    private TextField ownedCurrencies = new TextField("Owned Currencies");
    private Button delete = new Button("Delete");
    private final InventoryView inventoriesView;

    public InventoryForm(InventoryView inventoryView) {
        this.inventoriesView = inventoryView;
        HorizontalLayout buttons = new HorizontalLayout(delete);
        add(uuid, ownedItems, ownedCurrencies, buttons);
    }

    public void setUuid(String uuid) {
        this.uuid.setValue(uuid);
    }

    public void setOwnedItems(String ownedItems) {
        this.ownedItems.setValue(ownedItems);
    }

    public void setOwnedCurrencies(String ownedCurrencies) {
        this.ownedCurrencies.setValue(ownedCurrencies);
    }
}
