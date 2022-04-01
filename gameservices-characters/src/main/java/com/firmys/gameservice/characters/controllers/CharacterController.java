package com.firmys.gameservice.characters.controllers;

import com.firmys.gameservice.characters.service.CharacterDataLookup;
import com.firmys.gameservice.characters.service.CharacterService;
import com.firmys.gameservice.characters.service.data.Character;
import com.firmys.gameservice.common.AbstractController;
import com.firmys.gameservice.common.GameServiceProperties;
import com.firmys.gameservice.common.ServiceConstants;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@EnableConfigurationProperties(GameServiceProperties.class)
public class CharacterController extends AbstractController<Character> {
    private static final String baseDataName = ServiceConstants.CHARACTER;
    private static final String basePath = "/" + ServiceConstants.CHARACTER;
    private static final String uuidPathVariable = "{" + ServiceConstants.UUID + "}";
    private static final String uuidPath = "/" + uuidPathVariable;

    private final CharacterService service;
    private final CharacterDataLookup dataLookup;

    public CharacterController(
            CharacterService service,
            CharacterDataLookup dataLookup) {
        super(service, dataLookup, Character.class, Character::new);
        this.service = service;
        this.dataLookup = dataLookup;
    }

    @GetMapping(value = basePath)
    public Set<Character> findAll() {
        return super.findAll();
    }

    @PostMapping(basePath)
    public Character save(@RequestBody(required = false) Character entity) {
        return super.save(entity);
    }

    @DeleteMapping(basePath)
    public void delete(
            @RequestParam(value = "uuid", required = false) String uuidParam,
            @RequestBody(required = false) Character entityBody) {
        super.delete(uuidParam, entityBody);
    }

    @GetMapping(basePath + uuidPath)
    public Character findByUuid(@PathVariable(uuidPathVariable) String pathUuid) {
        return super.findByUuid(pathUuid);
    }

    @DeleteMapping(basePath + uuidPath)
    public void deleteByUuid(@PathVariable(uuidPathVariable) String pathUuid) {
        super.deleteByUuid(pathUuid);
    }

    @PutMapping(basePath)
    public Character update(@RequestBody Character entity) {
        return super.update(entity);
    }

    @PutMapping(basePath + uuidPath)
    public Character updateByUuid(@PathVariable(uuidPathVariable) String pathUuid,
                                  @RequestBody Character entity) {
        return super.updateByUuid(pathUuid, entity);
    }

}
