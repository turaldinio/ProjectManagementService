package com.digital.pm.repository.spec;


import com.digital.pm.repository.filter.TaskDaoFilter;
import com.digital.pm.model.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.*;


public class TaskSpecification {
    public static Specification<Task> getSpec(TaskDaoFilter taskFilter) {

        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.isNull(taskFilter)) {
                return query.where().orderBy(criteriaBuilder.desc(root.get("creationDate"))).getRestriction();

            }
            if (!ObjectUtils.isEmpty(taskFilter.getName())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("name")), SpecHandler.getFormatedString(taskFilter.getName()))).
                                getRestriction());

            }
            if (!ObjectUtils.isEmpty(taskFilter.getStatusList())) {
                predicates.add(query.where(root.get("status").in(taskFilter.getStatusList())).getRestriction());
            }
            if (!ObjectUtils.isEmpty(taskFilter.getAuthorId())) {
                predicates.add(criteriaBuilder.equal(root.get("authorId"), taskFilter.getAuthorId()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getExecutorId())) {
                predicates.add(criteriaBuilder.equal(root.get("executorId"), taskFilter.getExecutorId()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getDeadlineBefore())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("deadline").as(Date.class), taskFilter.getDeadlineBefore()));

            }
            if (!ObjectUtils.isEmpty(taskFilter.getDeadlineAfter())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("deadline").as(Date.class), taskFilter.getDeadlineAfter()));

            }
            if (!ObjectUtils.isEmpty(taskFilter.getCreatedAfter()) &&
                    !ObjectUtils.isEmpty(taskFilter.getCreatedBefore())) {

                predicates.add(criteriaBuilder.between(root.get("creationDate"),
                        taskFilter.getCreatedAfter(),
                        taskFilter.getCreatedBefore()));
            } else {
                if (!ObjectUtils.isEmpty(taskFilter.getCreatedAfter())) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate").as(Date.class),
                            taskFilter.getCreatedAfter()));
                } else {
                    if (!ObjectUtils.isEmpty(taskFilter.getCreatedBefore())) {
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDate").as(Date.class),
                                taskFilter.getCreatedBefore()));
                    }
                }
            }

            if (predicates.isEmpty()) {
                return query.where().orderBy(criteriaBuilder.desc(root.get("creationDate"))).getRestriction();
            }
            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).orderBy(criteriaBuilder.desc(root.get("creationDate"))).getRestriction();
        });
    }
}
