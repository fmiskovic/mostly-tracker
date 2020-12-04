package com.time.tracker.converters;

import com.time.tracker.dto.ProjectDto;
import com.time.tracker.model.Project;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProjectConverter implements EntityConverter<Project, ProjectDto> {

    public ProjectDto toDto(Project entity) {
        ProjectDto dto = new ProjectDto();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public Project toEntity(ProjectDto dto) {
        Project entity = new Project();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
