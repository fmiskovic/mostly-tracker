package com.time.tracker.converters;

import com.time.tracker.dto.IdentifierDto;
import com.time.tracker.model.Identifier;

public interface EntityConverter<E extends Identifier<Long>, D extends IdentifierDto<Long>> {

    E toEntity(D dto);

    D toDto(E entity);
}
