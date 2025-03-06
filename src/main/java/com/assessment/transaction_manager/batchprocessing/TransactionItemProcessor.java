package com.assessment.transaction_manager.batchprocessing;

import com.assessment.transaction_manager.model.TransactionOrder;
import org.springframework.batch.item.ItemProcessor;

public class TransactionItemProcessor implements ItemProcessor<TransactionOrder, TransactionOrder> {
    @Override
    public TransactionOrder process(final TransactionOrder item) {
        return new TransactionOrder(item.accountNo(), item.transactionAmount(), item.description(), item.transactionDate(), item.transactionTime(), item.customerId());
    }
}
