package com.assessment.transaction_manager.service;

import com.assessment.transaction_manager.model.Search;
import com.assessment.transaction_manager.model.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    Page<TransactionEntity> findAllPaginated(Search search, Pageable pageable);

    TransactionEntity update(final TransactionEntity transactionEntity);

    TransactionEntity findById(Long id);
}
