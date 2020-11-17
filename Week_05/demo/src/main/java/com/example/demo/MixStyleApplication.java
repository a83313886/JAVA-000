package com.example.demo;

import com.example.demo.domain.School;
import org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class MixStyleApplication {

    public static void main(String[] args) {
        method1();
    }

    public static void method1() {
        GenericApplicationContext context = new GenericApplicationContext();
        new GroovyBeanDefinitionReader(context).loadBeanDefinitions("config/daos.groovy");
        new XmlBeanDefinitionReader(context).loadBeanDefinitions("config/services.xml");
        context.refresh();
        School school = context.getBean(School.class);
        System.out.println(school);
    }
}
