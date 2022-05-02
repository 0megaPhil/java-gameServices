package com.firmys.gameservices.common;

import com.firmys.gameservices.common.error.GameServiceError;
import com.firmys.gameservices.common.error.GameServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AbstractController<D extends AbstractGameEntity> {
    private final GameService<D> gameService;
    private final GameDataLookup<D> gameDataLookup;
    private final Supplier<D> entitySupplier;
    private final Class<D> gameEntityClass;
    private final EntityPathBase<D> queryClass;
    private final EntityManager entityManager;
    private final Supplier<JPAQuery<D>> querySupplier;

    public AbstractController(
            GameService<D> gameService,
            GameDataLookup<D> gameDataLookup,
            Class<D> gameEntityClass,
            Supplier<D> entitySupplier,
            EntityPathBase<D> queryClass,
            EntityManager entityManager) {
        this.gameEntityClass = gameEntityClass;
        this.gameService = gameService;
        this.gameDataLookup = gameDataLookup;
        this.entitySupplier = entitySupplier;
        this.queryClass = queryClass;
        this.entityManager = entityManager;
        this.querySupplier = () -> new JPAQuery<D>(entityManager).from(queryClass);
    }

    public GameService<D> getGameService() {
        return gameService;
    }

    public GameDataLookup<D> getGameDataLookup() {
        return gameDataLookup;
    }

    /**
     * Find all objects for specific {@link GameEntity}
     *
     * @return set of {@link GameEntity} objects
     */
    public Set<D> findAll() {
        return findAll(Sort.unsorted());
    }

    public Set<D> findAll(Sort sort) {
        return this.entityCallableHandlerSet(() ->
                        StreamSupport.stream(
                                        gameService.findAll(sort).spliterator(), false)
                                .collect(Collectors.toSet()), null,
                "Unable to find any entities for type " + gameEntityClass.getSimpleName());
    }

    public Set<D> findAll(Pageable pageable) {
        return this.entityCallableHandlerSet(() ->
                        StreamSupport.stream(
                                        gameService.findAll(pageable).spliterator(), false)
                                .collect(Collectors.toSet()), null,
                "Unable to find any entities for type " + gameEntityClass.getSimpleName());
    }

    public D save() {
        return save(entitySupplier.get());
    }

    public D save(D entity) {
        D findOrGenerate = Optional.ofNullable(entity).orElse(entitySupplier.get());
        return entityCallableHandler(() -> {
                    return gameService.save(findOrGenerate);
                }, findOrGenerate,
                "Unable to either create or update entity of type " + gameEntityClass.getSimpleName());
    }

    public Set<D> save(Set<D> entities) {
        Set<D> found = findAll();
        return this.entityCallableHandlerSet(() ->
                        StreamSupport.stream(
                                        gameService.saveAll(entities).spliterator(), false)
                                .collect(Collectors.toSet()), null,
                "Unable to create entities of type " + gameEntityClass.getSimpleName(),
                entities.stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    public D find(UUID uuid) {
        return entityCallableHandler(() -> gameService.findById(gameDataLookup.getPrimaryKeyByUuid(uuid))
                        .orElseThrow(() -> new RuntimeException(uuid +
                                " not found for entity type " + gameEntityClass.getSimpleName())),
                null, uuid.toString() +
                        " not found for entity type " + gameEntityClass.getSimpleName());
    }

    public Set<D> findAll(Set<UUID> uuids) {
        return this.entityCallableHandlerSet(() ->
                        StreamSupport.stream(
                                        gameService.findAllById(uuids.stream().map(u ->
                                                        getGameDataLookup().
                                                                getPrimaryKeyByUuid(u)).
                                                collect(Collectors.toSet())).spliterator(), false)
                                .collect(Collectors.toSet()), null,
                "Unable to find any entities for type " + gameEntityClass.getSimpleName());
    }

    /*
     * TODO - Consider completely revamping this
     */
    public Set<D> findAll(Map<String, String> queryMap) {
        if (queryMap.containsKey(ServiceConstants.UUID) && queryMap.size() < 2) {
            return Set.of(find(UUID.fromString(queryMap.get(ServiceConstants.UUID))));
        } else {
            queryMap.remove(ServiceConstants.UUID);
        }
        JPAQuery<D> query = getQuerySupplier().get();
        BooleanBuilder combined = new BooleanBuilder();
        Set<BooleanExpression> expressions = getExpressionsForQuery(queryMap);
        expressions.forEach(combined::and);
        Predicate predicate = combined.getValue();
        return new HashSet<>(query.where(predicate).fetch());
    }

    public Set<D> findAll(Predicate predicate) {
        return StreamSupport.stream(
                gameService.findAll(predicate).spliterator(), false).collect(Collectors.toSet());
    }

    public void delete(UUID uuid) {
        voidCallableHandler(() -> {
            gameService.deleteById(gameDataLookup.getPrimaryKeyByUuid(uuid));
            return null;
        }, null, uuid.toString() +
                " not found for entity type " + gameEntityClass.getSimpleName());
    }

    public void delete(Set<UUID> uuids) {
        uuids.forEach(this::delete);
    }

    /**
     * Update by entity. Requires that entity has uuid
     *
     * @param entity the information to be updated
     * @return adjusted entity as recorded
     */
    public D update(D entity) {
        return entityCallableHandler(() -> gameService.save(GameDataUtils
                .update(gameEntityClass,
                        find(entity.getUuid()),
                        entity)), entity, "Unable to update entity " + entity.getUuid() +
                " of type " + gameEntityClass.getSimpleName() + " with details " + entity);
    }

    public Set<D> update(Set<D> entities) {
        return entities.stream().map(this::update).collect(Collectors.toSet());
    }

    /**
     * Update by uuid in path. Allows for an incomplete entity, that is, one without the uuid
     *
     * @param uuid   uuid assigned to entity
     * @param entity the information to be updated
     * @return adjusted entity as recorded
     */
    public D update(UUID uuid, D entity) {
        return entityCallableHandler(() -> gameService.save(GameDataUtils
                .update(gameEntityClass,
                        find(entity.getUuid()),
                        entity)), entity, "Unable to update " + uuid.toString()
                + " of type " + gameEntityClass.getSimpleName() + " with details " + entity);
    }

    /*
     * FIXME - This is going to turn into tech debt, I should redo this once I know more about QueryDsl
     *  I want to be able to handle different path types dynamically, but maybe I should just rely on building
     *  a Predicate and using that
     */
    public Set<BooleanExpression> getExpressionsForQuery(Map<String, String> queryMap) {
        Map<String, Class<?>> queryValueClasses = queryMap
                .keySet().stream().map(s -> {
                    try {
                        return Map.entry(s, entitySupplier.get().getClass().getDeclaredField(s).getType());
                    } catch (NoSuchFieldException ex) {
                        return Map.entry(s, Void.TYPE);
                    }
                }).filter(e -> !e.getValue().equals(Void.TYPE))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return queryValueClasses.entrySet().stream().filter(c -> !c.getValue().equals(UUID.class)).map(c -> {
            if (c.getValue().equals(String.class)) {
                return Expressions.stringPath(getQueryClass(),
                                c.getKey().toLowerCase())
                        .like(queryMap.get(c.getKey()));
            } else {
                if (c.getValue().equals(int.class) || c.getValue().equals(Integer.class)) {
                    return Expressions.numberPath(Integer.class,
                                    c.getKey())
                            .like(queryMap.get(c.getKey()));
                } else if (c.getValue().equals(double.class) || c.getValue().equals(Double.class)) {
                    return Expressions.numberPath(Double.class,
                                    c.getKey())
                            .like(queryMap.get(c.getKey()));
                } else if (c.getValue().equals(long.class) || c.getValue().equals(Long.class)) {
                    return Expressions.numberPath(Long.class,
                                    c.getKey())
                            .like(queryMap.get(c.getKey()));
                } else if (c.getValue().equals(float.class) || c.getValue().equals(Float.class)) {
                    return Expressions.numberPath(Float.class,
                                    c.getKey())
                            .like(queryMap.get(c.getKey()));
                } else {
                    return Expressions.stringPath(getQueryClass(),
                                    c.getKey().toLowerCase())
                            .like(queryMap.get(c.getKey()));
                }
            }
        }).collect(Collectors.toSet());
    }

    /*
     * Callable handlers for managing exceptions
     */
    public D entityCallableHandler(Callable<D> callable,
                                   @Nullable D requestBody,
                                   String... details) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new GameServiceException(e, new GameServiceError<>(
                    e.getMessage() +
                            "\n" + String.join("\n", details),
                    Optional.ofNullable(requestBody).orElse(entitySupplier.get())));
        }
    }

    public Set<D> entityCallableHandlerSet(Callable<Set<D>> callable,
                                           @Nullable D requestBody,
                                           String... details) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new GameServiceException(e, new GameServiceError<>(
                    e.getMessage() +
                            "\n" + String.join("\n", details),
                    Optional.ofNullable(requestBody).orElse(entitySupplier.get())));
        }
    }

    public void voidCallableHandler(Callable<Void> callable,
                                    @Nullable D requestBody,
                                    String... details) {
        try {
            callable.call();
        } catch (Exception e) {
            throw new GameServiceException(e, new GameServiceError<>(
                    e.getMessage() +
                            "\n" + String.join("\n", details),
                    Optional.ofNullable(requestBody).orElse(entitySupplier.get())));
        }
    }

    public EntityPathBase<D> getQueryClass() {
        return queryClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Supplier<JPAQuery<D>> getQuerySupplier() {
        return querySupplier;
    }
}
