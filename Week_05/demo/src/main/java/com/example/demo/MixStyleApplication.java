package com.example.demo;

import com.example.demo.domain.School;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MixStyleApplication {

    public static void main(String[] args) {
        method1();
    }

    public static void method1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("config/enableAnnotationConfig.xml", "config/componentscan.xml");
        School school = context.getBean(School.class);
        System.out.println(school);
    }
}
