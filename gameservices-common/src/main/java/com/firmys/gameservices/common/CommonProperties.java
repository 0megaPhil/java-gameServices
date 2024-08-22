package com.firmys.gameservices.common;

import jakarta.annotation.Nonnull;
import java.util.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "gameservices")
public class CommonProperties {

  private Services type;
  private Service config;
  private Service gateway;
  private Service current;
  private Map<Services, Service> services = new HashMap<>();

  public Service service(@Nonnull Services service) {
    return services.get(service);
  }

  @Data
  public static final class Service {
    private String id;
    private String name;
    private String host;
    private String port;
    private String username;
    private String password;
    private String protocol;

    public String baseUri() {
      return protocol() + "://" + host + ":" + port;
    }

    public String id() {
      return name + "Service";
    }

    public String endpoint() {
      return baseUri() + "/" + id;
    }

    public String graphQl() {
      return baseUri() + "/graphql";
    }

    public String protocol() {
      return Optional.ofNullable(protocol).orElse("https");
    }
  }
}
