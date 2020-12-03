package com.example.week07demo.repository.dynamic;


import com.example.week07demo.domain.T1DO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("dynamicT1Repository")
public interface T1Repository extends JpaRepository<T1DO, Long> {
}
