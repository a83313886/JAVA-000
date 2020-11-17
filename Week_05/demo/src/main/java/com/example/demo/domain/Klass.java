package com.example.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class Klass {
    private Collection<Student> students;

    public Collection<Student> getStudents() {
        return students;
    }

    @Autowired
    public void setStudents(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Klass{" +
                "students=" + students +
                '}';
    }
}
