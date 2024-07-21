package com.firmys.gameservices.common;

import jakarta.annotation.Nonnull;
import java.util.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "gameservices")
public class CommonProperties {

  private Service gateway;
  private Set<Service> services = new HashSet<>();

  public Service service(@Nonnull String name) {
    return services.stream()
        .filter(service -> name.equals(service.getName()))
        .findFirst()
        .orElseThrow();
  }

  @Data
  public static final class Service {
    private String name;
    private String host;
    private String port;

    public String baseUri() {
      return "https://" + host + ":" + port;
    }

    public String id() {
      return name + "Service";
    }

    public String predicate() {
      return "Path=/" + name + "/**";
    }

    public String endpoint() {
      return baseUri() + "/" + name;
    }

    public String fallback() {
      return baseUri() + "/error";
    }
  }
}
