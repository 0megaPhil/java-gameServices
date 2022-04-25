package com.firmys.gameservices.webui.views;

import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.models.Inventory;
import com.firmys.gameservices.sdk.services.InventoriesSdk;
import com.firmys.gameservices.sdk.services.ItemSdk;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.MultiSelect;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Route(ServiceStrings.INVENTORIES)
@UIScope
public class InventoriesView extends VerticalLayout {
    private static final H1 heading = new H1(ServiceStrings.INVENTORIES.toUpperCase());
    private final InventoriesSdk inventoriesSdk;
    private final ItemSdk itemSdk;

    public InventoriesView(@Autowired InventoriesSdk inventoriesSdk,
                           @Autowired ItemSdk itemSdk) {
        this.inventoriesSdk = inventoriesSdk;
        this.itemSdk = itemSdk;

        Grid<Inventory> inventoriesGrid = fetchInventoriesGrid();
        Text selectedUuid = new Text("");

        add(heading, selectedUuid, addButton(inventoriesGrid, selectedUuid),
                deleteButton(inventoriesGrid, selectedUuid), inventoriesGrid);
    }

    public Grid<Inventory> fetchInventoriesGrid() {
        Grid<Inventory> inventoriesGrid = new Grid<>();
        inventoriesGrid.addColumn(Inventory::getUuid).setHeader(ServiceStrings.UUID);
        inventoriesGrid.addColumn(i -> i.getOwnedItems().getOwnedItemMap()).setHeader(ServiceStrings.OWNED_ITEMS);
        inventoriesGrid.addColumn(i -> i.getOwnedCurrencies().getOwnedCurrencyMap()).setHeader(ServiceStrings.OWNED_CURRENCIES);
        inventoriesGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        inventoriesGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        inventoriesGrid.setItems(Objects.requireNonNull(inventoriesSdk.findMultipleInventory(null).block()));
        return inventoriesGrid;
    }

    public MultiSelect<Grid<Inventory>, Inventory> inventoriesSelect(Grid<Inventory> grid) {
        return grid.asMultiSelect();
    }

    public Button addButton(Grid<Inventory> grid, Text uuidText) {
        return new Button("Add Inventories", e -> {
            Set<Inventory> addedSet = inventoriesSdk.addMultipleInventory(1).block();
            uuidText.setText("ADDED -> " + Objects.requireNonNull(addedSet).stream().map(i ->
                    i.getUuid().toString()).collect(Collectors.joining("\n")));
            grid.setItems(Objects.requireNonNull(inventoriesSdk.findMultipleInventory(null).block()));
        });
    }

    public Button deleteButton(Grid<Inventory> grid, Text uuidText) {
        return new Button("Delete Inventories", e -> {
            Set<Inventory> inventorySet = inventoriesSelect(grid).getOptionalValue().orElse(Set.of(new Inventory()));
            uuidText.setText("DELETE -> " + inventorySet.stream().map(i ->
                            i.getUuid().toString()).collect(Collectors.joining("\n")));
            inventoriesSdk.deleteMultipleInventory(inventorySet.stream().map(i -> i.getUuid().toString()).collect(Collectors.toSet()), null).block();
            grid.setItems(Objects.requireNonNull(inventoriesSdk.findMultipleInventory(null).block()));
        });
    }

}
