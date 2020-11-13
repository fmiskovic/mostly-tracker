package com.mostly.tracker.repositories;

import com.mostly.tracker.model.ProjectEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectEntryRepository extends JpaRepository<ProjectEntry, Long>, JpaSpecificationExecutor<ProjectEntry> {

    @Query("SELECT e FROM ProjectEntry AS e WHERE e.project.id = :projectId")
    List<ProjectEntry> findAllByProjectId(@Param("projectId") Long projectId);
}
