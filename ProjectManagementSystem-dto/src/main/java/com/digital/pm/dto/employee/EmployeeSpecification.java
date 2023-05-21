package com.digital.pm.dto.employee;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

public class EmployeeSpecification {
    public static Specification<EmployeeDto> getSpec(EmployeeDto employeeDto) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!ObjectUtils.isEmpty(employeeDto.getId())) {
                predicates.add(criteriaBuilder.equal(root.get("id"), employeeDto.getId()));
            }
            if (!ObjectUtils.isEmpty(employeeDto.getPost())) {
                predicates.add(criteriaBuilder.equal(root.get("post"), employeeDto.getPost()));
            }
            if (!ObjectUtils.isEmpty(employeeDto.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), employeeDto.getStatus()));
            }
            if (!ObjectUtils.isEmpty(employeeDto.getEmail())) {
                predicates.add(criteriaBuilder.equal(root.get("email"), employeeDto.getEmail()));
            }
            if (!ObjectUtils.isEmpty(employeeDto.getFirstName())) {
                predicates.add(criteriaBuilder.equal(root.get("firstName"), employeeDto.getFirstName()));
            }
            if (!ObjectUtils.isEmpty(employeeDto.getLastName())) {
                predicates.add(criteriaBuilder.equal(root.get("lastName"), employeeDto.getLastName()));
            }
            if (!ObjectUtils.isEmpty(employeeDto.getPatronymic())) {
                predicates.add(criteriaBuilder.equal(root.get("patronymic"), employeeDto.getPatronymic()));
            }
            if (!ObjectUtils.isEmpty(employeeDto.getAccount())) {
                predicates.add(criteriaBuilder.equal(root.get("account"), employeeDto.getAccount()));

            }
            if (predicates.isEmpty()) {
                return query.where().getRestriction();
            }
            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();

        });
    }
}
