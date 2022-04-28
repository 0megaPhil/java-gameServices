package com.firmys.gameservices.characters.controllers;

import com.firmys.gameservices.characters.service.CharacterDataLookup;
import com.firmys.gameservices.characters.service.CharacterService;
import com.firmys.gameservices.characters.service.data.Character;
import com.firmys.gameservices.characters.service.data.QCharacter;
import com.firmys.gameservices.common.AbstractController;
import com.firmys.gameservices.common.MatchStrategy;
import com.firmys.gameservices.common.ServiceStrings;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
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

    // TODO - Implement and test
    @GetMapping(value = ServiceStrings.CHARACTERS_PATH + ServiceStrings.SEARCH_PATH)
    public Set<Character> searchByAttributes(
            @RequestParam(value = ServiceStrings.ATTRIBUTE) Set<String> attributes) {
        Map<String, String> attributeMap = attributes.stream()
                .filter(s -> attributes.contains(s.split(":")[0])).map(s ->
                        Map.of(s.split(":")[0], s.split(":")[1]))
                .flatMap(m -> m.entrySet().stream()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new HashSet<>(getQuerySupplier().get().where(attributeMap.entrySet().stream().map(entry -> Expressions.stringPath(getQueryClass(),
                        entry.getKey()).like(entry.getValue())).toArray(BooleanExpression[]::new))
                .fetch());
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

}
