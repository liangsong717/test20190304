package com.fineway.disasterSMS.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "personEntityManagerFactory",//配置连接工厂 entityManagerFactory
        transactionManagerRef = "personTransactionManager", //配置 事物管理器  transactionManager
        basePackages = {"com.fineway.disasterSMS.dao.person"}//设置持久层所在位置
)
public class PersonDataSourceConfig {

    @Autowired
    private JpaProperties jpaProperties;

    @Autowired
    @Qualifier("personDataSource")
    private DataSource personDataSource;// 自动注入配置好的数据源

    @Value("${spring.jpa.hibernate.person-dialect}")
    private String personDialect;// 获取对应的数据库方言


    /**
     *
     * @param builder
     * @return
     */
    @Bean(name = "personEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean personEntityManagerFactory(EntityManagerFactoryBuilder builder) {

        return builder
                //设置数据源
                .dataSource(personDataSource)
                //设置数据源属性
                .properties(getVendorProperties())
                //设置实体类所在位置.扫描所有带有 @Entity 注解的类
                .packages("com.fineway.disasterSMS.entity.person")
                // Spring会将EntityManagerFactory注入到Repository之中.有了 EntityManagerFactory之后,
                // Repository就能用它来创建 EntityManager 了,然后 EntityManager 就可以针对数据库执行操作
                .persistenceUnit("personPersistenceUnit")
                .build();

    }

    private Map<String, String> getVendorProperties() {
        Map<String,String> map = new HashMap<>();
        map.put("hibernate.dialect",personDialect);// 设置对应的数据库方言
        jpaProperties.setProperties(map);
        return jpaProperties.getProperties();
    }

    /**
     * 配置事物管理器
     *
     * @param builder
     * @return
     */
    @Bean(name = "personTransactionManager")
    @Primary
    PlatformTransactionManager personTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(personEntityManagerFactory(builder).getObject());
    }
}
