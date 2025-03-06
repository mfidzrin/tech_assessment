package com.assessment.transaction_manager.service;

import com.assessment.transaction_manager.exception.ConcurrentUpdateException;
import com.assessment.transaction_manager.model.Search;
import com.assessment.transaction_manager.model.TransactionEntity;
import com.assessment.transaction_manager.repository.TransactionOrderRepository;
import com.assessment.transaction_manager.repository.TransactionSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionOrderRepository transactionOrderRepository;

    public TransactionServiceImpl(TransactionOrderRepository transactionOrderRepository) {
        this.transactionOrderRepository = transactionOrderRepository;
    }

    @Override
    public Page<TransactionEntity> findAllPaginated(Search search, Pageable pageable) {
        Specification<TransactionEntity> specification = TransactionSpecification.getSpecifications(search);
        return transactionOrderRepository.findAll(specification, pageable);
    }

    @Override
    @Transactional
    public TransactionEntity update(TransactionEntity transactionEntity) {
        Optional<TransactionEntity> transactionEntityOptional = transactionOrderRepository.findById(transactionEntity.getId());
        if (transactionEntityOptional.isEmpty()) {
            return null;
        }
        TransactionEntity persistedTransaction = transactionEntityOptional.get();
        persistedTransaction.setDescription("askjdkjsd");
        try {
            return transactionOrderRepository.save(persistedTransaction);
        } catch (ObjectOptimisticLockingFailureException exception) {
            throw new ConcurrentUpdateException("Transaction with id " + persistedTransaction.getId() + " has been updated by another user.");
        }
    }

    @Override
    public TransactionEntity findById(Long id) {
        return transactionOrderRepository.findById(id).orElse(null);
    }
}
