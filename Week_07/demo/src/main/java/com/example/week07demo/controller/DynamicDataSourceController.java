package com.example.week07demo.controller;

import com.example.week07demo.datasource.ReadOnly;
import com.example.week07demo.domain.T1DO;
import com.example.week07demo.repository.dynamic.T1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/")
public class DynamicDataSourceController {
    @Autowired
    private T1Repository repository;

    @GetMapping
    public String index() {
        return "main";
    }

    @PostMapping("/t1s")
    public T1DO add() {
        T1DO t1DO = new T1DO();
        t1DO.setId(new Random().nextInt());
        return repository.save(t1DO);
    }

    @ReadOnly
    @GetMapping("/t1s")
    public List<T1DO> getAll() {
        return repository.findAll();
    }
}
