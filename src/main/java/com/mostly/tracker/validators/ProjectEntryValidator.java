package com.mostly.tracker.validators;

import com.mostly.tracker.errors.EntryDateException;
import com.mostly.tracker.model.Project;
import com.mostly.tracker.model.ProjectEntry;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ProjectEntryValidator {

    public void validateEntryDate(Project project, ProjectEntry entry) throws EntryDateException {
        LocalDate entryDate = entry.getEntryDate();
        LocalDate startDate = project.getStartDate();
        LocalDate endDate = project.getEndDate();

        if (entryDate.isBefore(startDate) || entryDate.isAfter(endDate)) {
            throw new EntryDateException();
        }
    }
}
