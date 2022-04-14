package com.firmys.gameservice.characters.service;

import com.firmys.gameservice.characters.service.data.Character;
import com.firmys.gameservice.common.GameDataLookup;
import org.springframework.stereotype.Component;

@Component
public class CharacterDataLookup extends GameDataLookup<Character> {

    public CharacterDataLookup(CharacterService service) {
        super(service);
    }

}
