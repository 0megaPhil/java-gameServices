package com.firmys.gameservice.api;

import com.firmys.gameservice.api.impl.ServiceClient;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "gameservice")
public class GameServiceProperties {

    private List<Service> services;
    private final Map<ServiceClient, Service> serviceMap = new HashMap<>();

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
        this.services.forEach(s -> serviceMap
                .putIfAbsent(
                        Arrays.stream(ServiceClient.values()).filter(v ->
                                        v.name().toLowerCase().equals(s.name))
                                .findFirst().orElseThrow(), s));
    }

    public Map<ServiceClient, Service> getServiceMap() {
        return serviceMap;
    }

    public static class Service {
        private String name;
        private String host;
        private String uri;
        private String port;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }

}
