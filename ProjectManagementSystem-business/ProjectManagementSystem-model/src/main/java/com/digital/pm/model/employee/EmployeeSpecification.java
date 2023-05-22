package com.digital.pm.model.employee;

import com.digital.pm.common.filters.EmployeeFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {
    public static Specification<Employee> getSpec(EmployeeFilter employee) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!ObjectUtils.isEmpty(employee.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), employee.getId()));
            }
            if (!ObjectUtils.isEmpty(employee.getPost())) {
                predicates.add(criteriaBuilder.equal(root.get("post"), employee.getPost()));
            }
            if (!ObjectUtils.isEmpty(employee.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), employee.getStatus()));
            }
            if (!ObjectUtils.isEmpty(employee.getEmail())) {
                predicates.add(criteriaBuilder.equal(root.get("email"), employee.getEmail()));
            }
            if (!ObjectUtils.isEmpty(employee.getFirstName())) {
                predicates.add(criteriaBuilder.equal(root.get("firstName"), employee.getFirstName()));
            }
            if (!ObjectUtils.isEmpty(employee.getLastName())) {
                predicates.add(criteriaBuilder.equal(root.get("lastName"), employee.getLastName()));
            }
            if (!ObjectUtils.isEmpty(employee.getPatronymic())) {
                predicates.add(criteriaBuilder.equal(root.get("patronymic"), employee.getPatronymic()));
            }
            if (!ObjectUtils.isEmpty(employee.getAccount())) {
                predicates.add(criteriaBuilder.equal(root.get("account"), employee.getAccount()));

            }
            if (predicates.isEmpty()) {
                return query.where().getRestriction();
            }
            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();

        });
    }
}
