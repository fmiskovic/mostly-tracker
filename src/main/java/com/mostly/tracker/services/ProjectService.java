package com.mostly.tracker.services;

import com.mostly.tracker.model.Project;
import com.mostly.tracker.mvc.commands.ProjectCommand;
import com.mostly.tracker.repositories.ProjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProjectService extends AbstractService<Project, Long> {

    private final ProjectRepository repository;

    public ProjectService(ProjectRepository repository) {
        this.repository = repository;
    }

    @Override
    protected JpaRepository<Project, Long> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<Project> getSpecificationExecutor() {
        return repository;
    }

    @Transactional
    public Project createProject(ProjectCommand command) {
        log.debug("Creating new project {}", command.getName());
        Project project = new Project();
        BeanUtils.copyProperties(command, project);
        return save(project);
    }

    @Transactional
    public Project updateProject(Long projectId, ProjectCommand command) {
        log.debug("Updating existing project with ID {}", projectId);
        Project project = new Project();
        project.setId(projectId);
        BeanUtils.copyProperties(command, project);
        return save(project);
    }

    @Transactional(readOnly = true)
    public List<Long> findProjectIdsByEndDate(LocalDate endDate) {
        return repository.findProjectIdsByEndDate(endDate);
    }
}
