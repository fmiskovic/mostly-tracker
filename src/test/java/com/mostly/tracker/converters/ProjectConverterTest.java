package com.mostly.tracker.converters;

import com.mostly.tracker.dto.ProjectDto;
import com.mostly.tracker.model.Project;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class ProjectConverterTest extends AbstractConverterTest<Project, ProjectDto> {

    @Autowired
    private ProjectConverter converter;

    @Override
    protected EntityConverter<Project, ProjectDto> getConverter() {
        return converter;
    }

    @Override
    protected ProjectDto createDto() {
        ProjectDto dto = new ProjectDto();
        dto.setId(1L);
        dto.setEndDate(LocalDate.MIN);
        dto.setEndDate(LocalDate.now());
        dto.setName("Lovely Project Name");
        return dto;
    }

    @Override
    protected Project createEntity() {
        Project entity = new Project();
        entity.setId(1L);
        entity.setEndDate(LocalDate.MIN);
        entity.setEndDate(LocalDate.now());
        entity.setName("Lovely Project Name");
        return entity;
    }
}
