// 2023.06.04
package com.example.repository.Cafe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Cafe;

@Repository
public interface CafeRepository extends JpaRepository<Cafe,String> {
    
}
