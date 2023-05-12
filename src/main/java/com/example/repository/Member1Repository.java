package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Member1;
import com.example.entity.Member1Projection;

//엔티티, 엔티티의 기본키 타입
@Repository
public interface Member1Repository extends JpaRepository<Member1, String> {
    //select id, name, age from member1 order by id asc
    public List<Member1Projection> findAllByOrderByIdAsc();

    // JPQL select m.* from Member1 order by m.name desc
    public List<Member1> findAllByOrderByNameDesc();

    public long countByNameContaining(String name);

    // JPQL select m.* from Member1 WHERE m.name like '%'?'%'order by m.name desc
    public List<Member1> findByNameContainingOrderByNameDesc(String name);

    // JPQL select m.* from Member1 WHERE m.name like '%'?'%'order by m.name desc
    // limit 페이지네이션
    public List<Member1> findByNameContainingOrderByNameDesc(String name, Pageable pageable);

    // nativequery 사용
    @Query(value = "SELECT * FROM ( SELECT m1.*, ROW_NUMBER() OVER (ORDER BY name DESC) rown FROM MEMBER1 m1 WHERE m1.name LIKE '%' || :name || '%' ) WHERE rown BETWEEN :start AND :end", nativeQuery = true)
    public List<Member1> selectByNameContainingPagenation(@Param("name") String name, @Param("start") int start,
            @Param("end") int end);

}
