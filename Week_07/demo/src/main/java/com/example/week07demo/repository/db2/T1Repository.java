package com.example.week07demo.repository.db2;


import com.example.week07demo.domain.T1DO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("db2T1Repository")
public interface T1Repository extends JpaRepository<T1DO, Long> {
}
