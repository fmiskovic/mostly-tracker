package com.time.tracker.services;

import com.time.tracker.model.Project;
import com.time.tracker.model.ProjectEntry;
import com.time.tracker.model.ProjectSummary;
import com.time.tracker.repositories.ProjectEntryRepository;
import com.time.tracker.repositories.ProjectRepository;
import com.time.tracker.repositories.ProjectSummaryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        log.debug("Get existing or generate new summary for project with ID {}", projectId);
        Optional<ProjectSummary> optSum = summaryRepository.findProjectSummaryByProjectId(projectId);
        return optSum.orElse(generateSummary(projectId));
    }

    @Transactional
    public ProjectSummary generateSummary(Long projectId) {
        log.debug("Generate new summary for project with ID {}", projectId);
        // verify project id
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Project with specified ID %d doesn't exist", projectId)));

        List<ProjectEntry> entries = entryRepository.findAllByProjectId(projectId);

        // calculate totalDays as number of unique entryDates
        long totalDays = entries.parallelStream()
                .map(ProjectEntry::getEntryDate)
                .collect(Collectors.toSet()).size();
        // calculate totalTimeSpent as a sum of timeSpent
        double totalTimeSpent = totalDays > 0 ?
                entries.parallelStream().mapToDouble(ProjectEntry::getTimeSpent).sum()
                : 0.0f;
        // calculate averageTimeSpentPerDay as a division of totalTimeSpent and totalDays
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
