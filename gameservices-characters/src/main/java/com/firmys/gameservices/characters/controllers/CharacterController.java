package com.firmys.gameservices.characters.controllers;

import com.firmys.gameservices.characters.service.CharacterDataLookup;
import com.firmys.gameservices.characters.service.CharacterService;
import com.firmys.gameservices.characters.service.data.Character;
import com.firmys.gameservices.characters.service.data.QCharacter;
import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.ServiceConstants;
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

@RestController
public class CharacterController extends AbstractController<Character> {

    public CharacterController(
            CharacterService service,
            CharacterDataLookup dataLookup,
            EntityManager entityManager) {
        super(service, dataLookup, Character.class, Character::new, QCharacter.character, entityManager);
    }

    /**
     * {@link ServiceConstants#CHARACTERS_PATH}
     */
    @GetMapping(ServiceConstants.CHARACTERS_PATH)
    public Set<Character> findSet(
            @RequestParam(value = ServiceConstants.UUID, required = false) Set<UUID> uuidParams) {
        return uuidParams == null ? super.findAll() : super.findAll(uuidParams);
    }

    @PostMapping(value = ServiceConstants.CHARACTERS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Character> createSet(
            @RequestBody Set<Character> entities) {
        return super.save(entities);
    }

    @DeleteMapping(value = ServiceConstants.CHARACTERS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteSet(
            @RequestParam(value = ServiceConstants.UUID) Set<UUID> uuidParams) {
        super.delete(uuidParams);
    }

    @PutMapping(value = ServiceConstants.CHARACTERS_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<Character> updateSet(
            @RequestBody Set<Character> entities) {
        return super.update(entities);
    }

    /**
     * {@link ServiceConstants#CHARACTER_PATH}
     */
    @GetMapping(value = ServiceConstants.CHARACTER_PATH + ServiceConstants.UUID_PATH_VARIABLE)
    public Character find(
            @PathVariable(ServiceConstants.PATH_UUID) UUID uuidPathVar) {
        return super.find(uuidPathVar);
    }

    @PostMapping(value = ServiceConstants.CHARACTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Character create(@RequestBody(required = false) Character entity) {
        return super.save(entity);
    }

    @DeleteMapping(value = ServiceConstants.CHARACTER_PATH + ServiceConstants.UUID_PATH_VARIABLE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(ServiceConstants.PATH_UUID) UUID uuidPathVar) {
        super.delete(uuidPathVar);
    }

    @PutMapping(value = ServiceConstants.CHARACTER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Character update(@RequestBody Character entity) {
        return super.update(entity);
    }

    /**
     * {@link ServiceConstants#CHARACTERS_PATH}/{@link ServiceConstants#QUERY_PATH}
     * @param queryMap key value style attributes such as http://url:port/path?key0=val0&key1=val1
     * @return set of entities which match attribute key and value restrictions
     */
    @GetMapping(value = ServiceConstants.CHARACTERS_PATH + ServiceConstants.QUERY_PATH)
    public Set<Character> findAll(
            @RequestParam Map<String, String> queryMap) {
        return super.findAll(queryMap);
    }

}
