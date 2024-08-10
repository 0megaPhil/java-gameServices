package com.firmys.gameservices.common;

public class CommonConstants {

  public static final String VERSION = "0.0.2-SNAPSHOT";
  public static final String GAME_SERVICES_GATEWAY = "GameServicesGateway";
  public static final String GATEWAY = "gateway";
  public static final String ITEM = "item";
  public static final String ITEM_PATH = "/" + ITEM;
  public static final String TRANSACTION = "transaction";
  public static final String TRANSACTION_PATH = "/" + TRANSACTION;
  public static final String TRANSACTION_SERVICES = "TransactionService";

  public static final String CHARACTER = "character";
  public static final String CHARACTER_SERVICE = "CharacterService";
  public static final String CHARACTER_PATH = "/" + CHARACTER;

  public static final String SKILL = "skill";
  public static final String SKILL_SERVICE = "SkillService";
  public static final String SKILL_PATH = "/" + SKILL;

  public static final String STAT = "stat";
  public static final String STAT_SERVICE = "StatService";
  public static final String STAT_PATH = "/" + STAT;

  public static final String RACE = "race";
  public static final String RACE_SERVICE = "RaceService";
  public static final String RACE_PATH = "/" + RACE;

  public static final String FLAVOR = "text";

  public static final String INVENTORY = "inventory";
  public static final String INVENTORY_SERVICE = "InventoryService";
  public static final String INVENTORY_PATH = "/" + INVENTORY;

  public static final String ID = "Id";

  public static final String CURRENCY = "currency";
  public static final String CURRENCY_PATH = "/" + CURRENCY;

  public static final String WORLD = "world";
  public static final String WORLD_SERVICE = "WorldService";
  public static final String WORLD_PATH = "/" + WORLD;

  public static final String CONFIG = "config";
  public static final String CONFIG_SERVICE = "ConfigService";
  public static final String CONFIG_PATH = "/" + CONFIG;

  public static final String USER = "user";
  public static final String USERS_SERVICE = "UsersService";
  public static final String USER_PATH = "/" + USER;

  public static final String UUID = "uuid";
  public static final String PATH_UUID = "/" + "{" + CommonConstants.UUID + "}";
  public static final String LIMIT = "limit";

  public static final String URL = "url";
  public static final String URI = "uri";

  public static final String ADD = "add";
  public static final String CONSUME = "consume";
  public static final String ADD_PATH = "/" + ADD;
  public static final String CONSUME_PATH = "/" + CONSUME;

  public static final String PROFILE_SERVICE = "service";
  public static final String PROFILE_GATEWAY = "gateway";
  public static final String PROFILE_NOT_GATEWAY = "!gateway";
  public static final String PROFILE_TEST = "test";
  public static final String PROFILE_NOT_TEST = "!test";

  public static final String PACKAGE_GENERATED = "com.firmys.gameservices.generated";
}
