package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "BOARD1")
@SequenceGenerator(name = "SEQ_B1", sequenceName = "SEQ_BOARD1_NO", initialValue = 1, allocationSize = 1)
public class Board1 {
    
    // 글번호 => 기본키이고 시퀀스를 사용함.
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_B1")
    private long no;

    private String title;  //글제목

    // 글내용 => 타입이 clob
    @Lob
    private String content;

    private String writer;  //글제목

    @Column(columnDefinition = "long default 1")
    private long hit=1;
   
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;

    @ToString.Exclude //스택오버플로우를 막기 위해서 쓴다 
    @OneToMany(mappedBy = "board1")
    List<Reply1> list=  new ArrayList<>();
}
