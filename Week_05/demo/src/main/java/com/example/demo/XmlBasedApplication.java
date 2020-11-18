package com.example.demo;

import com.example.demo.domain.School;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public class XmlBasedApplication {
    public static void main(String[] args) {
        // method1();
        method2();
    }

    private static void method1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("config/services.xml", "config/daos.xml");
        School school = context.getBean(School.class);
        System.out.println(school);
    }

    private static void method2() {
        GenericApplicationContext context = new GenericApplicationContext();
        new XmlBeanDefinitionReader(context).loadBeanDefinitions("config/services.xml", "config/daos.xml");
        context.refresh();
        School school = context.getBean(School.class);
        System.out.println(school);
    }
}
