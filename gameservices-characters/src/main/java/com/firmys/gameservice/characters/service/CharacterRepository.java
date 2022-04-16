package com.firmys.gameservice.characters.service;

import com.firmys.gameservice.characters.service.data.Character;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CharacterRepository extends PagingAndSortingRepository<Character, Integer> {
}
