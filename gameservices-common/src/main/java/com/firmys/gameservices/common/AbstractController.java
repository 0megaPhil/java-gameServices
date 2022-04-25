package com.firmys.gameservices.common;

import com.firmys.gameservices.common.error.GameServiceError;
import com.firmys.gameservices.common.error.GameServiceException;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

import java.util.Arrays;
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

    public AbstractController(
            GameService<D> gameService,
            GameDataLookup<D> gameDataLookup,
            Class<D> gameEntityClass,
            Supplier<D> entitySupplier) {
        this.gameEntityClass = gameEntityClass;
        this.gameService = gameService;
        this.gameDataLookup = gameDataLookup;
        this.exceptionBuilder = GameServiceException.builder.withGameDataType(gameEntityClass);
        this.errorBuilder = GameServiceError.builder.withName(gameEntityClass.getSimpleName());
        this.entitySupplier = entitySupplier;
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

    protected Set<UUID> parseUuidParams(Set<String> uuidStringSet) {
        return parseUuidParams(uuidStringSet.toArray(new String[]{}));
    }

    protected Set<UUID> parseUuidParams(String... uuidStrings) {
        try {
            if (uuidStrings != null) {
                return Arrays.stream(uuidStrings).map(UUID::fromString).collect(Collectors.toSet());
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
        return this.entityCallableHandlerSet(() ->
                StreamSupport.stream(
                                gameService.findAll(Sort.unsorted()).spliterator(), false)
                        .collect(Collectors.toSet()), null);
    }

    // TODO - Put in checks for ensuring not overlap in records
    public D save(@Nullable D entity) {
        return entityCallableHandler(() -> gameService.save(
                Optional.ofNullable(entity).orElse(entitySupplier.get())), entity);
    }

    public Set<D> save(Set<D> entities) {
        return entities.stream().map(this::save).collect(Collectors.toSet());
    }

    public void delete(
            UUID uuid,
            D entityBody) {
        voidCallableHandler(() -> {
            gameService.deleteById(
                    gameDataLookup.getPrimaryKeyByUuid(parseUuid(uuid, entityBody)));
            return null;
        }, entityBody, uuid.toString());
    }

    public D findByUuid(UUID uuid) {
        return entityCallableHandler(() -> gameService.findById(gameDataLookup.getPrimaryKeyByUuid(uuid)).orElseThrow(
                () -> generateGameServiceException(
                        getErrorBuilder()
                                .withDescription(ErrorMessages.notFoundByUuid(uuid)
                                        .apply(gameEntityClass)).build())), null, uuid.toString());
    }

    public Set<D> findByUuids(Set<UUID> uuids) {
        return uuids.stream().map(this::findByUuid).collect(Collectors.toSet());
    }

    public void deleteByUuid(UUID uuid) {
        voidCallableHandler(() -> {
            gameService.deleteById(gameDataLookup.getPrimaryKeyByUuid(uuid));
            return null;
        }, null, uuid.toString());
    }

    public void deleteByUuids(Set<UUID> uuids) {
        uuids.forEach(this::deleteByUuid);
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
                        findByUuid(entity.getUuid()),
                        entity)), entity);
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
    public D updateByUuid(UUID uuid, D entity) {
        return entityCallableHandler(() -> gameService.save(GameDataUtils
                .update(gameEntityClass,
                        findByUuid(entity.getUuid()),
                        entity)), entity, uuid.toString());
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
        return uuids.stream().map(u -> updateByUuid(u, entity)).collect(Collectors.toSet());
    }

    public Set<String> getEntityAttributes() {
        return GameDataUtils.getEntityAttributes(entitySupplier.get());
    }

    public Set<D> matchByAttributes(MatchStrategy strategy, Set<String> attributes, boolean partial) {
        Set<String> availableAttributes = getEntityAttributes();
        Map<String, String> attributeMap = attributes.stream()
                .filter(s -> availableAttributes.contains(s.split(":")[0])).map(s ->
                        Map.of(s.split(":")[0], s.split(":")[1]))
                .flatMap(m -> m.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if(strategy == MatchStrategy.ANY) {
            return matchByAnyAttributes(attributeMap, partial);
        }
        return matchByAllAttributes(attributeMap, partial);
    }

    public Set<D> matchByAllAttributes(Map<String, String> attributeMap, boolean partial) {
        return entityCallableHandlerSet(
                () -> GameDataUtils.matchByAllAttributes(attributeMap, findAll(), partial),
                null);
    }

    public Set<D> matchByAnyAttributes(Map<String, String> attributeMap, boolean partial) {

        return entityCallableHandlerSet(
                () -> GameDataUtils.matchByAnyAttributes(attributeMap, findAll(), partial),
                null);
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
//            D entity = entitySupplier.get();
//            entity.setError(GameServiceError.builder.withThrowable(e)
//                    .withName(gameEntityClass.getSimpleName())
//                    .withDescription(e.getMessage() + " - " + String.join("", details))
//                    .withRequest(requestBody)
//                    .build());
//            return entity;
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

    private UUID parseUuid(UUID uuid, D entityBody) {
        return Optional.ofNullable(uuid).orElse(Optional.ofNullable(entityBody).orElse(entitySupplier.get()).getUuid());
    }

}
