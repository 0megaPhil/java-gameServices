package com.firmys.gameservices.webui.views;

import com.firmys.gameservices.common.ServicePaths;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.services.InventoriesSdk;
import com.firmys.gameservices.sdk.services.InventorySdk;
import com.firmys.gameservices.sdk.services.ItemSdk;
import com.firmys.gameservices.webui.views.forms.InventoryForm;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

@Route(ServicePaths.INVENTORY)
@UIScope
public class InventoryView extends VerticalLayout {
    private static final H1 heading = new H1(ServicePaths.INVENTORY.toUpperCase());
    private final InventoriesSdk inventoriesSdk;
    private final InventorySdk inventorySdk;
    private final ItemSdk itemSdk;
    private final InventoryForm inventoryForm;
    private final SingleSelect<Grid<Inventory>, Inventory> inventorySelect;

    public InventoryView(@Autowired InventoriesSdk inventoriesSdk,
                         @Autowired InventorySdk inventorySdk,
                         @Autowired ItemSdk itemSdk) {
        this.inventoriesSdk = inventoriesSdk;
        this.inventorySdk = inventorySdk;
        this.itemSdk = itemSdk;

        this.inventoryForm = new InventoryForm(this);

        Grid<Inventory> inventoriesGrid = fetchInventoriesGrid();
        this.inventorySelect = inventoriesGrid.asSingleSelect();

        inventorySelect.addValueChangeListener(event -> {
            Inventory inventory = inventorySelect.getOptionalValue().orElse(new Inventory());
            if(inventory.getUuid() != null) {
                inventoryForm.setUuid(inventory.getUuid().toString());
                inventoryForm.setOwnedCurrencies(inventory.getOwnedCurrencies().getOwnedCurrencyMap().toString());
                inventoryForm.setOwnedItems(inventory.getOwnedItems().getOwnedItemMap().toString());
            }
        });

        Text selectedUuid = new Text("");

        add(heading, selectedUuid, addButton(inventoriesGrid, selectedUuid),
                deleteButton(inventoriesGrid, selectedUuid), inventoriesGrid, inventoryForm);
    }

    public Grid<Inventory> fetchInventoriesGrid() {
        Grid<Inventory> inventoriesGrid = new Grid<>();
        inventoriesGrid.addColumn(Inventory::getUuid).setHeader(ServicePaths.UUID);
        inventoriesGrid.addColumn(i -> i.getOwnedItems().getOwnedItemMap()).setHeader(ServicePaths.OWNED_ITEMS);
        inventoriesGrid.addColumn(i -> i.getOwnedCurrencies().getOwnedCurrencyMap()).setHeader(ServicePaths.OWNED_CURRENCIES);
        inventoriesGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        inventoriesGrid.setItems(Objects.requireNonNull(inventoriesSdk.findMultipleInventory(null).block()));
        return inventoriesGrid;
    }

    public Button addButton(Grid<Inventory> grid, Text uuidText) {
        return new Button("Add Inventory", e -> {
            Inventory addedInventory = inventorySdk.addInventory().block();
            uuidText.setText("ADDED -> " + Objects.requireNonNull(addedInventory).getUuid().toString());
            grid.setItems(Objects.requireNonNull(inventoriesSdk.findMultipleInventory(null).block()));
        });
    }

    public Button deleteButton(Grid<Inventory> grid, Text uuidText) {
        return new Button("Delete Inventory", e -> {
            Inventory inventory = this.inventorySelect.getOptionalValue().orElse(new Inventory());
            uuidText.setText("DELETE -> " + inventory.getUuid().toString());
            inventorySdk.deleteInventory( null, inventory).block();
            grid.setItems(Objects.requireNonNull(inventoriesSdk.findMultipleInventory(null).block()));
        });
    }
}
