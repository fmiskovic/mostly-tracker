package com.mostly.tracker.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "summary")
public class ProjectSummary implements Identifier<Long> {

    @Id // id is mapped with project id
    private Long id;

    @Column(nullable = false)
    private Float totalTimeSpent;

    @Column(nullable = false)
    private Long totalDays;

    @Column(nullable = false)
    private Float averageTimeSpentPerDay;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @MapsId
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    @Transient
    @Override
    public Long getIdentifier() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectSummary that = (ProjectSummary) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
