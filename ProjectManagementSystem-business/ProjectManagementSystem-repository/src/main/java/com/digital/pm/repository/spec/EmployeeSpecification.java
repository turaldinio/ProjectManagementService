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

import static com.digital.pm.repository.spec.FilterHandler.getFormatedString;

public class EmployeeSpecification {
    public static Specification<Employee> getSpec(EmployeeFilter employee) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("status"), EmployeeStatus.ACTIVE));

            if (employee == null) {
                return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();
            }

            if (!ObjectUtils.isEmpty(employee.getEmail())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("email")), getFormatedString(employee.getEmail()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employee.getFirstName())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("firstName")), getFormatedString(employee.getFirstName()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employee.getLastName())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("lastName")), getFormatedString(employee.getLastName()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employee.getPatronymic())) {
                predicates
                        .add(query.
                                where(criteriaBuilder.
                                        like(criteriaBuilder.lower(root.get("patronymic")), getFormatedString(employee.getPatronymic()))).
                                getRestriction());
            }
            if (!ObjectUtils.isEmpty(employee.getLogin())) {
                Join<Credential, Employee> join = root.join("credential");

                predicates.add(query.where(criteriaBuilder.like(criteriaBuilder.lower(join.get("login")),
                        getFormatedString(employee.getLogin()))).getRestriction());
            }

            return query.where(criteriaBuilder.and(predicates.toArray(Predicate[]::new))).getRestriction();

        });
    }


}
