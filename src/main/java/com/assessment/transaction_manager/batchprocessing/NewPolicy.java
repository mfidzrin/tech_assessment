package com.assessment.transaction_manager.batchprocessing;

import org.springframework.batch.item.file.separator.DefaultRecordSeparatorPolicy;

public class NewPolicy extends DefaultRecordSeparatorPolicy {

    @Override
    public boolean isEndOfRecord(String line) {
        return !line.trim().isEmpty() && super.isEndOfRecord(line);
    }

    @Override
    public String postProcess(String line) {
        if (line.trim().isEmpty()) {
            return null;
        }
        return super.postProcess(line);
    }
}
