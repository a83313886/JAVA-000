package com.example.week07demo.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicDataSourceAspect {
    @Before("@annotation(ReadOnly)")
    public void changeDataSource(JoinPoint point, ReadOnly readOnly) {

    }
}
