package com.mostly.tracker.mvc;

import com.mostly.tracker.converters.ProjectSummaryConverter;
import com.mostly.tracker.dto.ProjectSummaryDto;
import com.mostly.tracker.services.ProjectSummaryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/summary")
public class ProjectSummaryController {

    private final ProjectSummaryService service;

    private final ProjectSummaryConverter converter;

    public ProjectSummaryController(ProjectSummaryService service, ProjectSummaryConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/project/{projectId}")
    public ProjectSummaryDto getProjectSummary(@PathVariable("projectId") Long projectId) {
        return converter.toDto(service.generateSummary(projectId));
    }
}
