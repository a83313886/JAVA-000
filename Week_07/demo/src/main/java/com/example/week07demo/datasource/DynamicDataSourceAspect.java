package com.example.week07demo.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicDataSourceAspect {
    @Before("@annotation(readOnly)")
    public void setReadOnly(JoinPoint point, ReadOnly readOnly) {
        DataSourceContextHolder.set(DataSourceTypeEnum.SLAVE);
    }

    @After("@annotation(readOnly)")
    public void cleanReadOnly(JoinPoint point, ReadOnly readOnly) {
        DataSourceContextHolder.clear();
    }

}
