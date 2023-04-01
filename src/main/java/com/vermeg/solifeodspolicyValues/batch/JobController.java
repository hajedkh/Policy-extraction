package com.vermeg.solifeodspolicyValues.batch;


import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
public class JobController {

    @Autowired
    private JmsTemplate localJmsTemplate;

    @Autowired
    private ActiveMQConnectionFactory connectionFactory;


    @Autowired
    private JobLauncher simpleJobLauncher;

    @Autowired
    private ApplicationContext currentContext;




    @GetMapping("/testJob")
    public void perform() throws Exception {

        localJmsTemplate.setConnectionFactory(connectionFactory);

        System.out.println("Job Started at :" + LocalDateTime.now());

        JobParameters param = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        JobExecution execution = simpleJobLauncher.run(currentContext.getBean(Job.class), param);

        System.out.println("Job finished with status :" + execution.getStatus());
    }
}
