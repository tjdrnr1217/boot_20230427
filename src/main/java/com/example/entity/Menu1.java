package com.example.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Entity
@Data
@SequenceGenerator(name = "SEQ_MENU1_NO", sequenceName = "SEQ_MENU1_NO", initialValue = 1, allocationSize = 1)
@Table(name = "MENU1")
public class Menu1 {

      // @Id
      @Id
      @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MENU1_NO")
      private BigInteger no;
  
      private String name;
      
      private BigInteger price;
      
      private String imagename;
      //이미지타입
      private String imagetype;
      //이미지사이즈
      private BigInteger imagesize;
      //이미지데이터
      @Lob
      @ToString.Exclude
      private byte[] imagedata;
  
      @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
      @CreationTimestamp //변경시 날짜정보 변경
      @Column(updatable = false)
      private Date regdate;


      @ManyToOne
    @JoinColumn(name="rphone", referencedColumnName="phone")
    private Restaurant1 restaurant1;
    
}
