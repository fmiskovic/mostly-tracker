package com.time.tracker.services;

import com.time.tracker.model.Project;
import com.time.tracker.mvc.commands.ProjectCommand;
import com.time.tracker.mvc.filters.ProjectSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectServiceTest extends AbstractServiceTest<Project, Long> {

    @Autowired
    private ProjectService service;

    @Test
    public void testCreateAndUpdateProject() {
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

        String theOtherName = "The other name";
        command.setName(theOtherName);
        command.setEndDate(LocalDate.now());

        Project updatedProject = service.updateProject(newProject.getId(), command);
        assertEquals(theOtherName, updatedProject.getName());
        assertEquals(command.getEndDate(), updatedProject.getEndDate());

    }

    @Test
    public void testFindProjectsByEndDate(){
        ProjectCommand command = new ProjectCommand();
        command.setName(NAME);
        command.setStartDate(LocalDate.MIN.plusDays(10));
        command.setEndDate(D_DATE);

        Project newProject = service.createProject(command);
        assertNotNull(newProject);

        List<Long> projectIds = service.findProjectIdsByEndDate(D_DATE);
        assertEquals(1, projectIds.size());
        assertEquals(newProject.getId(), projectIds.get(0));
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
