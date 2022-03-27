package com.firmys.gameservice.common;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional
public abstract class RepositoryAbstract<R, ID extends Serializable> implements PagingAndSortingRepository<R, ID> {
    @Override
    public <S extends R> S save(S entity) {
        return null;
    }

    @Override
    public <S extends R> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }


}
