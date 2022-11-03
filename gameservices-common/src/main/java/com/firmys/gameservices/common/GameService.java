package com.firmys.gameservices.common;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface GameService<T extends GameEntity> extends
        PagingAndSortingRepository<T, Integer>, QuerydslPredicateExecutor<T> {
}
