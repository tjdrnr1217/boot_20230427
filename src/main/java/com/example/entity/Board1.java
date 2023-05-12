package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

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
@Table(name = "BOARD1")//생성하고자하는 테이블, 또는 생성되어 있는 테이블 매칭
@SequenceGenerator(name="SEQ_B1", sequenceName = "SEQ_BOARD1_NO", initialValue = 1, allocationSize = 1)
public class Board1 {
    @Id  //import javax.persistence.Id; 기본키설정
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_B1")
    private long no;  //@Column 을 생략하면 변수명이 컬럼명


    private String title;
    //글 내용 => 타입이 clob
    @Lob
    private String content;

    private String writer;

    @Column(columnDefinition="long default 1")
    private long hit=1;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
    @CreationTimestamp
    private Date regdate;

    @ToString.Exclude
    @OneToMany(mappedBy = "board1")
    List<Reply1> list = new ArrayList<>();

     @ToString.Exclude// stackoverflow
    @OneToMany(mappedBy = "board1", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE )
    @OrderBy(value = "no desc")
    List<BoardImage1> list1 = new ArrayList<>();

    @Transient//임시변수 == 컬럼이 생성되지않는다. mybatis dto개념
    private String imageUrl;
 }
