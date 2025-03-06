package com.assessment.transaction_manager.model;

import java.util.List;

public record Search(String customerId, List<String> accountNo, String description) {
}
