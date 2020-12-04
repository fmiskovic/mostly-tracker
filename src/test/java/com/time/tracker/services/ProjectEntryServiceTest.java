package com.time.tracker.services;

import com.time.tracker.errors.EntryDateException;
import com.time.tracker.model.Project;
import com.time.tracker.model.ProjectEntry;
import com.time.tracker.mvc.commands.ProjectEntryCommand;
import com.time.tracker.mvc.filters.ProjectEntrySpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectEntryServiceTest extends AbstractServiceTest<ProjectEntry, Long> {

    @Autowired
    private ProjectEntryService service;

    @Autowired
    private ProjectService projectService;

    @Test
    public void testGetAllEntriesByProjectId() {
        Project project = persistAndGetProject();

        List<ProjectEntry> entries = service.getAllEntriesByProjectId(project.getId());
        assertTrue(entries.isEmpty());

        ProjectEntry projectEntry = service.save(createProjectEntry(project));
        assertNotNull(projectEntry);

        entries = service.getAllEntriesByProjectId(project.getId());
        assertFalse(entries.isEmpty());
        assertEquals(1, entries.size());
    }

    @Test
    public void testCreateProjectEntry() {
        Project project = persistAndGetProject();

        ProjectEntryCommand command = new ProjectEntryCommand();
        command.setDescription("Description");
        command.setEntryDate(D_DATE.minusDays(3));
        command.setTimeSpent(3.33f);
        command.setProjectId(project.getId());

        ProjectEntry projectEntry = service.createProjectEntry(command);
        assertNotNull(projectEntry);
    }

    @Test
    public void testCreateProjectEntryWithInvalidEntryDate() {
        Project project = persistAndGetProject();

        ProjectEntryCommand command = new ProjectEntryCommand();
        command.setDescription("Description");
        command.setEntryDate(D_DATE.plusDays(14));
        command.setTimeSpent(3.33f);
        command.setProjectId(project.getId());

        assertThrows(EntryDateException.class, () -> service.createProjectEntry(command));
    }

    @Override
    protected GenericService<ProjectEntry, Long> getService() {
        return service;
    }

    @Override
    protected ProjectEntry createEntity() {
        Project project = persistAndGetProject();
        return createProjectEntry(project);
    }

    @Override
    protected Specification<ProjectEntry> createFilter() {
        ProjectEntrySpecification spec = new ProjectEntrySpecification();
        spec.setEntryDate(D_DATE);
        spec.setProjectName(NAME);
        return spec;
    }

    private Project persistAndGetProject() {
        Project entity = new Project();
        entity.setStartDate(D_DATE.minusDays(30));
        entity.setEndDate(D_DATE);
        entity.setName(NAME);

        Project project = projectService.save(entity);
        return projectService.findById(project.getId()).orElseThrow(EntityNotFoundException::new);
    }

    private ProjectEntry createProjectEntry(Project project) {
        ProjectEntry entity = new ProjectEntry();
        entity.setEntryDate(D_DATE);
        entity.setTimeSpent(2.33f);
        entity.setDescription("Nicely put description.");
        entity.setProject(project);
        return entity;
    }
}
