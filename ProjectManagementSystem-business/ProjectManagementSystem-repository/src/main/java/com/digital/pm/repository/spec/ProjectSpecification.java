package com.digital.pm.repository.spec;

import com.digital.pm.repository.filter.ProjectDaoFilter;
import com.digital.pm.model.Project;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.criteria.Predicate;

public class ProjectSpecification {
    public static Specification<Project> getSpec(ProjectDaoFilter projectFilter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.isNull(projectFilter)) {
                return query.where().getRestriction();
            }


            if (!ObjectUtils.isEmpty(projectFilter.getProjectCode())) {
                predicates.add(query.
                        where(criteriaBuilder.
                                like(criteriaBuilder.lower(root.get("projectCode")), SpecHandler.getFormatedString(projectFilter.getProjectCode()))).
                        getRestriction());
            }

            if (!ObjectUtils.isEmpty(projectFilter.getName())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("name")), SpecHandler.getFormatedString(projectFilter.getName()))).
                                getRestriction());
            }

            if (!ObjectUtils.isEmpty(projectFilter.getStatusList())) {
                predicates.add(query.where(root.get("status").in(projectFilter.getStatusList())).getRestriction());
            }

            if (predicates.isEmpty()) {
                return query.where().getRestriction();
            }

            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();

        });


    }
}
