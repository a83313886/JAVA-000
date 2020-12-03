package com.example.week07demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

// @Configuration
// @EnableJpaRepositories(
//         basePackages = "com.example.week07demo.repository.db2",
//         entityManagerFactoryRef = "db2EntityManager",
//         transactionManagerRef = "db2TransactionManager"
// )
// @EnableTransactionManagement
public class DB2JpaConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean db2EntityManager(EntityManagerFactoryBuilder builder,
                                                                    @Qualifier("secondaryDataSource") DataSource dataSource,
                                                                    @Qualifier("vendorProperties") Map<String,
                                                                            Object> vendorProperties) {
        return builder
                .dataSource(dataSource)
                .properties(vendorProperties)
                .packages("com.example.week07demo.domain") //设置实体类所在位置
                .persistenceUnit("db2PersistenceUnit")
                .build();
    }

    @Bean
    public PlatformTransactionManager db2TransactionManager(@Qualifier("db2EntityManager") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
