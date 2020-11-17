package com.example.demo;

import com.example.demo.domain.School;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;

public class GroovyBasedApplication {
    public static void main(String[] args) {
        method4();
    }

    public static void method1() {
        ApplicationContext context = new GenericGroovyApplicationContext("config/components.groovy");
        School school = context.getBean(School.class);
        System.out.println(school);
    }

    public static void method2() {
        ApplicationContext context = new GenericGroovyApplicationContext("config/daos.groovy", "config/services.groovy");
        School school = context.getBean(School.class);
        System.out.println(school);
    }

    public static void method3() {
        ApplicationContext context = new GenericGroovyApplicationContext("config/componentsFromXml.groovy");
        School school = context.getBean(School.class);
        System.out.println(school);
    }

    public static void method4() {
        GenericApplicationContext context = new GenericApplicationContext();
        new GroovyBeanDefinitionReader(context).loadBeanDefinitions("config/componentsFromXml.groovy");
        context.refresh();
        School school = context.getBean(School.class);
        System.out.println(school);
    }
}
