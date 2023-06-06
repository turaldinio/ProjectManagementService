package com.digital.pm.repository.spec;

import com.digital.pm.common.enums.EmployeeStatus;
import com.digital.pm.common.filters.EmployeeFilter;
import com.digital.pm.model.Credential;
import com.digital.pm.model.Employee;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmployeeSpecification {
    public static Specification<Employee> getSpec(EmployeeFilter employee) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("status"), EmployeeStatus.ACTIVE));

            if (Objects.isNull(employee)) {
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
            }

            if (!ObjectUtils.isEmpty(employee.getEmail())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("email")), SpecHandler.getFormatedString(employee.getEmail()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employee.getFirstName())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("firstName")), SpecHandler.getFormatedString(employee.getFirstName()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employee.getLastName())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("lastName")), SpecHandler.getFormatedString(employee.getLastName()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employee.getPatronymic())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("patronymic")), SpecHandler.getFormatedString(employee.getPatronymic()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employee.getLogin())) {
                Join<Credential, Employee> join = root.join("credential");

                predicates.add(query.where(criteriaBuilder.like(criteriaBuilder.lower(join.get("login")),
                        SpecHandler.getFormatedString(employee.getLogin()))).getRestriction());
            }

            if (!ObjectUtils.isEmpty(employee.getText())) {
                Join<Credential, Employee> join = root.join("credential");

                predicates.add(query.where(criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.lower(join.get("login")), SpecHandler.getFormatedString(employee.getText())),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), SpecHandler.getFormatedString(employee.getText())),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), SpecHandler.getFormatedString(employee.getText())),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), SpecHandler.getFormatedString(employee.getText())),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("patronymic")), SpecHandler.getFormatedString(employee.getText())))).
                        getRestriction());
            }

            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();

        });
    }
}
