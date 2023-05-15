package com.example.repository.library;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Student2;
import com.example.entity.Student2Projection;

public interface Student2Repository extends JpaRepository<Student2, String> {

    // select * from 테이블명 order by no asc
    List<Student2> findAllByOrderBySnameAsc();

    long countBySemail(String semail);

    Student2Projection findBySemail(String semail);
}
