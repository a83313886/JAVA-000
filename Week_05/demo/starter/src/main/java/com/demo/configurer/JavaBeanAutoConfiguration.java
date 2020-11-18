package com.demo.configurer;

import com.example.demo.domain.Klass;
import com.example.demo.domain.School;
import com.example.demo.domain.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(School.class)
@EnableConfigurationProperties(JavaBeanConfigProperties.class)
public class JavaBeanAutoConfiguration {
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
    public Student student100(JavaBeanConfigProperties properties) {
        Student student = new Student();
        student.setName(properties.getName());
        System.out.println("properties:" + properties);
        return student;
    }

}
