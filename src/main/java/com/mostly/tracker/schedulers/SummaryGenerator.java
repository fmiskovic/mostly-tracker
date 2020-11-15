package com.mostly.tracker.schedulers;

import com.mostly.tracker.model.ProjectSummary;
import com.mostly.tracker.services.ProjectService;
import com.mostly.tracker.services.ProjectSummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * A cron job responsible to generate summary for each project that endDate is today.
 * We need this because {@link com.mostly.tracker.mvc.ProjectSummaryController#getProjectSummary(Long projectId)}
 * endpoint can be heavy operation, and we want to optimize it when possible.
 */
@Component
public class SummaryGenerator {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ProjectService projectService;

    private final ProjectSummaryService summaryService;

    public SummaryGenerator(ProjectService projectService, ProjectSummaryService summaryService) {
        this.projectService = projectService;
        this.summaryService = summaryService;
    }

    @Async
    @Scheduled(cron = "${mostly.tracker.schedule}") // every day at 1am
    public void generateSummary() {
        log.debug("Checking for ended projects to generate summaries...");
        LocalDate today = LocalDate.now();
        List<Long> projectIds = projectService.findProjectIdsByEndDate(today);
        projectIds.forEach(id -> {
            ProjectSummary summary = summaryService.generateSummary(id);
            summaryService.save(summary);
        });
    }
}
