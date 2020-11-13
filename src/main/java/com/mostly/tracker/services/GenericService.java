package com.mostly.tracker.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.util.Optional;

public interface GenericService<E, ID extends Serializable> {

    E save(E entity);

    void deleteById(ID id);

    Optional<E> findById(ID id);

    Page<E> findPage(Pageable pageable);

    Page<E> search(Specification<E> spec, Pageable pageable);
}
