package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Board1View;

public interface Board1ViewRepository extends JpaRepository<Board1View, Long>{
    
    List<Board1View> findByNoAndTitleOrderByNoAsc(Long no, String title);

    List<Board1View> findByNoOrTitleOrderByNoAsc(Long no, String title);

    List<Board1View> findByNoInOrderByNoAsc(List<Long> no);

    List<Board1View> findByTitleInOrderByNoAsc(List<String> title);
}
