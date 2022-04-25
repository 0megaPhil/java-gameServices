/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.firmys.gameservices.webui;

import com.firmys.gameservices.common.ServiceStrings;
import com.firmys.gameservices.webui.views.InventoriesView;
import com.firmys.gameservices.webui.views.InventoryView;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route
public class HomeView extends VerticalLayout {

    public HomeView(@Autowired ExampleTemplate template) {
        H1 heading = new H1("Game Services UI");

        RouterLink inventoriesLink = new RouterLink(
                ServiceStrings.INVENTORIES,
                InventoriesView.class);

        RouterLink inventoryLink = new RouterLink(
                ServiceStrings.INVENTORY,
                InventoryView.class);

        Style linkStyle = inventoriesLink.getElement().getStyle();
        linkStyle.set("display", "block");
        linkStyle.set("margin-bottom", "10px");
        Style inventoryLinkStyle = inventoryLink.getElement().getStyle();
        inventoryLinkStyle.set("display", "block");
        inventoryLinkStyle.set("margin-bottom", "10px");
        add(heading, inventoriesLink, inventoryLink, template);
    }

}
