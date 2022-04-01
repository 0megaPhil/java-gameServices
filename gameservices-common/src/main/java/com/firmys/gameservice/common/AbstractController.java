package com.firmys.gameservice.common;

import com.firmys.gameservice.common.error.GameServiceError;
import com.firmys.gameservice.common.error.GameServiceException;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

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

    public UUID getUuidFromBodyOrParam(GameEntity gameEntity, String uuidParam) {
        if (gameEntity != null) {
            return gameEntity.getUuid();
        } else if (uuidParam != null) {
            return UUID.fromString(uuidParam);
        } else {
            throw new RuntimeException("No uuid found in body or parameters");
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
     * @return set of {@link GameEntity} objects
     */
    public Set<D> findAll() {
        return this.entityCallableHandlerSet(() ->
                StreamSupport.stream(
                        gameService.findAll(Sort.unsorted()).spliterator(), false)
                        .collect(Collectors.toSet()), null);
    }

    public D save(@Nullable D entity) {
        return entityCallableHandler(() -> gameService.save(
                Optional.ofNullable(entity).orElse(entitySupplier.get())), entity);
    }

    public void delete(
            String uuidParam,
            D entityBody) {
        voidCallableHandler(() -> {
            String uuidString = Optional.ofNullable(uuidParam).orElse(entityBody.getUuid().toString());
            gameService.deleteById(gameDataLookup.getPrimaryKeyByUuid(uuidString));
            return null;
        }, entityBody, uuidParam);
    }

    public D findByUuid(String uuidString) {
        return entityCallableHandler(() -> gameService.findById(gameDataLookup.getPrimaryKeyByUuid(uuidString)).orElseThrow(
                () -> generateGameServiceException(
                        getErrorBuilder()
                                .withDescription(ErrorMessages.notFoundByUuid(uuidString)
                                        .apply(gameEntityClass)).build())), null, uuidString);
    }

    public void deleteByUuid(String uuidString) {
        voidCallableHandler(() -> {
            gameService.deleteById(gameDataLookup.getPrimaryKeyByUuid(uuidString));
            return null;
        }, null, uuidString);

    }

    /**
     * Update by entity. Requires that entity has uuid
     * @param entity the information to be updated
     * @return adjusted entity as recorded
     */
    public D update(D entity) {
        return entityCallableHandler(() -> gameService.save(GameDataUtils
                .update(gameEntityClass,
                        findByUuid(entity.getUuid().toString()),
                        entity)), entity);
    }

    /**
     * Update by uuid in path. Allows for an incomplete entity, that is, one without the uuid
     * @param uuidString uuid assigned to entity
     * @param entity the information to be updated
     * @return adjusted entity as recorded
     */
    public D updateByUuid(String uuidString, D entity) {
        return entityCallableHandler(() -> gameService.save(GameDataUtils
                .update(gameEntityClass,
                        findByUuid(entity.getUuid().toString()),
                        entity)), entity, uuidString);
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

}
