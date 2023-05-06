package com.vermeg.solifeodspolicyValues.batch;

import com.vermeg.solifeodspolicyValues.dtos.Policy;
import com.vermeg.solifeodspolicyValues.dtos.PolicyActuarialValue;
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
    public DirectChannel workerRequests() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel workerReplies() {
        return new DirectChannel();
    }


    @Bean
    public IntegrationFlow inboundFlowWorker(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows.from(Jms.messageDrivenChannelAdapter(connectionFactory).destination("requests"))
                .channel(workerRequests()).get();
    }


    @Bean
    public IntegrationFlow outBoundFlowWorker(ActiveMQConnectionFactory connectionFactory) {
        return IntegrationFlows.from(workerReplies()).handle(Jms.outboundAdapter(connectionFactory).destination("replies"))
                .get();
    }

    @Bean
    public PolicyItemProcessor simplePolicyProcessor() {
        return new PolicyItemProcessor();
    }


    @Bean
    public IntegrationFlow workerIntegrationFlow() {
        return this.remoteChunkingWorkerBuilder.itemProcessor(simplePolicyProcessor())
                .itemWriter(writer(odsDatasource))
                .inputChannel(workerRequests())
                .outputChannel(workerReplies())
                .build();
    }


    @Bean
    public JdbcBatchItemWriter<PolicyActuarialValue> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<PolicyActuarialValue>()
                .dataSource(dataSource)
                .beanMapped()
                .sql("INSERT INTO policy_actuarial_value (id, TOTAL_PAIED_PREMIUMS,BENIFITS_FROM_INVESTMENT,TOTAL_FEES_VALUE," +
                        "SURRENDER_VALUE) VALUES (:id, :totalPaiedPremiums, :benifitsFromInvestment, :totalFeesValue," +
                        ":surrenderValue)")
                .build();
    }
}
