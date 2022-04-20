package com.firmys.gameservices.characters.service;

import com.firmys.gameservices.characters.service.data.Character;
import com.firmys.gameservices.common.GameDataLookup;
import org.springframework.stereotype.Component;

@Component
public class CharacterDataLookup extends GameDataLookup<Character> {

    public CharacterDataLookup(CharacterService service) {
        super(service);
    }

}
