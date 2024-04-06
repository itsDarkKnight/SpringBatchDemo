package com.SpringBatch.example;

import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class JobCompletionImpl implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("Batch processing strated");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus()== BatchStatus.COMPLETED)
        {
            System.out.println("Processing completed....");
        }
    }
}
