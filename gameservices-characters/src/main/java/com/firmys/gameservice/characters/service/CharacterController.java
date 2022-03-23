package com.firmys.gameservice.characters.service;

import com.firmys.gameservice.characters.service.data.Character;
import com.firmys.gameservice.common.ControllerAbstract;
import com.firmys.gameservice.common.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
public class CharacterController extends ControllerAbstract<Character> {
    private final static String basePath = "/character/";

    public CharacterController(Service<Character> service) {
        super(service);
    }

    @GetMapping(basePath)
    public Set<Character> findAll() {
        return super.findAll();
    }

    @PostMapping(basePath)
    public Character save(@RequestBody Character entity) {
        return super.save(entity);
    }

    @DeleteMapping(basePath)
    public void delete(@RequestBody Character entity) {
        super.delete(entity);
    }

    @GetMapping(basePath + "{id}")
    public Character findById(@PathVariable("id") int id) {
        return super.findById(id);
    }

    @DeleteMapping(basePath + "{id}")
    public void deleteById(@PathVariable("id") int id) {
        super.deleteById(id);
    }

    @PutMapping(basePath + "{id}")
    public void update(@PathVariable("id") int id, @RequestBody Character entity) {
        Character existingEntity = super.findById(id);
        existingEntity.setName(Optional.ofNullable(entity.getName())
                .orElse(existingEntity.getName()));
        existingEntity.setDescription(Optional.ofNullable(entity.getDescription())
                .orElse(existingEntity.getDescription()));
        existingEntity.setGender(Optional.ofNullable(entity.getGender())
                .orElse(existingEntity.getGender()));
        existingEntity.setHeight(Optional.ofNullable(entity.getHeight())
                .orElse(existingEntity.getHeight()));
        existingEntity.setWeight(Optional.ofNullable(entity.getWeight())
                .orElse(existingEntity.getWeight()));
        existingEntity.setInventory(Optional.ofNullable(entity.getInventory())
                .orElse(existingEntity.getInventory()));

        super.save(existingEntity);
    }

}
