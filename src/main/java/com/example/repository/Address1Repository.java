package com.example.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Address1;

@Repository
public interface Address1Repository extends JpaRepository<Address1, Long>{
//제너릭을 이용한 타입설정
<T>List<T> findALlByOrderByNoDesc(Class<T> type);




//     //select a.no, a.address, m.id, m.name from address1 a,member1 m order by desc
// List<Address1Projection> findAllByOrderByNoDesc();



//select .... WHERE address=?    
List<Address1> findByAddress(String address);
//select .... WHERE postcode=?    
List<Address1> findByPostcode(String postcode);
//select .... WHERE postcode=? AND address=?   
List<Address1> findByAddressAndPostcode(String address, String postcode);
//member1은 객체이기 때문에 _를 이용해서 id값
List<Address1> findByMember1_idOrderByNoDesc(String id, Pageable pageable);
//select count(*) from address1 where member1.id=?
long countByMember1_id(String id);
}
