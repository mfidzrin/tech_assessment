package com.assessment.transaction_manager.repository;

import com.assessment.transaction_manager.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionOrderRepository extends PagingAndSortingRepository<TransactionEntity, Long>, JpaSpecificationExecutor<TransactionEntity>, JpaRepository<TransactionEntity, Long> {
}
