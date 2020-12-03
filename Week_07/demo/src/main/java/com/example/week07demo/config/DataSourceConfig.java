package com.example.week07demo.config;

import com.example.week07demo.datasource.DataSourceTypeEnum;
import com.example.week07demo.datasource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.primary")
    public DataSource firstDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "secondaryDataSource")
    @ConfigurationProperties("spring.datasource.secondary")
    public DataSource secondDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "thirdDataSource")
    @ConfigurationProperties("spring.datasource.third")
    public DataSource thirdDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DynamicDataSource dynamicDatasource(DataSource primaryDatasource, List<DataSource> allDataSources){

        Map<Object,Object> targetDatasource = new HashMap<>(allDataSources.size());
        targetDatasource.put(DataSourceTypeEnum.MASTER, primaryDatasource);

        // 从库负载均衡
        DataSource[] slaveDataSourcesArray = allDataSources
                .stream()
                .filter(dataSource -> dataSource != primaryDatasource)
                .toArray(DataSource[]::new);
        for (int i = 0; i < slaveDataSourcesArray.length; i++) {
            targetDatasource.put(DataSourceTypeEnum.SLAVE + "_" + i, slaveDataSourcesArray[i]);
        }

        DynamicDataSource dynamicDatasource = new DynamicDataSource();
        dynamicDatasource.setSlaveSize(allDataSources.size() - 1);
        dynamicDatasource.setTargetDataSources(targetDatasource);
        dynamicDatasource.setDefaultTargetDataSource(primaryDatasource);
        return dynamicDatasource;
    }

    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;

    @Bean(name = "vendorProperties")
    public Map<String, Object> getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }
}
