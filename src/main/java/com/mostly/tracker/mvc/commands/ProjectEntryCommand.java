package com.mostly.tracker.mvc.commands;

import java.time.LocalDate;
import javax.validation.constraints.NotNull;

public class ProjectEntryCommand {

    @NotNull
    private LocalDate entryDate;

    @NotNull
    private Float timeSpent;

    private String description;

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
}
