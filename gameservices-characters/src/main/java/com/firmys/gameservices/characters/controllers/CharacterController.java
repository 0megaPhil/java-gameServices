package com.firmys.gameservices.characters.controllers;

import com.firmys.gameservices.characters.service.CharacterDataLookup;
import com.firmys.gameservices.characters.service.CharacterService;
import com.firmys.gameservices.characters.service.data.Character;
import com.firmys.gameservices.characters.service.data.QCharacter;
import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.ServiceStrings;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CharacterController extends AbstractController<Character> {

    public CharacterController(
            CharacterService service,
            CharacterDataLookup dataLookup,
            EntityManager entityManager) {
        super(service, dataLookup, Character.class, Character::new, QCharacter.character, entityManager);
    }

    /**
     * {@link ServiceStrings#CHARACTERS_PATH}
     */
    @GetMapping(ServiceStrings.CHARACTERS_PATH)
    public Set<Character> findSet(
            @RequestParam(value = ServiceStrings.UUID, required = false) Set<UUID> uuidParams) {
        return uuidParams == null ? super.findAll() : super.find(uuidParams);
    }

    @PostMapping(value = ServiceStrings.CHARACTERS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Character> createSet(
            @RequestBody Set<Character> currencies) {
        return currencies.stream().map(super::save).collect(Collectors.toSet());
    }

    @DeleteMapping(value = ServiceStrings.CHARACTERS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSet(
            @RequestParam(value = ServiceStrings.UUID) Set<UUID> uuidParams) {
        super.delete(uuidParams);
    }

    @PutMapping(value = ServiceStrings.CHARACTERS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Character> updateSet(
            @RequestBody Set<Character> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServiceStrings#CHARACTER_PATH}
     */
    @GetMapping(value = ServiceStrings.CHARACTER_PATH + ServiceStrings.UUID_PATH_VARIABLE)
    public Character find(
            @PathVariable(ServiceStrings.PATH_UUID) UUID uuidPathVar) {
        return super.find(uuidPathVar);
    }

    @PostMapping(value = ServiceStrings.CHARACTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Character create(@RequestBody(required = false) Character entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceStrings.CHARACTER_PATH + ServiceStrings.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(ServiceStrings.PATH_UUID) UUID uuidPathVar) {
        super.delete(uuidPathVar);
    }

    @PutMapping(value = ServiceStrings.CHARACTER_PATH + ServiceStrings.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Character update(@PathVariable(ServiceStrings.PATH_UUID) UUID uuidPathVar,
                            @RequestBody Character entity) {
        return super.update(uuidPathVar, entity);
    }

    /**
     * {@link ServiceStrings#CHARACTERS_PATH}/{@link ServiceStrings#QUERY_PATH}
     * @param queryMap key value style attributes such as http://url:port/path?key0=val0&key1=val1
     * @return set of entities which match attribute key and value restrictions
     */
    @GetMapping(value = ServiceStrings.CHARACTERS_PATH + ServiceStrings.QUERY_PATH)
    public Set<Character> findAll(
            @RequestParam Map<String, String> queryMap) {
        return super.findAll(queryMap);
    }

}
