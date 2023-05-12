package com.example.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "MEMBERINFO1")
public class Memberinfo1 {

    // 기본키의 역할
    // 기본키이면서 외래키
    @Id
    @Column(name ="ID1")
    private String id1; 

    // 외래키의 역할
    @MapsId //컬럴을 줄이고 ID1컬럼 하나만 생성(외래키 제약)
    @OneToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "ID1")
    private Member1 member1;

    private String info;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
    @CreationTimestamp //변경시 날짜정보 변경
    private Date regdate;
    
}
