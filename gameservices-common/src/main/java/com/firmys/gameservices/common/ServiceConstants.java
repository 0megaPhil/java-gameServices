package com.firmys.gameservices.common;

public class ServiceConstants {

  public static final String VERSION = "0.1.0-SNAPSHOT";
  public static final String GAME_SERVICES_GATEWAY = "GameServicesGateway";
  public static final String CACHE_MANAGER_SUFFIX = "CacheManager";
  public static final String ITEM = "item";
  public static final String ITEM_PATH = "/" + ITEM;
  public static final String ITEMS = "items";
  public static final String ITEMS_PATH = "/" + ITEMS;
  public static final String TRANSACTION = "transaction";
  public static final String TRANSACTION_PATH = "/" + TRANSACTION;
  public static final String TRANSACTIONS = "transactions";
  public static final String TRANSACTIONS_PATH = "/" + TRANSACTIONS;

  public static final String CHARACTER = "character";
  public static final String CHARACTER_SERVICE = "CharacterService";
  public static final String CHARACTER_PATH = "/" + CHARACTER;
  public static final String CHARACTERS = "characters";
  public static final String CHARACTERS_PATH = "/" + CHARACTERS;

  public static final String INVENTORY = "inventory";
  public static final String INVENTORY_SERVICE = "InventoryService";
  public static final String INVENTORY_PATH = "/" + INVENTORY;
  public static final String INVENTORIES = "inventories";
  public static final String INVENTORIES_PATH = "/" + INVENTORIES;
  public static final String CONSUMABLE_ITEM = "consumableItem";
  public static final String TRANSACTIONAL_CURRENCY = "transactionalCurrency";
  public static final String ID = "Id";

  public static final String CURRENCY = "currency";
  public static final String CURRENCY_PATH = "/" + CURRENCY;
  public static final String CURRENCIES = "currencies";
  public static final String CURRENCIES_PATH = "/" + CURRENCIES;

  public static final String WORLD = "world";
  public static final String WORLDS = "worlds";
  public static final String WORLD_SERVICE = "WorldService";
  public static final String WORLD_PATH = "/" + WORLD;
  public static final String WORLDS_PATH = "/" + WORLDS;

  public static final String UUID = "uuid";
  public static final String PATH_UUID = "pathUuid";
  public static final String UUID_PATH_VARIABLE = "/" + "{" + ServiceConstants.PATH_UUID + "}";

  public static final String AMOUNT = "amount";
  public static final String ATTRIBUTE = "attribute";
  public static final String ATTRIBUTES = "attributes";
  public static final String MATCH = "match";
  public static final String PARTIAL = "partial";
  public static final String EXACT = "exact";
  public static final String STRATEGY = "strategy";
  public static final String PARTIAL_PATH = "/" + PARTIAL;
  public static final String SEARCH = "search";
  public static final String SEARCH_PATH = "/" + SEARCH;
  public static final String QUERY = "query";
  public static final String QUERY_PATH = "/" + QUERY;
  public static final String MATCH_PATH_VARIABLE = "/" + "{" + ServiceConstants.MATCH + "}";

  public static final String OWNED_ITEMS = "OwnedItems";
  public static final String OWNED_CURRENCIES = "OwnedCurrencies";

  public static final String OWNED_ITEM = "OwnedItem";
  public static final String OWNED_CURRENCY = "OwnedCurrency";

  public static final String URL = "url";
  public static final String URI = "uri";

  public static final String CREDIT = "credit";
  public static final String DEBIT = "debit";
  public static final String CREDIT_PATH = "/" + CREDIT;
  public static final String DEBIT_PATH = "/" + DEBIT;

  public static final String ADD = "add";
  public static final String CONSUME = "consume";
  public static final String ADD_PATH = "/" + ADD;
  public static final String CONSUME_PATH = "/" + CONSUME;
}
