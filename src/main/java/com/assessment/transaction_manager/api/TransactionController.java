package com.assessment.transaction_manager.api;

import com.assessment.transaction_manager.model.Search;
import com.assessment.transaction_manager.model.TransactionEntity;
import com.assessment.transaction_manager.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<Page<TransactionEntity>> getTransactions(Pageable pageable,
                                                                   @RequestParam(required = false) String customerId,
                                                                   @RequestParam(required = false, value = "accountNo") List<String> accountNo,
                                                                   @RequestParam(required = false) String description) {
        Search search = new Search(customerId, accountNo, description);
        Page<TransactionEntity> transactions = transactionService.findAllPaginated(search, pageable);
        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionEntity> getTransaction(@PathVariable Long id) {
        TransactionEntity transaction = transactionService.findById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<TransactionEntity> updateTransaction(@RequestBody TransactionEntity transactionEntity) {
        TransactionEntity updatedTransaction = transactionService.update(transactionEntity);
        return new ResponseEntity<>(updatedTransaction, HttpStatus.OK);
    }

}
