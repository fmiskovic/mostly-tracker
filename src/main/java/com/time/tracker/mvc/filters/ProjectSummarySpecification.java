package com.time.tracker.mvc.filters;

import com.time.tracker.model.ProjectSummary;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Objects;

public class ProjectSummarySpecification implements Specification<ProjectSummary> {

    private Long id;

    private String projectName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public Predicate toPredicate(Root<ProjectSummary> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        var predicates = new ArrayList<>();

        if (Objects.nonNull(id)) {
            predicates.add(builder.equal(root.get("id"), id));
        }

        if (!StringUtils.isEmpty(projectName)) {
            predicates.add(builder.equal(root.get("project").get("name"), projectName));
        }

        return predicates.isEmpty() ? null : builder.and(predicates.toArray(new Predicate[0]));
    }
}
