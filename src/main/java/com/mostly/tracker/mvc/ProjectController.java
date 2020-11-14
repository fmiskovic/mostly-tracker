package com.mostly.tracker.mvc;

import com.mostly.tracker.converters.ProjectConverter;
import com.mostly.tracker.dto.ProjectDto;
import com.mostly.tracker.model.Project;
import com.mostly.tracker.mvc.commands.ProjectCommand;
import com.mostly.tracker.mvc.filters.ProjectSpecification;
import com.mostly.tracker.services.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;

/**
 * This controller specifies endpoints responsible for project entity
 * creating, updating and browsing.
 */
@RestController
@RequestMapping(path = "/api/v1/projects")
public class ProjectController {

    private final ProjectService service;

    private final ProjectConverter converter;

    public ProjectController(ProjectService service, ProjectConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    /**
     * Find project by ID
     *
     * @param projectId - project identifier
     * @return Project details. Check {@link ProjectDto}.
     */
    @GetMapping("/{projectId}")
    public ProjectDto getProjectById(@PathVariable("projectId") Long projectId) {
        return converter
                .toDto(service.findById(projectId)
                        .orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Delete project by ID
     *
     * @param projectId - project identifier
     * @return NO CONTENT
     */
    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProjectById(@PathVariable("projectId") Long projectId) {
        service.deleteById(projectId);
    }

    /**
     * Search projects with optional parameters that act like AND filter.
     * <p>
     * ?sort=field{,asc|,desc}  sort by field ascending or descending
     * ?page=number             returns only {number} page
     * ?size=limit              limits only to {limit} resources
     * ?field1=val1&field2=val1 search for val1 or val2
     *
     * @param spec     - search filter of type {@link ProjectSpecification}
     * @param pageable - pageable object of type {@link Pageable}
     * @return Paged representation of project entities. Check {@link ProjectDto}.
     */
    @GetMapping("/search")
    public Page<ProjectDto> search(ProjectSpecification spec, Pageable pageable) {
        return service.search(spec, pageable).map(converter::toDto);
    }

    /**
     * Search projects with optional pagination parameters.
     * <p>
     * ?sort=field{,asc|,desc}  sort by field ascending or descending
     * ?page=number             returns only {number} page
     * ?size=limit              limits only to {limit} resources
     *
     * @param pageable - pageable object of type {@link Pageable}
     * @return Paged representation of project entities. Check {@link ProjectDto}.
     */
    @GetMapping("/page")
    public Page<ProjectDto> getPage(Pageable pageable) {
        return service.findPage(pageable).map(converter::toDto);
    }

    /**
     * Create new project entity.
     *
     * @param command {@link ProjectCommand} object with entity properties.
     * @return Resource location.
     */
    @PutMapping("/save")
    public ResponseEntity<URI> save(@Valid @RequestBody ProjectCommand command) {
        Project project = service.createProject(command);
        // Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(project.getId())
                .toUri();
        // Send location in response
        return ResponseEntity.created(location).build();
    }

    /**
     * Update of a specified project entity.
     *
     * @param projectId - project identifier
     * @param command   - Command object with entity properties.
     * @return Dto representation of the project entity. Check {@link ProjectDto}.
     */
    @PostMapping("/{projectId}/update")
    public ProjectDto update(@PathVariable("projectId") Long projectId, @Valid @RequestBody ProjectCommand command) {
        Project project = service.updateProject(projectId, command);
        return converter.toDto(project);
    }
}
