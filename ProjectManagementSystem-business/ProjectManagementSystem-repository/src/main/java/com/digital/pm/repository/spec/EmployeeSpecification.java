package com.digital.pm.repository.spec;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.model.Employee;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {
    public static Specification<Employee> getSpec(EmployeeFilter employee) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("status"), EmployeeStatus.ACTIVE));

            if (employee == null) {
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();

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

            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();

        });
    }
}
