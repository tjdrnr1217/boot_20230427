package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Board1View;

@Repository
public interface Board1ViewRepository extends JpaRepository<Board1View, BigDecimal> {
    List<Board1View> findByNoAndTitle(long no, String title);

    List<Board1View> findByNoOrTitle(long no, String title);

    List<Board1View> findByNoIn(List<Long> no);

    List<Board1View> findByTitleIn(List<String> no);

    
}
