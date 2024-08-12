package com.firmys.gameservices.common.data;

import static java.lang.String.format;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DataBaseService {

  public static final String SQL_GET_TASK = "select * from tasks";
  private final ConnectionFactory connection;

  public static String sqlCreateDb(String db) {
    String sql = "create database %1$s;";
    String[] sql1OrderedParams = quotify(new String[] {db});
    String finalSql = format(sql, (Object[]) sql1OrderedParams);
    return finalSql;
  }

  public static String sqlCreateSchema(String schema) {
    String sql = "create schema if not exists %1$s;";
    String[] sql1OrderedParams = quotify(new String[] {schema});
    return format(sql, (Object[]) sql1OrderedParams);
  }

  public static String sqlCreateTable(String schema, String table) {

    String sql1 =
        "create table %1$s.%2$s "
            + "(id serial not null constraint tasks_pk primary key, "
            + "lastname varchar not null); ";
    String[] sql1OrderedParams = quotify(new String[] {schema, table});
    String sql1Final = format(sql1, (Object[]) sql1OrderedParams);

    String sql2 = "alter table %1$s.%2$s owner to root; ";
    String[] sql2OrderedParams = quotify(new String[] {schema, table});
    String sql2Final = format(sql2, (Object[]) sql2OrderedParams);

    return sql1Final + sql2Final;
  }

  public static String sqlPopulateTable(String schema, String table) {

    String sql = "insert into %1$s.%2$s values (1, 'schema-table-%3$s');";
    String[] sql1OrderedParams = quotify(new String[] {schema, table, schema});
    return format(sql, (Object[]) sql1OrderedParams);
  }

  private static String[] quotify(String[] stringArray) {

    String[] returnArray = new String[stringArray.length];

    for (int i = 0; i < stringArray.length; i++) {
      returnArray[i] = "\"" + stringArray[i] + "\"";
    }
    return returnArray;
  }

  private Mono<Void> createDb(String db) {
    DatabaseClient dbClient = DatabaseClient.create(connection);
    return dbClient.sql(sqlCreateDb(db)).then();
  }

  public Flux<Object> createDbByDb(String db, String schema, String table) {
    return createDb(db)
        .thenMany(
            Mono.from(connection.create())
                .flatMapMany(
                    connection ->
                        Flux.from(
                            connection
                                .createBatch()
                                .add(sqlCreateSchema(schema))
                                .add(sqlCreateTable(db, table))
                                .add(sqlPopulateTable(db, table))
                                .execute())));
  }
}
