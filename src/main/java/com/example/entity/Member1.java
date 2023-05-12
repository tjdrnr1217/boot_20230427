package com.example.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
@Table(name = "MEMBER1")//생성하고자하는 테이블, 또는 생성되어 있는 테이블 매칭
public class Member1 {
    @Id  //import javax.persistence.Id; 기본키설정
    @Column(name = "ID",  length= 50)
    private String id;  //@Column 을 생략하면 변수명이 컬럼명

    private String pw;

    private String name;

    private int age;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
    @CreationTimestamp
    private Date regdate;

    @ToString.Exclude
    @OneToOne(mappedBy = "member1", cascade=CascadeType.ALL,fetch = FetchType.LAZY)
    private Memberinfo1 memberinfo1;


    //Eager member1조회시 address1을 조인하여 보여줌
    //lazy member1조회시 address1을 조인하지 않고 address1을 필요할때 조인함
    //cascade =>member1의 회원을 지우면 자도으로 address1dm1의 관련 주소도 삭제함.
    // @ToString.Exclude// stackoverflow
    // @OneToMany(mappedBy = "member1", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE )
    // @OrderBy(value = "no desc")
    // List<Address1> list = new ArrayList<>();
}
