package com.example.week07demo.controller;

import com.example.week07demo.domain.T1DO;
import com.example.week07demo.repository.db1.T1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/")
public class MainController {
    @Autowired
    private T1Repository db1T1Repository;
    @Autowired
    private com.example.week07demo.repository.db2.T1Repository db2T1Repository;

    @GetMapping
    public String index() {
        return "main";
    }

    @PostMapping("/t1s")
    public T1DO add() {
        T1DO t1DO = new T1DO();
        t1DO.setId(new Random().nextInt());
        return db1T1Repository.save(t1DO);
    }

    @GetMapping("/t1s")
    public List<T1DO> getAll() {
        return db2T1Repository.findAll();
    }
}
