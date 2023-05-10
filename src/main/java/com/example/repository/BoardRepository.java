package com.example.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, BigDecimal> {

    // select * from board order by no desc
    List<Board> findAllByOrderByNoDesc(); 

    // select * from board where title like '%' || ? || '%' order by no desc
    List<Board> findByTitleIgnoreCaseContainingOrderByNoDesc( String title );
    List<Board> findByContentIgnoreCaseContainingOrderByNoDesc( String content );
    List<Board> findByWriterIgnoreCaseContainingOrderByNoDesc( String writer );

    List<Board> findByTitleIgnoreCaseContainingOrderByNoDesc( String title, Pageable pageable );
    List<Board> findByContentIgnoreCaseContainingOrderByNoDesc( String content, Pageable pageable );
    List<Board> findByWriterIgnoreCaseContainingOrderByNoDesc( String writer, Pageable pageable );

    long countByTitleIgnoreCaseContainingOrderByNoDesc( String title );
    long countByContentIgnoreCaseContainingOrderByNoDesc( String content );
    long countByWriterIgnoreCaseContainingOrderByNoDesc( String writer );

}