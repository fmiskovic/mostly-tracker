package com.mostly.tracker.services;

import com.mostly.tracker.model.Project;
import com.mostly.tracker.model.ProjectEntry;
import com.mostly.tracker.mvc.commands.ProjectEntryCommand;
import com.mostly.tracker.repositories.ProjectEntryRepository;
import com.mostly.tracker.repositories.ProjectRepository;
import com.mostly.tracker.validators.ProjectEntryValidator;
import org.springframework.beans.BeanUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ProjectEntryService extends AbstractService<ProjectEntry, Long> {

    private final ProjectEntryRepository repository;

    private final ProjectRepository projectRepository;

    private final ProjectEntryValidator validator;

    public ProjectEntryService(ProjectEntryRepository repository, ProjectRepository projectRepository, ProjectEntryValidator validator) {
        this.repository = repository;
        this.projectRepository = projectRepository;
        this.validator = validator;
    }

    @Override
    protected JpaRepository<ProjectEntry, Long> getRepository() {
        return repository;
    }

    @Override
    protected JpaSpecificationExecutor<ProjectEntry> getSpecificationExecutor() {
        return repository;
    }

    public List<ProjectEntry> getAllEntriesByProjectId(Long projectId) {
        return repository.findAllByProjectId(projectId);
    }

    public ProjectEntry createProjectEntry(ProjectEntryCommand command) {
        Project project = projectRepository.findById(command.getProjectId())
                .orElseThrow(EntityNotFoundException::new);
        ProjectEntry entry = new ProjectEntry();
        BeanUtils.copyProperties(command, entry);

        validator.validateEntryDate(project, entry); // throws an error if entry date is not valid
        entry.setProject(project);

        return save(entry);
    }
}
