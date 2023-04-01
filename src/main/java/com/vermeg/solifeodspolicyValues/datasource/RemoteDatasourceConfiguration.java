package com.vermeg.solifeodspolicyValues.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.querydsl.SimpleEntityPathResolver;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class RemoteDatasourceConfiguration {

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.solife")
    public DataSourceProperties solifeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.ods")
    public DataSourceProperties odsDataSourceProperties() {
        return new DataSourceProperties();
    }



    @Primary
    @Bean(name="solifeDataSource")
    public DataSource solifeDataSource(){
        return solifeDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }

    @Bean(name="odsDataSource")
    public DataSource odsDataSource(){
        return odsDataSourceProperties()
                .initializeDataSourceBuilder()
                .build();
    }


}
