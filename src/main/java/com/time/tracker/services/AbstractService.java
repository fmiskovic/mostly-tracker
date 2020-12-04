package com.time.tracker.services;

import com.time.tracker.model.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;

public abstract class AbstractService<E extends Identifier<ID>, ID extends Serializable> implements GenericService<E, ID> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected abstract JpaRepository<E, ID> getRepository();

    protected abstract JpaSpecificationExecutor<E> getSpecificationExecutor();

    @Transactional
    @Override
    public E save(E entity) {
        log.debug("Saving new entity");
        return getRepository().save(entity);
    }

    @Transactional
    @Override
    public void deleteById(ID id) {
        log.debug("Deleting entity by ID {}", id);
        getRepository().deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<E> findById(ID id) {
        log.debug("Looking for an entity with ID {}", id);
        return getRepository().findById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<E> findPage(Pageable pageable) {
        log.debug("Browse page...");
        return search(null, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<E> search(Specification<E> spec, Pageable pageable) {
        log.debug("Search page...");
        return getSpecificationExecutor().findAll(spec, pageable);
    }
}
