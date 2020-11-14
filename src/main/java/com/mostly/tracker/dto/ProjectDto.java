package com.mostly.tracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mostly.tracker.model.ProjectEntry;

import java.time.LocalDate;
import java.util.List;

public class ProjectDto implements IdentifierDto<Long> {

    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    private List<ProjectEntry> entries;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    @Override
    public Long getIdentifier() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public List<ProjectEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ProjectEntry> entries) {
        this.entries = entries;
    }
}
