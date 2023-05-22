package com.digital.pm.model.task;

import com.digital.pm.common.filters.ProjectFilter;
import com.digital.pm.common.filters.TaskFilter;
import com.digital.pm.model.project.Project;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class TaskSpecification {
    public static Specification<Task> getSpec(TaskFilter taskFilter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(taskFilter.getId())) {
                list.add(criteriaBuilder.equal(root.get("id"), taskFilter.getId()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getName())) {
                list.add(criteriaBuilder.equal(root.get("name"), taskFilter.getName()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getDescription())) {
                list.add(criteriaBuilder.equal(root.get("description"), taskFilter.getDescription()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getStatus())) {
                list.add(criteriaBuilder.equal(root.get("status"), taskFilter.getStatus()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getAuthor())) {
                list.add(criteriaBuilder.equal(root.get("author"), taskFilter.getAuthor()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getLaborCosts())) {
                list.add(criteriaBuilder.equal(root.get("laborCost"), taskFilter.getLaborCosts()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getDeadline())) {
                list.add(criteriaBuilder.equal(root.get("deadline"), taskFilter.getDeadline()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getDateOfCreation())) {
                list.add(criteriaBuilder.equal(root.get("dateOfCreation"), taskFilter.getDateOfCreation()));
            }
            if (!ObjectUtils.isEmpty(taskFilter.getUpdateTime())) {
                list.add(criteriaBuilder.equal(root.get("updateTime"), taskFilter.getUpdateTime()));
            }
            if (list.isEmpty()) {
                return query.where().getRestriction();
            }
            return query.where(criteriaBuilder.and(list.toArray(Predicate[]::new))).getRestriction();
        });
    }
}
