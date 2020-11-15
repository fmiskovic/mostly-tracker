package com.mostly.tracker.converters;

import com.mostly.tracker.dto.ProjectEntryDto;
import com.mostly.tracker.model.Project;
import com.mostly.tracker.model.ProjectEntry;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

public class ProjectEntityConverterTest extends AbstractConverterTest<ProjectEntry, ProjectEntryDto> {

    @Autowired
    private ProjectEntryConverter converter;

    @Override
    protected EntityConverter<ProjectEntry, ProjectEntryDto> getConverter() {
        return converter;
    }

    @Override
    protected ProjectEntryDto createDto() {
        ProjectEntryDto dto = new ProjectEntryDto();
        dto.setId(1L);
        dto.setEntryDate(LocalDate.now());
        dto.setTimeSpent(2.33f);
        dto.setDescription("Nicely put description.");
        return dto;
    }

    @Override
    protected ProjectEntry createEntity() {
        ProjectEntry entity = new ProjectEntry();
        entity.setId(1L);
        entity.setEntryDate(LocalDate.now());
        entity.setTimeSpent(2.33f);
        entity.setDescription("Nicely put description.");
        entity.setProject(new Project());
        return entity;
    }
}
