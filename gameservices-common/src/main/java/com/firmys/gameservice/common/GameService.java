package com.firmys.gameservice.common;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface GameService<T extends GameEntity> extends PagingAndSortingRepository<T, Integer> {

}
