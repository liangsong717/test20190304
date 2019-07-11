package com.fineway.scaffolding.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourceConfig {

    @Autowired
    ApplicationContext applicationContext;

    /**
     * 适配oracle,mysql数据库schema设置
     * @param dataSource
     * @return
     */
    @Bean
    public DatabaseDataSourceConnectionFactoryBean dbUnitDatabaseConnection(
            DataSource dataSource){
        DatabaseDataSourceConnectionFactoryBean bean = new DatabaseDataSourceConnectionFactoryBean(dataSource);
        if(dataSource instanceof DruidDataSource){
            if(((DruidDataSource) dataSource).getDbType().equals("oracle")){
                bean.setSchema(((DruidDataSource)dataSource).getUsername());
            }
        }
        return bean;
    }

}
