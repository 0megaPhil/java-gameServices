package com.firmys.gameservice.common;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface GameService<T extends GameData> extends PagingAndSortingRepository<T, Integer> {

}
