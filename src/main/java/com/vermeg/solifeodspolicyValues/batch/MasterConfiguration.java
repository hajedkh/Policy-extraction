package com.vermeg.solifeodspolicyValues.batch;

import com.vermeg.solifeodspolicyValues.dtos.Policy;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.integration.chunk.RemoteChunkingManagerStepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;
import javax.sql.DataSource;


@Configuration
@Profile("manager")
public  class MasterConfiguration {

    @Autowired
    @Qualifier("solifeDataSource")
    DataSource solifeDataSource;


    @Autowired
    private JobBuilderFactory jobBuilderFactory;


    @Autowired
    private RemoteChunkingManagerStepBuilderFactory remoteChunkingManagerStepBuilderFactory;


    @Bean
    public DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public QueueChannel replies() {
        return new QueueChannel();
    }




    @Bean
    public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows.from(requests())
                .log()
                .handle(Jms.outboundAdapter(connectionFactory).destination("requests"))
                .get();
    }



    @Bean
    public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("replies"))
                .channel(replies())
                .get();
    }



    @Bean
    public JdbcCursorItemReader<Policy> jdbcReader() {
        return new JdbcCursorItemReaderBuilder<Policy>()
                .dataSource(solifeDataSource)
                .name("policyItemReader")
                .sql("SELECT ID,VALUE FROM \"POLICY\"")
                .rowMapper((rs, rowNum) -> {
                    Policy policy = new Policy();
                    policy.setId(Long.valueOf(rs.getString(1)));
                    policy.setValue(rs.getString(2));
                    return policy;
                })
                .build();
    }

    @Bean
    public TaskletStep step1() {
        return this.remoteChunkingManagerStepBuilderFactory.get("step1")
                .inputChannel(replies())
                .outputChannel(requests())
                .reader(jdbcReader())
                .build();
    }

    @Bean
    public Job remoteChunkingJob(JobCompletionNotificationListener listener) {
        return this.jobBuilderFactory.get("remoteChunkingJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(step1())
                .build();
    }
}
