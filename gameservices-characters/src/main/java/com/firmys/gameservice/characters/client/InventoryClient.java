package com.firmys.gameservice.characters.client;

import com.firmys.gameservice.common.GameServiceProperties;
import com.firmys.gameservice.common.ServiceConstants;
import com.firmys.gameservice.common.ServiceProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class InventoryClient {
    private final GameServiceProperties properties;
    private final RestTemplate restTemplate;
    private String path;
    private ServiceProperties props;

    public InventoryClient(GameServiceProperties properties) {
        this.properties = properties;
        this.restTemplate = new RestTemplateBuilder().build();
        this.props = properties.getServices().stream()
                .filter(p -> p.getName().contains(ServiceConstants.INVENTORY.toLowerCase())).findFirst().orElseThrow();
        this.path = "http://" + props.getHost() + ":" + props.getPort() + "/" + ServiceConstants.INVENTORY;
    }

    // TODO - do
    public <T> void request() {
//        this.restTemplate.postForObject(path, )
    }

}
