package com.example.demo.domain;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class School {
    private String name;

    @Autowired
    private Collection<Klass> klasses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Klass> getKlasses() {
        return klasses;
    }

    public void setKlasses(Collection<Klass> klasses) {
        this.klasses = klasses;
    }

    @Override
    public String toString() {
        return "School{" +
                "name='" + name + '\'' +
                ", klasses=" + klasses +
                '}';
    }
}
