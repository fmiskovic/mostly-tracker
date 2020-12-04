package com.time.tracker.converters;

import com.time.tracker.dto.ProjectEntryDto;
import com.time.tracker.model.ProjectEntry;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProjectEntryConverter implements EntityConverter<ProjectEntry, ProjectEntryDto> {

    @Override
    public ProjectEntry toEntity(ProjectEntryDto dto) {
        ProjectEntry entity = new ProjectEntry();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    @Override
    public ProjectEntryDto toDto(ProjectEntry entity) {
        ProjectEntryDto dto = new ProjectEntryDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
