package com.mostly.tracker.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProjectSummaryDto implements IdentifierDto<Long> {

    private Long id;

    private Float totalTimeSpent;

    private Long totalDays;

    private Float averageTimeSpentPerDay;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getTotalTimeSpent() {
        return totalTimeSpent;
    }

    public void setTotalTimeSpent(Float totalTimeSpent) {
        this.totalTimeSpent = totalTimeSpent;
    }

    public Long getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Long totalDays) {
        this.totalDays = totalDays;
    }

    public Float getAverageTimeSpentPerDay() {
        return averageTimeSpentPerDay;
    }

    public void setAverageTimeSpentPerDay(Float averageTimeSpentPerDay) {
        this.averageTimeSpentPerDay = averageTimeSpentPerDay;
    }

    @JsonIgnore
    @Override
    public Long getIdentifier() {
        return id;
    }
}
