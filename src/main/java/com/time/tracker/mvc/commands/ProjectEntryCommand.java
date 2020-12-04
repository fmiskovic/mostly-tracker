package com.time.tracker.mvc.commands;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class ProjectEntryCommand {

    @NotNull
    private LocalDate entryDate;

    @NotNull
    private Float timeSpent;

    private String description;

    @NotNull
    private Long projectId;

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Float getTimeSpent() {
        return timeSpent;
    }

    public void setTimeSpent(Float timeSpent) {
        this.timeSpent = timeSpent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
