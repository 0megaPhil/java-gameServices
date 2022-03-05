package com.firmys.gameservice.api.impl;

import com.firmys.gameservice.api.GameServiceProperties;
import com.firmys.gameservice.inventory.impl.Item;
import org.h2.util.json.JSONArray;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Lazy
public class InventoryClient implements Client {

    private static final ServiceClient SERVICE_CLIENT = ServiceClient.INVENTORY;
    private final RestTemplate restTemplate;
    private final GameServiceProperties.Service service;
    private final String rootUri;

    public InventoryClient(RestTemplate restTemplate, GameServiceProperties props) {
        this.restTemplate = restTemplate;
        this.service = props.getServiceMap().get(SERVICE_CLIENT);
        rootUri = "http://" + service.getHost() + ":" + service.getPort() + service.getUri();
    }

    public List<Item> getAll() {
        Item[] items = (restTemplate.getForEntity(rootUri + "/items", Item[].class).getBody());
        return Arrays.asList(Objects.requireNonNull(items));
    }

}
