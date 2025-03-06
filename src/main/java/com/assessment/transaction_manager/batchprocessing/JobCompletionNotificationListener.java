package com.assessment.transaction_manager.batchprocessing;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println(jobExecution.getStatus().name());
        System.out.println("Job finished");
    }
}
