package com.firmys.gameservice.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@ConfigurationProperties(prefix = "gameservice")
public class GameServiceProperties {

    private List<ServiceProperties> services;

    public List<ServiceProperties> getServices() {
        return services;
    }

    public void setServices(List<ServiceProperties> services) {
        this.services = services;
    }

}
