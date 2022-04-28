package com.firmys.gameservices.common;

import com.firmys.gameservices.common.error.GameServiceError;
import com.firmys.gameservices.common.error.GameServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import javax.persistence.EntityManager;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AbstractController<D extends AbstractGameEntity> {
    private final GameServiceException.Builder exceptionBuilder;
    private final GameServiceError.Builder errorBuilder;
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
        this.exceptionBuilder = GameServiceException.builder.withGameDataType(gameEntityClass);
        this.errorBuilder = GameServiceError.builder.withName(gameEntityClass.getSimpleName());
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

    protected UUID getUuidFromBodyOrParam(GameEntity gameEntity, String uuidParam) {
        try {
            if (gameEntity != null) {
                return gameEntity.getUuid();
            } else if (uuidParam != null) {
                return UUID.fromString(uuidParam);
            } else {
                throw new RuntimeException("No uuid found in body or parameters");
            }
        } catch (Exception e) {
            throw GameServiceException.builder
                    .withGameDataType(gameEntityClass)
                    .withGameServiceError(
                            GameServiceError.builder
                                    .withName(gameEntityClass.getSimpleName())
                                    .withDescription(e.getMessage())
                                    .withThrowable(e)
                                    .build()
                    )
                    .build();
        }
    }

    public GameServiceException.Builder getExceptionBuilder() {
        return exceptionBuilder;
    }

    public GameServiceError.Builder getErrorBuilder() {
        return errorBuilder;
    }

    public GameServiceException generateGameServiceException(GameServiceError gameServiceError) {
        return getExceptionBuilder().withGameServiceError(gameServiceError).build();
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
        return entityCallableHandler(() -> gameService.save(
                        findOrGenerate), findOrGenerate,
                "Unable to either create or update entity of type " + gameEntityClass.getSimpleName());
    }

    public Set<D> save(Set<D> entities) {
        return entities.stream().map(this::save).collect(Collectors.toSet());
    }

    public D find(UUID uuid) {
        return entityCallableHandler(() -> gameService.findById(gameDataLookup.getPrimaryKeyByUuid(uuid))
                        .orElseThrow(() -> new RuntimeException(uuid +
                                " not found for entity type " + gameEntityClass.getSimpleName())),
                null, uuid.toString() +
                        " not found for entity type " + gameEntityClass.getSimpleName());
    }

    public Set<D> find(Set<UUID> uuids) {
        return uuids.stream().map(this::find).collect(Collectors.toSet());
    }

    /*
     * TODO - Consider completely revamping this
     */
    public Set<D> findAll(Map<String, String> queryMap) {
        if(queryMap.containsKey(ServiceStrings.UUID) && queryMap.size() < 2) {
            return Set.of(find(UUID.fromString(queryMap.get(ServiceStrings.UUID))));
        } else {
            queryMap.remove(ServiceStrings.UUID);
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
                gameService.findAll(predicate).spliterator(), true).collect(Collectors.toSet());
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

    /**
     * Update multiple to match one or more fields of an entity
     * This is useful for updating many at once with a single set or subset of fields
     *
     * @param uuids  set of UUIDs which will be changed to match entity
     * @param entity entity containing details to be applied to existing entities by UUID
     * @return set of adjusted entities
     */
    public Set<D> updateForUuids(Set<UUID> uuids, D entity) {
        return uuids.stream().map(u -> update(u, entity)).collect(Collectors.toSet());
    }

    public Set<String> getEntityAttributes() {
        return GameDataUtils.getEntityAttributes(entitySupplier.get());
    }

    public Set<D> matchByAttributes(MatchStrategy strategy, Set<String> attributes, Boolean exactMatch) {
        Set<String> availableAttributes = getEntityAttributes();
        Map<String, String> attributeMap = attributes.stream()
                .filter(s -> availableAttributes.contains(s.split(":")[0])).map(s ->
                        Map.of(s.split(":")[0], s.split(":")[1]))
                .flatMap(m -> m.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (strategy == MatchStrategy.ANY) {
            return matchByAnyAttributes(attributeMap, exactMatch);
        }
        return matchByAllAttributes(attributeMap, exactMatch);
    }

    public Set<D> matchByAllAttributes(Map<String, String> attributeMap, Boolean exactMatch) {
        return entityCallableHandlerSet(
                () -> GameDataUtils.matchByAllAttributes(attributeMap, findAll(), exactMatch),
                null, "Cannot match ALL attributes of " +
                        attributeMap.entrySet().stream().map(e -> e.getKey() + "=" +
                                e.getValue()).collect(Collectors.joining(", \n")) + " for type " +
                        gameEntityClass.getSimpleName());
    }

    public Set<D> matchByAnyAttributes(Map<String, String> attributeMap, Boolean exactMatch) {

        return entityCallableHandlerSet(
                () -> GameDataUtils.matchByAnyAttributes(attributeMap, findAll(), exactMatch),
                null, "Cannot match ANY attributes of " +
                        attributeMap.entrySet().stream().map(e -> e.getKey() + "=" +
                                e.getValue()).collect(Collectors.joining(", \n")) + " for type " +
                        gameEntityClass.getSimpleName());
    }

    private Class<?> getQueryFieldTypeByKey(String key) {
        try {
            return getQueryClass().getClass().getDeclaredField(key).getType();
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("key of " + key + " not available in " + getQueryClass().getClass().getSimpleName());
        }
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
            throw GameServiceException.builder.withGameServiceError(
                    GameServiceError.builder.withThrowable(e)
                            .withName(gameEntityClass.getSimpleName())
                            .withDescription(e.getMessage() + " - " + String.join("", details))
                            .withRequest(requestBody)
                            .build()
            ).withGameDataType(gameEntityClass).build();
        }
    }

    public Set<D> entityCallableHandlerSet(Callable<Set<D>> callable,
                                           @Nullable D requestBody,
                                           String... details) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw GameServiceException.builder.withGameServiceError(
                    GameServiceError.builder.withThrowable(e)
                            .withName(gameEntityClass.getSimpleName())
                            .withDescription(e.getMessage() + " - " + String.join("", details))
                            .withRequest(requestBody)
                            .build()
            ).withGameDataType(gameEntityClass).build();
        }
    }

    protected Set<UUID> gatherUuids(Set<String> uuidParams, Set<D> entities) {
        Set<UUID> uuids = new HashSet<>();
        if (entities != null) {
            uuids.addAll(entities.stream().map(GameEntity::getUuid).collect(Collectors.toSet()));
        } else if (uuidParams != null) {
            uuids.addAll(uuidParams.stream().map(UUID::fromString).collect(Collectors.toSet()));
        }
        return uuids;
    }

    public void voidCallableHandler(Callable<Void> callable,
                                    @Nullable D requestBody,
                                    String... details) {
        try {
            callable.call();
        } catch (Exception e) {
            throw GameServiceException.builder.withGameServiceError(
                    GameServiceError.builder.withThrowable(e)
                            .withName(gameEntityClass.getSimpleName())
                            .withDescription(e.getMessage() + " - " + String.join("", details))
                            .withRequest(requestBody)
                            .build()
            ).withGameDataType(gameEntityClass).build();
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
