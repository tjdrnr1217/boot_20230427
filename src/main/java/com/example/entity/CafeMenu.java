package com.example.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
// 2023.06.04
@Entity
@Data
@SequenceGenerator(name = "SEQ_CAFEMENU_NO", sequenceName = "SEQ_CAFEMENU_NO", initialValue = 1, allocationSize = 1)
@Table(name = "CAFEMENU")
public class CafeMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="SEQ_CAFEMENU_NO")
    private BigInteger no;
  
      private String name;
      
      private BigInteger price;
      
      @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
      @CreationTimestamp //변경시 날짜정보 변경
      @Column(updatable = false)
      private Date regdate;


      @ManyToOne
    @JoinColumn(name="cphone", referencedColumnName="phone")
    private Cafe cafe; 
}
