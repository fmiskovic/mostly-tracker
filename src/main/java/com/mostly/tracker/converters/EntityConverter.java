package com.mostly.tracker.converters;

import com.mostly.tracker.dto.IdentifierDto;
import com.mostly.tracker.model.Identifier;

public interface EntityConverter<E extends Identifier<Long>, D extends IdentifierDto<Long>> {

    E toEntity(D dto);

    D toDto(E entity);
}
