package com.example.week07demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        basePackages = "com.example.week07demo.repository.dynamic",
        entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "transactionManager"
)
@EnableTransactionManagement
public class DynamicDataSourceJpaConfig {
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManager(EntityManagerFactoryBuilder builder,
                                                                    @Qualifier("dynamicDatasource") DataSource dataSource,
                                                                    @Qualifier("vendorProperties") Map<String,
                                                                            Object> vendorProperties) {
        return builder
                .dataSource(dataSource)
                .properties(vendorProperties)
                .packages("com.example.week07demo.domain")
                .persistenceUnit("dynamicPersistenceUnit")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
