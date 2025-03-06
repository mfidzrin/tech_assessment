package com.assessment.transaction_manager.model;

public record TransactionOrder(String accountNo, String transactionAmount, String description, String transactionDate, String transactionTime, String customerId) {
}
