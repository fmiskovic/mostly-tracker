package com.time.tracker.repositories;

import com.time.tracker.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {

    @Query("SELECT p.id FROM Project AS p WHERE p.endDate = :endDate")
    List<Long> findProjectIdsByEndDate(@Param("endDate") LocalDate endDate);
}
