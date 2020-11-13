package com.mostly.tracker.services;

import com.mostly.tracker.model.Project;
import com.mostly.tracker.mvc.commands.ProjectCommand;
import com.mostly.tracker.mvc.filters.ProjectSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectServiceTest extends AbstractServiceTest<Project, Long> {

    @Autowired
    private ProjectService service;

    @Test
    public void testCreateProject() {
        ProjectCommand command = new ProjectCommand();
        command.setName(NAME);
        command.setStartDate(LocalDate.MIN.plusDays(10));
        command.setEndDate(D_DATE);

        Project newProject = service.createProject(command);
        assertNotNull(newProject);
        assertNotNull(newProject.getIdentifier());
        assertEquals(NAME, newProject.getName());
        assertEquals(command.getStartDate(), newProject.getStartDate());
        assertEquals(command.getEndDate(), newProject.getEndDate());

        assertTrue(service.findById(newProject.getId()).isPresent());
    }

    @Override
    protected GenericService<Project, Long> getService() {
        return service;
    }

    @Override
    protected Project createEntity() {
        Project entity = new Project();
        entity.setStartDate(D_DATE.minusDays(30));
        entity.setEndDate(D_DATE);
        entity.setName(NAME);
        return entity;
    }

    @Override
    protected Specification<Project> createFilter() {
        ProjectSpecification spec = new ProjectSpecification();
        spec.setStartDate(LocalDate.MIN.plusDays(3));
        spec.setEndDate(D_DATE.minusDays(3));
        spec.setName(NAME);
        return spec;
    }
}
