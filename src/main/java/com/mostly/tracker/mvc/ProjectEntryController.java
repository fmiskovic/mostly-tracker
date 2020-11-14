package com.mostly.tracker.mvc;

import com.mostly.tracker.converters.ProjectEntryConverter;
import com.mostly.tracker.dto.ProjectDto;
import com.mostly.tracker.dto.ProjectEntryDto;
import com.mostly.tracker.model.ProjectEntry;
import com.mostly.tracker.mvc.commands.ProjectEntryCommand;
import com.mostly.tracker.mvc.filters.ProjectEntrySpecification;
import com.mostly.tracker.services.ProjectEntryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This controller specifies endpoints responsible for project time tracking.
 */
@RestController
@RequestMapping(path = "/api/v1/entries")
public class ProjectEntryController {

    private final ProjectEntryService service;

    private final ProjectEntryConverter converter;

    public ProjectEntryController(ProjectEntryService service, ProjectEntryConverter converter) {
        this.service = service;
        this.converter = converter;
    }

    /**
     * Find project entry by ID.
     *
     * @param entryId - project entry identifier
     * @return Project entry details. Check {@link ProjectEntryDto}.
     */
    @GetMapping("/{entryId}")
    public ProjectEntryDto getEntryById(@PathVariable("entryId") Long entryId) {
        return converter
                .toDto(service.findById(entryId)
                        .orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Find project entries by specified project ID.
     *
     * @param projectId - project entry identifier
     * @return Collection of project entries. Check {@link ProjectEntryDto}.
     */
    @GetMapping("/project/{projectId}")
    public List<ProjectEntryDto> getAllEntriesByProjectId(@PathVariable("projectId") Long projectId) {
        return service.getAllEntriesByProjectId(projectId)
                .stream().map(converter::toDto).collect(Collectors.toList());
    }

    /**
     * Delete project entry by ID
     *
     * @param entryId - project entry identifier
     * @return NO CONTENT
     */
    @DeleteMapping("/{entryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEntryById(@PathVariable("entryId") Long entryId) {
        service.deleteById(entryId);
    }

    /**
     * Search project entries with optional parameters that act like AND filter.
     * <p>
     * ?sort=field{,asc|,desc}  sort by field ascending or descending
     * ?page=number             returns only {number} page
     * ?size=limit              limits only to {limit} resources
     * ?field1=val1&field2=val1 search for val1 or val2
     *
     * @param spec     - search filter of type {@link ProjectEntrySpecification}
     * @param pageable - pageable object of type {@link Pageable}
     * @return Dto representation of the project entity. Check {@link ProjectDto}.
     */
    @GetMapping("/search")
    public Page<ProjectEntryDto> search(ProjectEntrySpecification spec, Pageable pageable) {
        return service.search(spec, pageable).map(converter::toDto);
    }

    /**
     * Create new entry for specified project.
     *
     * @param command {@link ProjectEntryCommand} object with entity properties.
     * @return Resource location.
     */
    @PutMapping("/save")
    public ResponseEntity<URI> save(@RequestBody ProjectEntryCommand command) {
        ProjectEntry project = service.createProjectEntry(command);
        // Create resource location
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(project.getId())
                .toUri();
        // Send location in response
        return ResponseEntity.created(location).build();
    }
}
