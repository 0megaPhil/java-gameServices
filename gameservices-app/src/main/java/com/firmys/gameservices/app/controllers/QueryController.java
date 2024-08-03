package com.firmys.gameservices.app.controllers;

import static com.firmys.gameservices.common.JsonUtils.JSON;

import com.firmys.gameservices.app.services.QueryService;
import com.firmys.gameservices.characters.models.Character;
import com.firmys.gameservices.characters.models.Race;
import com.firmys.gameservices.characters.models.Skill;
import com.firmys.gameservices.characters.models.Stat;
import com.firmys.gameservices.common.CommonEntity;
import com.firmys.gameservices.common.CommonObject;
import com.firmys.gameservices.inventory.models.Inventory;
import com.firmys.gameservices.inventory.models.InventoryCurrency;
import com.firmys.gameservices.inventory.models.InventoryItem;
import com.firmys.gameservices.inventory.models.Item;
import com.firmys.gameservices.transactions.models.Currency;
import com.firmys.gameservices.users.models.User;
import com.firmys.gameservices.world.models.World;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
@RequiredArgsConstructor
public class QueryController {

  public static final String baseDataName = "error";
  public static final String gameServiceErrorBase = "/" + baseDataName;
  private final QueryService service;

  @QueryMapping
  public Flux<Character> allCharacters(@Argument Integer limit) {
    return service.getAll(limit, Character.class);
  }

  @QueryMapping
  public Flux<Skill> allSkills(@Argument Integer limit) {
    return service.getAll(limit, Skill.class);
  }

  @QueryMapping
  public Flux<Race> allRaces(@Argument Integer limit) {
    return service.getAll(limit, Race.class);
  }

  @QueryMapping
  public Flux<Stat> allStats(@Argument Integer limit) {
    return service.getAll(limit, Stat.class);
  }

  @QueryMapping
  public Mono<Character> characterById(@Argument UUID uuid) {
    return service.getById(uuid, Character.class);
  }

  @QueryMapping
  public Mono<Stat> createStat(@Argument Stat input) {
    return service.create(input);
  }

  @QueryMapping
  public Mono<Stat> updateStat(@Argument Stat input) {
    return service.update(input);
  }

  @QueryMapping
  public Mono<Skill> createSkill(@Argument Skill input) {
    return service.create(input);
  }

  @QueryMapping
  public Mono<Skill> updateSkill(@Argument Skill input) {
    return service.update(input);
  }

  @QueryMapping
  public Mono<Race> createRace(@Argument Race input) {
    return service.create(input);
  }

  @QueryMapping
  public Mono<Race> updateRace(@Argument Race input) {
    return service.update(input);
  }

  @QueryMapping
  public Mono<Character> createCharacter(@Argument Character input) {
    return service.create(input);
  }

  @QueryMapping
  public Mono<Character> updateCharacter(@Argument Character input) {
    return service.update(input);
  }

  @QueryMapping
  public Mono<Inventory> createInventory(@Argument Inventory input) {
    return service.create(input);
  }

  @QueryMapping
  public Mono<Inventory> updateInventory(@Argument Inventory input) {
    return service.update(input);
  }

  @QueryMapping
  public Mono<Item> createItem(@Argument Item input) {
    return service.create(input);
  }

  @QueryMapping
  public Mono<Item> updateItem(@Argument Item input) {
    return service.update(input);
  }

  @QueryMapping
  public Mono<Currency> createCurrency(@Argument Currency input) {
    return service.create(input);
  }

  @QueryMapping
  public Mono<Currency> updateCurrency(@Argument Currency input) {
    return service.update(input);
  }

  @QueryMapping
  public Mono<InventoryItem> createInventoryItem(@Argument InventoryItem input) {
    return service.create(input);
  }

  @QueryMapping
  public Mono<InventoryItem> updateInventoryItem(@Argument InventoryItem input) {
    return service.update(input);
  }

  @QueryMapping
  public Mono<InventoryCurrency> createInventoryCurrency(@Argument InventoryCurrency input) {
    return service.create(input);
  }

  @QueryMapping
  public Mono<InventoryCurrency> updateInventoryCurrency(@Argument InventoryCurrency input) {
    return service.update(input);
  }

  @QueryMapping
  public Flux<Character> charactersByName(@Argument String name) {
    return service.charactersByName(name);
  }

  @QueryMapping
  public Mono<Inventory> inventoryById(@Argument UUID uuid) {
    return service.getById(uuid, Inventory.class);
  }

  @QueryMapping
  public Mono<InventoryItem> inventoryItemById(@Argument UUID uuid) {
    return service.getById(uuid, InventoryItem.class);
  }

  @QueryMapping
  public Mono<InventoryCurrency> inventoryCurrencyById(@Argument UUID uuid) {
    return service.getById(uuid, InventoryCurrency.class);
  }

  @QueryMapping
  public Mono<Item> itemById(@Argument UUID uuid) {
    return service.getById(uuid, Item.class);
  }

  @QueryMapping
  public Mono<Currency> currencyById(@Argument UUID uuid) {
    return service.getById(uuid, Currency.class);
  }

  @QueryMapping
  public Mono<User> userById(@Argument UUID uuid) {
    return service.getById(uuid, User.class);
  }

  @QueryMapping
  public Mono<World> worldById(@Argument UUID uuid) {
    return service.getById(uuid, World.class);
  }

  @GetMapping(gameServiceErrorBase)
  public CommonObject getError(ServerHttpRequest serverHttpRequest) {
    return new CommonObject() {
      private final String message = serializeRequest(serverHttpRequest);

      public String getMessage() {
        return message;
      }
    };
  }

  @PostMapping(gameServiceErrorBase)
  public CommonObject postError(
      ServerHttpRequest serverHttpRequest,
      @RequestBody(required = false) CommonEntity requestBody) {
    return new CommonObject() {
      private final String message = serializeRequest(serverHttpRequest);
      private final CommonEntity entity = requestBody;

      public String getMessage() {
        return message;
      }

      public CommonEntity getEntity() {
        return entity;
      }
    };
  }

  @PutMapping(value = gameServiceErrorBase)
  public CommonObject putError(
      ServerHttpRequest serverHttpRequest,
      @RequestBody(required = false) CommonEntity requestBody) {
    return new CommonObject() {
      private final String message = serializeRequest(serverHttpRequest);
      private final CommonEntity entity = requestBody;

      public String getMessage() {
        return message;
      }

      public CommonEntity getEntity() {
        return entity;
      }
    };
  }

  @DeleteMapping(gameServiceErrorBase)
  public CommonObject deleteError(
      ServerHttpRequest serverHttpRequest,
      @RequestBody(required = false) CommonEntity requestBody) {
    return new CommonObject() {
      private final String message = serializeRequest(serverHttpRequest);
      private final CommonEntity entity = requestBody;

      public String getMessage() {
        return message;
      }

      public CommonEntity getEntity() {
        return entity;
      }
    };
  }

  private String serializeRequest(ServerHttpRequest serverHttpRequest) {
    String cookies = JSON.toJson(serverHttpRequest.getCookies());
    return JSON.toJson(
        Map.of(
            "Query Params",
            serverHttpRequest.getQueryParams(),
            "Id",
            serverHttpRequest.getId(),
            "Local Address",
            String.valueOf(Objects.requireNonNull(serverHttpRequest.getLocalAddress()).toString()),
            "Remote Address",
            String.valueOf(Objects.requireNonNull(serverHttpRequest.getRemoteAddress()).toString()),
            "Path",
            serverHttpRequest.getPath().toString(),
            "Cookies",
            cookies));
  }
}
