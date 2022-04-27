package com.firmys.gameservices.characters.service;

import com.firmys.gameservices.characters.service.data.Character;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CharacterRepository extends
        PagingAndSortingRepository<Character, Integer>, QuerydslPredicateExecutor<Character> {
}
