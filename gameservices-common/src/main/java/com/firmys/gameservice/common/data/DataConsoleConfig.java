package com.firmys.gameservice.common.data;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DataConsoleConfig {
    private org.h2.tools.Server webServer;
    private org.h2.tools.Server tcpServer;

    @EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
    public void start() throws java.sql.SQLException {
        // TODO - will need different ports for different services
        this.webServer = org.h2.tools.Server.createWebServer("-webPort", "8000", "-tcpAllowOthers").start();
        this.tcpServer = org.h2.tools.Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    @EventListener(org.springframework.context.event.ContextClosedEvent.class)
    public void stop() {
        this.tcpServer.stop();
        this.webServer.stop();
    }
}
