package com.mostly.tracker.services;

import com.mostly.tracker.model.Identifier;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.io.Serializable;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:truncate.sql")
@ActiveProfiles("test")
public abstract class AbstractServiceTest<E extends Identifier<ID>, ID extends Serializable> {

    protected static final String NAME = "The Name";

    protected static final LocalDate D_DATE = LocalDate.now();

    @Test
    public void testSave() {
        persistEntity();
    }

    @Test
    public void testDeleteById() {
        E entity = persistEntity();
        ID id = entity.getIdentifier();
        getService().deleteById(id);
        assertFalse(getService().findById(id).isPresent());
    }

    @Test
    public void testFindById() {
        E entity = persistEntity();
        ID id = entity.getIdentifier();
        assertTrue(getService().findById(id).isPresent());
    }

    @Test
    public void testFindPage() {
        persistEntity();

        PageRequest request = PageRequest.of(0, 10);
        Page<E> page = getService().findPage(request);
        assertFalse(page.isEmpty(), "Couldn't find any entities!");
        assertTrue(page.isFirst());
    }

    @Test
    public void testSearch() {
        persistEntity();

        PageRequest request = PageRequest.of(0, 10);
        Page<E> page = getService().search(createFilter(), request);
        assertFalse(page.isEmpty(), "Couldn't find any entities!");
        assertTrue(page.isFirst());
    }

    protected abstract GenericService<E, ID> getService();

    protected abstract E createEntity();

    protected abstract Specification<E> createFilter();

    protected E persistEntity() {
        E entity = getService().save(createEntity());
        assertNotNull(entity);
        assertNotNull(entity.getIdentifier());
        return entity;
    }
}
