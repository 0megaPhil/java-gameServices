package com.firmys.gameservice.characters.service;

import com.firmys.gameservice.characters.service.data.Character;
import com.firmys.gameservice.common.GameData;
import com.firmys.gameservice.common.GameServiceError;
import com.firmys.gameservice.common.GameServiceProperties;
import com.firmys.gameservice.common.ServiceConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
public class CharacterController {
    private static final String baseDataName = ServiceConstants.CHARACTER;
    private static final String basePath = "/" + ServiceConstants.CHARACTER;
    private static final String uuidPathVariable = "{" + ServiceConstants.UUID + "}";
    private static final String uuidPath = "/" + uuidPathVariable;


    private final CharacterService service;
    private final CharacterDataLookup dataLookup;

    public CharacterController(
            CharacterService service,
            CharacterDataLookup dataLookup) {
        this.service = service;
        this.dataLookup = dataLookup;
    }

    @GetMapping(value = basePath)
    public Set<Character> findAll() {
        return StreamSupport.stream(service.findAll(Sort.unsorted()).spliterator(), false)
                .collect(Collectors.toSet());
    }

    @PostMapping(basePath)
    public GameData save(@RequestBody Character entity) {
        if(findAll().stream().noneMatch(
                c -> c.getName().equals(entity.getName()) ||
                c.getUuid().equals(entity.getUuid()) ||
                c.getInventoryId() == entity.getInventoryId())) {
            return service.save(entity);
        } else {
            return new GameServiceError(entity, baseDataName + ": " + " Adding not allowed",
                    "Matches existing entry " +
                    "ensure unique", null);
        }
    }

    @DeleteMapping(basePath)
    public void delete(@RequestBody Character entity) {
        UUID uuid = findAll().stream()
                .filter(c -> c.getName().equals(entity.getName())).findFirst().orElseThrow(
                        () -> new RuntimeException(
                                new GameServiceError(entity, baseDataName + ": " + " Deleting not allowed",
                                        "No matching record found", null).toString())).getUuid();
        service.deleteById(dataLookup.getPrimaryKeyByUuid(uuid));
    }

    @GetMapping(basePath + uuidPath)
    public Character findByUuid(@PathVariable(uuidPathVariable) String uuidString) {
        return service.findById(dataLookup.getPrimaryKeyByUuid(uuidString)).orElseThrow(
                () -> new RuntimeException(
                new GameServiceError(null, baseDataName + ": " + " not found by uuid",
                        "No matching record found", null).toString()));
    }

    @DeleteMapping(basePath + uuidPath)
    public void deleteByUuid(@PathVariable(uuidPathVariable) String uuidString) {
        service.deleteById(dataLookup.getPrimaryKeyByUuid(uuidString));
    }

    @PutMapping(basePath)
    public Character update(@RequestBody Character entity) {
        Character character = findByUuid(entity.getUuid().toString());
        character.setAge(entity.getAge());
        character.setHeight(entity.getHeight());
        character.setName(entity.getName());
        character.setWeight(entity.getWeight());
        character.setGender(entity.getGender());
        character.setDescription(entity.getDescription());
        character.setInventoryId(entity.getInventoryId());
        service.save(character);
        return dataLookup.getDataByUuid(character.getUuid());
    }

    @PutMapping(basePath + uuidPath)
    public Character updateByUuid(@PathVariable(uuidPathVariable) String uuidString, @RequestBody Character entity) {
        Character character = findByUuid(uuidString);
        character.setAge(entity.getAge());
        character.setHeight(entity.getHeight());
        character.setName(entity.getName());
        character.setWeight(entity.getWeight());
        character.setGender(entity.getGender());
        character.setDescription(entity.getDescription());
        character.setInventoryId(entity.getInventoryId());
        service.save(character);
        return findByUuid(character.getUuid().toString());
    }

}
