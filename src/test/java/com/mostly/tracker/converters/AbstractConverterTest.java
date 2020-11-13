package com.mostly.tracker.converters;

import com.mostly.tracker.dto.IdentifierDto;
import com.mostly.tracker.model.Identifier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
@ActiveProfiles("test")
public abstract class AbstractConverterTest<E extends Identifier<Long>, D extends IdentifierDto<Long>> {

    @Test
    public void testToEntity() {
        E entity = getConverter().toEntity(createDto());
        assertNotNull(entity);
    }

    @Test
    public void testToDto() {
        D dto = getConverter().toDto(createEntity());
        assertNotNull(dto);
    }

    protected abstract EntityConverter<E, D> getConverter();

    protected abstract D createDto();

    protected abstract E createEntity();
}
