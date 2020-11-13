package com.mostly.tracker.converters;

import com.mostly.tracker.dto.ProjectDto;
import com.mostly.tracker.model.Project;
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
