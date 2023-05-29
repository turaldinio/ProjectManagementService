package com.digital.pm.repository.spec;


import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.model.task.Task;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {
    public static Specification<Task> getSpec(TaskFilter taskFilter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(taskFilter.getName())) {
                list.add(criteriaBuilder.equal(root.get("name"), taskFilter.getName()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getStatus())) {
                list.add(criteriaBuilder.equal(root.get("status"), taskFilter.getStatus()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getAuthorId())) {
                list.add(criteriaBuilder.equal(root.get("authorId"), taskFilter.getAuthorId()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getExecutorId())) {
                list.add(criteriaBuilder.equal(root.get("executorId"), taskFilter.getExecutorId()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getDeadline())) {
                list.add(criteriaBuilder.equal(root.get("deadline"), taskFilter.getDeadline()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getDateOfCreation())) {
                list.add(criteriaBuilder.equal(root.get("dateOfCreation"), taskFilter.getDateOfCreation()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getDateOfCreation())) {
                list.add(criteriaBuilder.equal(root.get("dateOfCreation"), taskFilter.getDateOfCreation()));
            }
            if (list.isEmpty()) {
                return query.where().getRestriction();
            }
            return query.where(criteriaBuilder.and(list.toArray(Predicate[]::new))).getRestriction();
        });
    }
}
