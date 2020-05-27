package com.fineway.disasterSMS.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "disasterDataSource")
    @Primary
    @Qualifier("disasterDataSource")
    @ConfigurationProperties(prefix = "spring.disaster.datasource")
    public DataSource disasterDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "personDataSource")
    @Qualifier("personDataSource")
    @ConfigurationProperties(prefix = "spring.person.datasource")
    public DataSource personDataSource() {
        return DataSourceBuilder.create().build();
    }
}
