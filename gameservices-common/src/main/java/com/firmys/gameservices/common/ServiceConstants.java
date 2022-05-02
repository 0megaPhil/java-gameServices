package com.firmys.gameservices.common;

public class ServiceConstants {

    public static final String VERSION = "0.1.0-SNAPSHOT";
    public static final String GAME_SERVICES_GATEWAY = "GameServicesGateway";
    public static final String CACHE_MANAGER_SUFFIX = "CacheManager";
    public static final String ITEM = "item";
    public static final String ITEM_PATH = "/" + ITEM;
    public static final String ITEMS = "items";
    public static final String ITEMS_PATH = "/" + ITEMS;

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

    public static final String CURRENCY = "currency";
    public static final String CURRENCY_PATH = "/" + CURRENCY;
    public static final String CURRENCIES = "currencies";
    public static final String CURRENCIES_PATH = "/" + CURRENCIES;

    public static final String WORLD = "world";
    public static final String WORLDS = "worlds";
    public static final String WORLD_SERVICE = "WorldService";
    public static final String WORLD_PATH = "/" + WORLD;
    public static final String WORLDS_PATH = "/" + WORLDS;

    public final static String UUID = "uuid";
    public final static String PATH_UUID = "pathUuid";
    public final static String UUID_PATH_VARIABLE = "/" + "{" + ServiceConstants.PATH_UUID + "}";

    public final static String AMOUNT = "amount";
    public final static String ATTRIBUTE = "attribute";
    public final static String ATTRIBUTES = "attributes";
    public final static String MATCH = "match";
    public final static String PARTIAL = "partial";
    public final static String EXACT = "exact";
    public final static String STRATEGY = "strategy";
    public final static String PARTIAL_PATH = "/" + PARTIAL;
    public final static String SEARCH = "search";
    public final static String SEARCH_PATH = "/" + SEARCH;
    public final static String QUERY = "query";
    public final static String QUERY_PATH = "/" + QUERY;
    public final static String MATCH_PATH_VARIABLE = "/" + "{" + ServiceConstants.MATCH + "}";

    public final static String OWNED_ITEMS = "OwnedItems";
    public final static String OWNED_CURRENCIES = "OwnedCurrencies";

    public final static String OWNED_ITEM = "OwnedItem";
    public final static String OWNED_CURRENCY = "OwnedCurrency";

    public final static String URL = "url";
    public final static String URI = "uri";

    public final static String CREDIT = "credit";
    public final static String DEBIT = "debit";
    public final static String CREDIT_PATH = "/" + CREDIT;
    public final static String DEBIT_PATH = "/" + DEBIT;

    public final static String ADD = "add";
    public final static String CONSUME = "consume";
    public final static String ADD_PATH = "/" + ADD;
    public final static String CONSUME_PATH = "/" + CONSUME;

}
