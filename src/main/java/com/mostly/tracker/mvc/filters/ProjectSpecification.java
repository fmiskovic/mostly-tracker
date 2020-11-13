package com.mostly.tracker.mvc.filters;

import com.mostly.tracker.model.Project;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class ProjectSpecification implements Specification<Project> {

    private Long id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        var predicates = new ArrayList<>();

        if (Objects.nonNull(id)) {
            predicates.add(builder.equal(root.get("id"), id));
        }

        if (!StringUtils.isEmpty(name)) {
            predicates.add(builder.equal(root.get("name"), name));
        }

        if (Objects.nonNull(startDate)) {
            predicates.add(builder.lessThanOrEqualTo(root.get("startDate"), startDate));
        }

        if (Objects.nonNull(endDate)) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("endDate"), endDate));
        }

        return predicates.isEmpty() ? null : builder.and(predicates.toArray(new Predicate[0]));
    }
}
