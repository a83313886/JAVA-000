package com.example.demo.domain;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class Student {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("PostConstruct on student");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("PreDestroy on student");

    }
}
