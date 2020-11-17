package com.example.demo;

import com.example.demo.domain.School;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ComposingXmlBasedApplication {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("config/components.xml");
        School school = context.getBean(School.class);
        System.out.println(school);
    }
}
