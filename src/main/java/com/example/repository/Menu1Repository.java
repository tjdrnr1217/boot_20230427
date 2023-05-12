package com.example.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Menu1;
import com.example.entity.Menu1ImageProjection;
@Repository
public interface Menu1Repository extends JpaRepository<Menu1, BigInteger> {
    //findBy변수명_하위변수
    public List<Menu1> findByRestaurant1_phone(String rphone);

    // 메뉴 번호가 전달되면 이미지 정보(3개 봔환)
    Menu1ImageProjection findByNo(BigInteger no);
    
}
