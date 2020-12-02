package com.example.week07demo.repository.db1;


import com.example.week07demo.domain.T1DO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Qualifier("db1T1Repository")
@Repository
public interface T1Repository extends JpaRepository<T1DO, Long> {
}
