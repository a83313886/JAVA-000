package com.example.week07demo.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicLong;

public class DynamicDataSource extends AbstractRoutingDataSource {
    private final AtomicLong counter = new AtomicLong();
    private int slaveSize = 0;

    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceTypeEnum dataSourceType = DataSourceContextHolder.getDataSourceType();
        // 没有设置则走默认MASTER数据源
        if (dataSourceType == null) {
            return DataSourceTypeEnum.MASTER;
        }

        // 实现round robin轮询从库
        long l = counter.incrementAndGet();
        int index = (int) l % slaveSize;
        return DataSourceTypeEnum.SLAVE + "_" + index;
    }

    public void setSlaveSize(int slaveSize) {
        this.slaveSize = slaveSize;
    }
}
