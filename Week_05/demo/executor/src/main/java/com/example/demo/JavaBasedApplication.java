package com.example.demo;

import com.example.demo.domain.Klass;
import com.example.demo.domain.School;
import com.example.demo.domain.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

public class JavaBasedApplication {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(JavaBeanConfig.class);
        School school = context.getBean(School.class);
        System.out.println(school);
    }

    @Configuration
    public static class JavaBeanConfig {
        @Bean
        public School school(Klass klasses) {
            School school = new School();
            school.setClass1(klasses);
            return school;
        }

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
}
