package com.digital.pm.repository.spec;


import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.model.task.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.*;

public class TaskSpecification {
    public static Specification<Task> getSpec(TaskFilter taskFilter) {

        return ((root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(taskFilter.getName())) {
                list.add(criteriaBuilder.equal(root.get("name"), taskFilter.getName()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getStatusList())) {
                list.add(query.where(root.get("status").in(taskFilter.getStatusList())).getRestriction());
            }
            if (!ObjectUtils.isEmpty(taskFilter.getAuthorId())) {
                list.add(criteriaBuilder.equal(root.get("authorId"), taskFilter.getAuthorId()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getExecutorId())) {
                list.add(criteriaBuilder.equal(root.get("executorId"), taskFilter.getExecutorId()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getDeadlineBefore())) {
                list.add(criteriaBuilder.lessThanOrEqualTo(root.get("deadline").as(Date.class), taskFilter.getDeadlineBefore()));

            }
            if (!ObjectUtils.isEmpty(taskFilter.getDeadlineAfter())) {
                list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("deadline").as(Date.class), taskFilter.getDeadlineAfter()));

            }
            if (!ObjectUtils.isEmpty(taskFilter.getCreatedAfter()) &&
                    !ObjectUtils.isEmpty(taskFilter.getCreatedBefore())) {

                list.add(criteriaBuilder.between(root.get("creationDate"),
                        taskFilter.getCreatedAfter(),
                        taskFilter.getCreatedBefore()));
            } else {
                if (!ObjectUtils.isEmpty(taskFilter.getCreatedAfter())) {
                    list.add(criteriaBuilder.greaterThanOrEqualTo(root.get("creationDate").as(Date.class),
                            taskFilter.getCreatedAfter()));
                } else {
                    if (!ObjectUtils.isEmpty(taskFilter.getCreatedBefore())) {
                        list.add(criteriaBuilder.lessThanOrEqualTo(root.get("creationDate").as(Date.class),
                                taskFilter.getCreatedBefore()));
                    }
                }
            }

            if (list.isEmpty()) {
                return query.where().orderBy(criteriaBuilder.desc(root.get("creationDate"))).getRestriction();
            }
            return query.where(criteriaBuilder.and(list.toArray(Predicate[]::new))).orderBy(criteriaBuilder.desc(root.get("creationDate"))).getRestriction();
        });
    }
}
