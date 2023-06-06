package com.example.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
// import javax.persistence.IdClass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

// 2023.06.04
@Entity
@Data
@SequenceGenerator(name = "SEQ_CAFE11_NO", sequenceName = "SEQ_CAFE11_NO", initialValue = 1, allocationSize = 1)
@Table(name = "CAFE")
public class Cafe {

    @Generated(GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAFE11_NO")
    private BigInteger no;

    @Id
    private String phone;

    private String name;

    private String address;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
    @CreationTimestamp //변경시 날짜정보 변경
    @Column(updatable = false)
    private Date regdate;
    

    // cascade => 외래키가 존재해도 강제로 항목을 지움.
    // ex) 메뉴가 있어도 삭제가 됨
    @ToString.Exclude
    @OneToMany(mappedBy = "cafe", cascade = CascadeType.REMOVE)
    List<CafeMenu> cafemenulist = new ArrayList<>();
}
