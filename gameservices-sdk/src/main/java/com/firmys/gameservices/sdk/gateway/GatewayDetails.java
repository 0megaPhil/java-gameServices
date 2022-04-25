package com.firmys.gameservices.sdk.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GatewayDetails {

    @Value("${gameservices.gateway.host}")
    private String gatewayHost;
    @Value("${gameservices.gateway.port}")
    private String gatewayPort;

    public GatewayDetails() {
    }

    public String getGatewayAddress() {
        return "http://" + gatewayHost + ":" + gatewayPort;
    }

    public String getGatewayPort() {
        return gatewayPort;
    }

    public String getGatewayHost() {
        return gatewayHost;
    }
}
