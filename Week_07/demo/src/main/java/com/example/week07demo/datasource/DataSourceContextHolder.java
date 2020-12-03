package com.example.week07demo.datasource;

import org.springframework.util.Assert;

public class DataSourceContextHolder {
    private static final ThreadLocal<DataSourceTypeEnum> CONTEXT = new ThreadLocal<>();

    public static void set(DataSourceTypeEnum dataSourceType) {
        Assert.notNull(dataSourceType, "dataSourceType cannot be null");
        CONTEXT.set(dataSourceType);
    }

    public static DataSourceTypeEnum getDataSourceType() {
        return CONTEXT.get();
    }

    public static void clear() {
        CONTEXT.remove();
    }
}
