package com.digital.pm.repository.spec;

import com.digital.pm.common.filters.employee.EmployeeDaoFilter;
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
    public static Specification<Employee> getSpec(EmployeeDaoFilter employeeDaoFilter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (Objects.isNull(employeeDaoFilter)) {
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
            }


            if (!ObjectUtils.isEmpty(employeeDaoFilter.getEmployeeStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), employeeDaoFilter.getEmployeeStatus()));
            }

            if (!ObjectUtils.isEmpty(employeeDaoFilter.getEmail())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("email")), SpecHandler.getFormatedString(employeeDaoFilter.getEmail()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employeeDaoFilter.getFirstName())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("firstName")), SpecHandler.getFormatedString(employeeDaoFilter.getFirstName()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employeeDaoFilter.getLastName())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("lastName")), SpecHandler.getFormatedString(employeeDaoFilter.getLastName()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employeeDaoFilter.getPatronymic())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("patronymic")), SpecHandler.getFormatedString(employeeDaoFilter.getPatronymic()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employeeDaoFilter.getLogin())) {
                Join<Credential, Employee> join = root.join("credential");

                predicates.add(query.where(criteriaBuilder.like(criteriaBuilder.lower(join.get("login")),
                        SpecHandler.getFormatedString(employeeDaoFilter.getLogin()))).getRestriction());
            }

            if (!ObjectUtils.isEmpty(employeeDaoFilter.getText())) {
                Join<Credential, Employee> join = root.join("credential");

                predicates.add(query.where(criteriaBuilder.or(
                                criteriaBuilder.like(criteriaBuilder.lower(join.get("login")), SpecHandler.getFormatedString(employeeDaoFilter.getText())),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), SpecHandler.getFormatedString(employeeDaoFilter.getText())),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), SpecHandler.getFormatedString(employeeDaoFilter.getText())),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), SpecHandler.getFormatedString(employeeDaoFilter.getText())),
                                criteriaBuilder.like(criteriaBuilder.lower(root.get("patronymic")), SpecHandler.getFormatedString(employeeDaoFilter.getText())))).
                        getRestriction());
            }

            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();

        });
    }
}
