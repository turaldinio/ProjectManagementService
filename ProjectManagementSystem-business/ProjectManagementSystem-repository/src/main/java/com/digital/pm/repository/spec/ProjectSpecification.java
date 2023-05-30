package com.digital.pm.repository.spec;

import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.model.project.Project;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.criteria.Predicate;


public class ProjectSpecification {
    public static Specification<Project> getSpec(ProjectFilter projectFilter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();

            if (projectFilter == null) {
                return query.where().getRestriction();
            }

            if (!ObjectUtils.isEmpty(projectFilter.getId())) {
                list.add(criteriaBuilder.equal(root.get("id"), projectFilter.getId()));
            }

            if (!ObjectUtils.isEmpty(projectFilter.getProjectCode())) {
                list.add(criteriaBuilder.equal(root.get("project_code"), projectFilter.getProjectCode()));
            }

            if (!ObjectUtils.isEmpty(projectFilter.getName())) {
                list.add(criteriaBuilder.equal(root.get("name"), projectFilter.getName()));
            }

            if (!ObjectUtils.isEmpty(projectFilter.getStatusList())) {
                list.add(query.where(root.get("status").in(projectFilter.getStatusList())).getRestriction());
            }

            if (list.isEmpty()) {
                return query.where().getRestriction();
            }

            return query.where(criteriaBuilder.and(list.toArray(Predicate[]::new))).getRestriction();

        });
    }
}
