package com.mostly.tracker.services;

import com.mostly.tracker.model.Project;
import com.mostly.tracker.model.ProjectEntry;
import com.mostly.tracker.model.ProjectSummary;
import com.mostly.tracker.mvc.filters.ProjectSummarySpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectSummaryServiceTest extends AbstractServiceTest<ProjectSummary, Long> {

    @Autowired
    private ProjectSummaryService service;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectEntryService entryService;

    @Test
    public void testGetProjectSummaryByProjectId() {
        // prepare
        long totalDays = 3;
        float timeSpent1 = 2.55f;
        float timeSpent2 = 6.44f;
        float timeSpent3 = 3.37f;
        float sum = timeSpent1 + timeSpent2 + timeSpent3;
        float avg = sum / totalDays;
        Project project = persistAndGetProject();
        ProjectSummary summary = new ProjectSummary();
        summary.setId(project.getId());
        summary.setProject(project);
        summary.setAverageTimeSpentPerDay(avg);
        summary.setTotalDays(totalDays);
        summary.setTotalTimeSpent(sum);
        service.save(summary);
        // execute
        ProjectSummary getSummary = service.getProjectSummaryByProjectId(project.getId());
        //verify
        assertNotNull(summary);
        assertEquals(3, getSummary.getTotalDays());
        assertEquals(sum, getSummary.getTotalTimeSpent());
        assertEquals(avg, getSummary.getAverageTimeSpentPerDay());
    }

    @Test
    public void testGenerateSummary() {
        // prepare
        long totalDays = 2;
        float timeSpent1 = 2.55f;
        float timeSpent2 = 6.44f;
        float timeSpent3 = 3.37f;
        float sum = timeSpent1 + timeSpent2 + timeSpent3;
        float avg = sum / totalDays;
        Project project = persistAndGetProject();
        createProjectEntry(timeSpent1, LocalDate.now().minusDays(5), project);
        createProjectEntry(timeSpent2, LocalDate.now().minusDays(4), project);
        createProjectEntry(timeSpent3, LocalDate.now().minusDays(4), project);
        // execute
        ProjectSummary summary = service.generateSummary(project.getId());
        summary = service.save(summary);
        //verify
        assertNotNull(summary);
        assertEquals(totalDays, summary.getTotalDays());
        assertEquals(sum, summary.getTotalTimeSpent());
        assertEquals(avg, summary.getAverageTimeSpentPerDay());
    }

    @Override
    protected GenericService<ProjectSummary, Long> getService() {
        return service;
    }

    @Override
    protected ProjectSummary createEntity() {
        long totalDays = 20L;
        float totalTimeSpent = 456.55f;

        Project project = persistAndGetProject();

        ProjectSummary summary = new ProjectSummary();
        summary.setId(project.getId());
        summary.setProject(project);
        summary.setTotalTimeSpent(totalTimeSpent);
        summary.setTotalDays(totalDays);
        summary.setAverageTimeSpentPerDay(totalTimeSpent / totalDays);
        return summary;
    }

    @Override
    protected Specification<ProjectSummary> createFilter() {
        ProjectSummarySpecification spec = new ProjectSummarySpecification();
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

    private void createProjectEntry(float timeSpent, LocalDate entryDate, Project project) {
        ProjectEntry entity = new ProjectEntry();
        entity.setEntryDate(entryDate);
        entity.setTimeSpent(timeSpent);
        entity.setDescription("Nicely put description.");
        entity.setProject(project);

        ProjectEntry saved = entryService.save(entity);
        assertNotNull(saved);
        assertTrue(entryService.findById(saved.getId()).isPresent());
    }
}
