package com.example.week07demo.domain;



import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "T1")
public class T1DO {
    @Id
    private Integer id;

}
