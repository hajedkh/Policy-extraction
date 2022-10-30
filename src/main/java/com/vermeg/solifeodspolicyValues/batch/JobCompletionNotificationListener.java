package com.vermeg.solifeodspolicyValues.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    public static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);


    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("JOB FINISHED!");
            jobExecution.getStepExecutions().stream().forEach(stepExecution -> log.info("Step \"" +
                    stepExecution.getStepName() + "\"  completed with Status " + stepExecution.getStatus() +
                    " :\n> Commit count : " + stepExecution.getCommitCount() + "\n> Read count : "
                    + stepExecution.getReadCount() + "\n> Write count : " + stepExecution.getWriteCount()));

        }
    }
}
