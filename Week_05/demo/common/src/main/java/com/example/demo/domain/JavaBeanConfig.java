package com.example.demo.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class JavaBeanConfig {
    @Bean
    public Klass klass(List<Student> students) {
        Klass klass = new Klass();
        klass.setStudents(students);
        return klass;
    }

    @Bean
    public Student student() {
        Student student = new Student();
        student.setName("John");
        return student;
    }
    @Bean
    public Student student100() {
        Student student = new Student();
        student.setName("student100");
        return student;
    }

}
