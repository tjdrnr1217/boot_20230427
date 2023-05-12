package com.example.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.Board;

public interface BoardRepository extends JpaRepository<Board, BigDecimal> {

    List<Board> findAllByOrderByNoDesc();

    List<Board> findAllByOrderByNoDesc(Pageable pageable);

    List<Board> findByTitleIgnoreCaseContainingOrderByNoDesc(String title);

    List<Board> findByContentIgnoreCaseContainingOrderByNoDesc(String content);

    List<Board> findByWriterIgnoreCaseContainingOrderByNoDesc(String writer);

    List<Board> findByTitleIgnoreCaseContainingOrderByNoDesc(String title, Pageable pageable);

    List<Board> findByContentIgnoreCaseContainingOrderByNoDesc(String content, Pageable pageable);

    List<Board> findByWriterIgnoreCaseContainingOrderByNoDesc(String writer, Pageable pageable);

    long countByTitleIgnoreCaseContainingOrderByNoDesc(String title);

    long countByContentIgnoreCaseContainingOrderByNoDesc(String content);

    long countByWriterIgnoreCaseContainingOrderByNoDesc(String writer);

}
