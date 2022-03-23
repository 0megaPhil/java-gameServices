package com.firmys.gameservice.characters.service;

import com.firmys.gameservice.characters.service.data.Character;
import com.firmys.gameservice.common.Repository;
import com.firmys.gameservice.common.ServiceAbstract;
import org.springframework.stereotype.Service;

@Service
public class CharacterService extends ServiceAbstract<Character> {

    public CharacterService(Repository<Character> repository, String basePath) {
        super(repository, basePath);
    }
}
