package com.vermeg.solifeodspolicyValues.batch;


import com.vermeg.solifeodspolicyValues.dtos.Policy;
import com.vermeg.solifeodspolicyValues.dtos.PolicyActuarialValue;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;



@Configuration
@Profile("basic")
public class PolicyBatchConfiguration {



    @Autowired
    @Qualifier("solifeDataSource")
    DataSource solifeDataSource;

    @Autowired
    @Qualifier("odsDataSource")
    DataSource odsDatasource;

    @Autowired
    @Qualifier("batchDataSource")
    private DataSource batchDataSource;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public PlatformTransactionManager transactionManager;


    @Bean
    protected JobRepository jobRepository(DataSource batchDataSource) throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(batchDataSource);
        factory.setTransactionManager(transactionManager);
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE");
        factory.setTablePrefix("BATCH_");
        factory.setMaxVarCharLength(1000);
        return factory.getObject();
    }

    @Bean
    public PolicyItemProcessor simplePolicyProcessor() {
        return new PolicyItemProcessor();
    }

    @Bean
    public JdbcCursorItemReader<Policy> jdbcReader() {
        return new JdbcCursorItemReaderBuilder<Policy>()
                .dataSource(solifeDataSource)
                .name("policyItemReader")
                .sql("SELECT ID,VALUE FROM POLICY")
                .rowMapper((rs, rowNum) -> {
                    Policy policy = new Policy();
                    policy.setId(Long.valueOf(rs.getString(1)));
                    policy.setValue(rs.getString(2));
                    return policy;
                })
                .build();
    }


    @Bean
    public JdbcBatchItemWriter<PolicyActuarialValue> writer() {
        return new JdbcBatchItemWriterBuilder<PolicyActuarialValue>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO policy_actuarial_value (id, TOTAL_PAIED_PREMIUMS,BENIFITS_FROM_INVESTMENT,TOTAL_FEES_VALUE," +
                        "SURRENDER_VALUE) VALUES (:id, :totalPaiedPremiums, :benifitsFromInvestment, :totalFeesValue," +
                        ":surrenderValue)")
                .dataSource(odsDatasource)
                .build();
    }

    @Bean
    public Job simpleJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("simpleExtractionJob1")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .start(this.step1(writer()))
                .build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<PolicyActuarialValue> writer) {
        return stepBuilderFactory.get("step1")
                .<Policy, PolicyActuarialValue>chunk(10)
                .reader(jdbcReader())
                .processor(simplePolicyProcessor())
                .writer(writer)
                .build();
    }


}
