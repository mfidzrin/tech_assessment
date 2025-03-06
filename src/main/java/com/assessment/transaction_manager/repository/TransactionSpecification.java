package com.assessment.transaction_manager.repository;

import com.assessment.transaction_manager.model.Search;
import com.assessment.transaction_manager.model.TransactionEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {

    public static Specification<TransactionEntity> getSpecifications(Search search) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (search.customerId() != null && !search.customerId().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(root.get("customerId"), search.customerId()));
            }
            if (search.accountNo() != null && !search.accountNo().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, root.get("accountNo").in(search.accountNo()));
            }
            if (search.description() != null && !search.description().isEmpty()) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(root.get("description"), "%" + search.description() + "%"));
            }
            return predicate;
        };

    }
}
