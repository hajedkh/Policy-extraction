package com.vermeg.solifeodspolicyValues.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class RemoteDatasourceConfiguration {

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
