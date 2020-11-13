package com.mostly.tracker.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Entity class for project time tracking.
 */
@Entity
@Table(name = "entries")
public class ProjectEntry implements Identifier<Long> {

    @Id
    @GeneratedValue(generator = "project_entry_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "project_entry_sequence", sequenceName = "project_entry_sequence", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private LocalDate entryDate;

    @Column(nullable = false)
    private Float timeSpent;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id")
    private Project project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Transient
    @Override
    public Long getIdentifier() {
        return id;
    }

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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntry that = (ProjectEntry) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
