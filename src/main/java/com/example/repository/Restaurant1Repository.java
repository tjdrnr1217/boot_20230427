package com.example.repository;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Restaurant1;

@Repository
public interface Restaurant1Repository extends  JpaRepository <Restaurant1, String>{
    

    
    
    @Query(value = "SELECT * FROM ( SELECT m1.*, ROW_NUMBER() OVER (ORDER BY name DESC) rown FROM RESTAURANT1 m1 WHERE m1.name LIKE '%' || :name || '%' ) WHERE rown BETWEEN :start AND :end", nativeQuery = true)
    public List<Restaurant1> selectByNameContainingPagenation(@Param("name") String name, @Param("start") int start,
    @Param("end") int end);
    
    
    List<Restaurant1> findByPhoneContainingOrderByNoDesc(String phone, Pageable Pageable);
    long countByPhoneContaining(String Phone);

    List<Restaurant1> findByNameContainingOrderByNoDesc(String name, Pageable Pageable);
    long countByNameContaining(String name);

    List<Restaurant1> findByTypeContainingOrderByNoDesc(String type, Pageable Pageable);
    long countByType(String type);

    List<Restaurant1> findByAddressContainingOrderByNoDesc(String address, Pageable Pageable);
    long countByAddressContaining(String address);
    
}
