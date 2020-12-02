package com.example.week07demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Map;

/*@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.week07demo.repository.db1",
        entityManagerFactoryRef = "userEntityManager",
        transactionManagerRef = "userTransactionManager"
)*/
public class PersistenceUserConfiguration {
    @Autowired
    @Qualifier("vendorProperties")
    private Map<String, Object> vendorProperties;

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean userEntityManager(EntityManagerFactoryBuilder builder,
                                                                    DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .properties(vendorProperties)
                .packages("com.example.week07demo.domain") //设置实体类所在位置
                .persistenceUnit("primaryPersistenceUnit")
                .build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager userTransactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}
