package com.firmys.gameservices.characters.controllers;

import com.firmys.gameservices.characters.service.CharacterDataLookup;
import com.firmys.gameservices.characters.service.CharacterService;
import com.firmys.gameservices.characters.service.data.Character;
import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.MatchStrategy;
import com.firmys.gameservices.common.ServiceStrings;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class CharacterController extends AbstractController<Character> {

    public CharacterController(
            CharacterService service,
            CharacterDataLookup dataLookup) {
        super(service, dataLookup, Character.class, Character::new);
    }

    /**
     * {@link ServiceStrings#CHARACTERS_PATH}
     * Multiple methods do not support UUID as path variable
     * <p>
     * Some methods, such as findMultiple, can collect UUIDs across parameters
     */
    @GetMapping(ServiceStrings.CHARACTERS_PATH)
    public Set<Character> findMultiple(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<String> uuidParams) {
        if (uuidParams != null) {
            return super.findByUuids(uuidParams.stream().map(UUID::fromString).collect(Collectors.toSet()));
        }
        return super.findAll();
    }

    @PostMapping(value = ServiceStrings.CHARACTERS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Character> addMultiple(@RequestBody(required = false) Set<Character> entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceStrings.CHARACTERS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteMultiple(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<String> uuidParams,
            @RequestBody(required = false) Set<Character> entities) {
        super.deleteByUuids(gatherUuids(uuidParams, entities));
    }

    @PutMapping(value = ServiceStrings.CHARACTERS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Character> updateMultiple(
            @RequestBody Set<Character> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServiceStrings#CHARACTER_PATH}
     * Singular methods support UUID as part of path
     */
    @GetMapping(value = ServiceStrings.CHARACTER_PATH)
    public Character findByUuidParam(
            @RequestParam(value = ServiceStrings.UUID) String uuidParam) {
        return super.findByUuid(UUID.fromString(uuidParam));
    }

    @GetMapping(value = ServiceStrings.CHARACTER_PATH + ServiceStrings.UUID_PATH_VARIABLE)
    public Character findByUuidPath(
            @PathVariable(ServiceStrings.UUID) String pathUuid) {
        return super.findByUuid(UUID.fromString(pathUuid));
    }

    @PostMapping(value = ServiceStrings.CHARACTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Character add(@RequestBody(required = false) Character entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceStrings.CHARACTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(
            @RequestParam(value = ServiceStrings.UUID, required = false) String uuidParam,
            @RequestBody(required = false) @Nullable Character entity) {
        super.delete(UUID.fromString(uuidParam), entity);
    }

    @DeleteMapping(value = ServiceStrings.CHARACTER_PATH + ServiceStrings.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteByUuid(@PathVariable(ServiceStrings.UUID) String pathUuid) {
        super.deleteByUuid(UUID.fromString(pathUuid));
    }

    @PutMapping(value = ServiceStrings.CHARACTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Character update(@RequestBody Character entity) {
        return super.update(entity);
    }

    @PutMapping(value = ServiceStrings.CHARACTER_PATH + ServiceStrings.UUID_PATH_VARIABLE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Character updateByUuid(@PathVariable(ServiceStrings.UUID) String pathUuid,
                                  @RequestBody Character entity) {
        return super.updateByUuid(UUID.fromString(pathUuid), entity);
    }

    /**
     * {@link ServiceStrings#CHARACTERS_PATH}
     * Complex-standard
     */
    @GetMapping(value = ServiceStrings.CHARACTERS_PATH + ServiceStrings.SEARCH_PATH)
    public Set<Character> searchByAttributes(
            @RequestParam(value = ServiceStrings.PARTIAL, required = false) String partial,
            @RequestParam(value = ServiceStrings.MATCH, required = false) String match,
            @RequestParam(value = ServiceStrings.ATTRIBUTE) Set<String> attributes) {

        return matchByAttributes(
                Optional.ofNullable(MatchStrategy.get(match)).orElse(MatchStrategy.ALL),
                attributes, Boolean.parseBoolean(
                        Optional.ofNullable(partial)
                                .orElse("false")));
    }

}
