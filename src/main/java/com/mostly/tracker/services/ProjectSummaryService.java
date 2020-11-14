package com.mostly.tracker.services;

import com.mostly.tracker.model.Project;
import com.mostly.tracker.model.ProjectEntry;
import com.mostly.tracker.model.ProjectSummary;
import com.mostly.tracker.repositories.ProjectEntryRepository;
import com.mostly.tracker.repositories.ProjectRepository;
import com.mostly.tracker.repositories.ProjectSummaryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectSummaryService extends AbstractService<ProjectSummary, Long> {

    private final ProjectSummaryRepository summaryRepository;

    private final ProjectRepository projectRepository;

    private final ProjectEntryRepository entryRepository;

    public ProjectSummaryService(ProjectSummaryRepository summaryRepository, ProjectRepository projectRepository, ProjectEntryRepository entryRepository) {
        this.summaryRepository = summaryRepository;
        this.projectRepository = projectRepository;
        this.entryRepository = entryRepository;
    }

    @Transactional
    public ProjectSummary getProjectSummaryByProjectId(Long projectId) {
        Optional<ProjectSummary> optSum = summaryRepository.findProjectSummaryByProjectId(projectId);
        return optSum.orElse(generateSummary(projectId));
    }

    @Transactional
    public ProjectSummary generateSummary(Long projectId) {
        // verify project id
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Project with specified ID %d doesn't exist", projectId)));

        List<ProjectEntry> entries = entryRepository.findAllByProjectId(projectId);

        // calculations below
        long totalDays = entries.size();
        double totalTimeSpent = totalDays > 0 ?
                entries.parallelStream().mapToDouble(ProjectEntry::getTimeSpent).sum()
                : 0.0f;
        float averageTimeSpentPerDay = totalDays > 0 ? (float) (totalTimeSpent / totalDays) : 0.0f;

        ProjectSummary summary = new ProjectSummary();
        summary.setAverageTimeSpentPerDay(averageTimeSpentPerDay);
        summary.setTotalDays(totalDays);
        summary.setTotalTimeSpent((float) totalTimeSpent);
        summary.setProject(project);
        summary.setId(projectId);

        return summary;
    }

    @Override
    protected JpaRepository<ProjectSummary, Long> getRepository() {
        return summaryRepository;
    }

    @Override
    protected JpaSpecificationExecutor<ProjectSummary> getSpecificationExecutor() {
        return summaryRepository;
    }
}
