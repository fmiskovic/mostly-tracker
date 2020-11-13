package com.mostly.tracker.services;

import com.mostly.tracker.model.Identifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

public abstract class AbstractService<E extends Identifier<ID>, ID extends Serializable> implements GenericService<E, ID> {

    protected abstract JpaRepository<E, ID> getRepository();

    protected abstract JpaSpecificationExecutor<E> getSpecificationExecutor();

    @Transactional
    @Override
    public E save(E entity) {
        return getRepository().save(entity);
    }

    @Transactional
    @Override
    public void deleteById(ID id) {
        getRepository().deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<E> findById(ID id) {
        return getRepository().findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<E> findPage(Pageable pageable) {
        return search(null, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<E> search(Specification<E> spec, Pageable pageable) {
        return getSpecificationExecutor().findAll(spec, pageable);
    }
}
