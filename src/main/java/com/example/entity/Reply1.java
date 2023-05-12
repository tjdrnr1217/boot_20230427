package com.example.entity;

import java.util.Date;

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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "REPLY1")//생성하고자하는 테이블, 또는 생성되어 있는 테이블 매칭
@SequenceGenerator(name="SEQ_R1", sequenceName = "SEQ_REPLY1_NO", initialValue = 1, allocationSize = 1)
public class Reply1 {
    @Id  //import javax.persistence.Id; 기본키설정
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_R1")
    private long no;  //@Column 을 생략하면 변수명이 컬럼명

    @Lob
    private String content;

    private String writer;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
    @CreationTimestamp
    private Date regdate;
    
    //답글과 게시글의 관계
    @ManyToOne
    @JoinColumn(name="BRDNO", referencedColumnName="NO")
    private Board1 board1;
}
