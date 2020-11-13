package com.mostly.tracker.services;

import com.mostly.tracker.model.Project;
import com.mostly.tracker.mvc.commands.ProjectCommand;
import com.mostly.tracker.repositories.ProjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

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

    public Project createProject(ProjectCommand command) {
        Project project = new Project();
        BeanUtils.copyProperties(command, project);
        return save(project);
    }

    public Project updateProject(Long projectId, ProjectCommand command) {
        Project project = new Project();
        project.setId(projectId);
        BeanUtils.copyProperties(command, project);
        return save(project);
    }
}
