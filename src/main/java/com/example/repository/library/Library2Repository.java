package com.example.repository.library;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Library2;
import java.util.List;


public interface Library2Repository extends JpaRepository<Library2, BigInteger> {

    // select from 테이블명 where 컬럼=?
    // findBy + 컬럼

    // select * from 테이블명 order by no asc
    List<Library2> findAllByOrderByLnameAsc();

    // 연락처별 내림차순 선별
    List<Library2> findAllByOrderByLphoneDesc();
    
}
