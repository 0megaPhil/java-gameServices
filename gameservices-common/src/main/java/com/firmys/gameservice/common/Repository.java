package com.firmys.gameservice.common;

import org.springframework.data.repository.CrudRepository;

public interface Repository<R> extends CrudRepository<R, Integer> {
}
