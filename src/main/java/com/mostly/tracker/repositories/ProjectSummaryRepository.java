package com.mostly.tracker.repositories;

import com.mostly.tracker.model.ProjectSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectSummaryRepository extends JpaRepository<ProjectSummary, Long>, JpaSpecificationExecutor<ProjectSummary> {

    Optional<ProjectSummary> findProjectSummaryByProjectId(Long projectId);
}
