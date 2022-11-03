package com.firmys.gameservices.webui.views;

import com.firmys.gameservices.common.ServiceConstants;
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

@Route(ServiceConstants.INVENTORIES)
@UIScope
public class InventoriesView extends VerticalLayout {
    private static final H1 heading = new H1(ServiceConstants.INVENTORIES.toUpperCase());
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
        inventoriesGrid.addColumn(Inventory::getUuid).setHeader(ServiceConstants.UUID);
        inventoriesGrid.addColumn(i -> i.getOwnedItems().getOwnedItemMap()).setHeader(ServiceConstants.OWNED_ITEMS);
        inventoriesGrid.addColumn(i -> i.getOwnedCurrencies().getOwnedCurrencyMap()).setHeader(ServiceConstants.OWNED_CURRENCIES);
        inventoriesGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        inventoriesGrid.setSelectionMode(Grid.SelectionMode.MULTI);
//        inventoriesGrid.setItems(Objects.requireNonNull(inventoriesSdk.findMultipleInventory(null).block()));
        return inventoriesGrid;
    }

    public MultiSelect<Grid<Inventory>, Inventory> inventoriesSelect(Grid<Inventory> grid) {
        return grid.asMultiSelect();
    }

    public Button addButton(Grid<Inventory> grid, Text uuidText) {
        return new Button("Add Inventories", e -> {
//            Set<Inventory> addedSet = inventoriesSdk.addMultipleInventory(1).block();
//            uuidText.setText("ADDED -> " + Objects.requireNonNull(addedSet).stream().map(i ->
//                    i.getUuid().toString()).collect(Collectors.joining("\n")));
//            grid.setItems(Objects.requireNonNull(inventoriesSdk.findMultipleInventory(null).block()));
        });
    }

    public Button deleteButton(Grid<Inventory> grid, Text uuidText) {
        return new Button("Delete Inventories", e -> {
//            Set<Inventory> inventorySet = inventoriesSelect(grid).getOptionalValue().orElse(Set.of(new Inventory()));
//            uuidText.setText("DELETE -> " + inventorySet.stream().map(i ->
//                            i.getUuid().toString()).collect(Collectors.joining("\n")));
//            inventoriesSdk.deleteMultipleInventory(inventorySet.stream().map(i -> i.getUuid().toString()).collect(Collectors.toSet()), null).block();
//            grid.setItems(Objects.requireNonNull(inventoriesSdk.findMultipleInventory(null).block()));
        });
    }

}
