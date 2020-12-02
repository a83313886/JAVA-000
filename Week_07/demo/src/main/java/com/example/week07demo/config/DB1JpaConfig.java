package com.example.week07demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.week07demo.repository.db1",
        entityManagerFactoryRef = "db1EntityManager",
        transactionManagerRef = "db1TransactionManager"
)
@EnableTransactionManagement
public class DB1JpaConfig {
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean db1EntityManager(EntityManagerFactoryBuilder builder,
                                                                    DataSource dataSource,
                                                                    @Qualifier("vendorProperties") Map<String,
                                                                            Object> vendorProperties) {
        return builder
                .dataSource(dataSource)
                .properties(vendorProperties)
                .packages("com.example.week07demo.domain") //设置实体类所在位置
                .persistenceUnit("db1PersistenceUnit")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager db1TransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
