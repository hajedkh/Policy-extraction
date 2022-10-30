package com.vermeg.solifeodspolicyValues.batch;

import com.vermeg.solifeodspolicyValues.models.Policy;
import com.vermeg.solifeodspolicyValues.models.PolicyActuarialValue;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.batch.integration.chunk.RemoteChunkingWorkerBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.jms.dsl.Jms;

import javax.sql.DataSource;

@Configuration
@Profile("worker")
public class WorkerConfiguration {

    @Autowired
    @Qualifier("odsDataSource")
    DataSource odsDatasource;


    @Autowired
    private RemoteChunkingWorkerBuilder<Policy, PolicyActuarialValue> remoteChunkingWorkerBuilder;


    @Bean
    public DirectChannel requests() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel replies() {
        return new DirectChannel();
    }


    @Bean
    public IntegrationFlow inboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("requests"))
                .channel(requests()).get();
    }


    @Bean
    public IntegrationFlow outboundFlow(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows.from(replies()).handle(Jms.outboundAdapter(connectionFactory).destination("replies"))
                .get();
    }

    @Bean
    public PolicyItemProcessor simplePolicyProcessor() {
        return new PolicyItemProcessor();
    }


    @Bean
    public IntegrationFlow workerIntegrationFlow() {
        return this.remoteChunkingWorkerBuilder.itemProcessor(simplePolicyProcessor()).itemWriter(writer(odsDatasource))
                .inputChannel(requests()).outputChannel(replies()).build();
    }


    @Bean
    public JdbcBatchItemWriter<PolicyActuarialValue> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<PolicyActuarialValue>()
                .dataSource(dataSource)
                .beanMapped()
                .sql("INSERT INTO policy_actuarial_value (id, value) VALUES (:id, :value)")
                .build();
    }
}
