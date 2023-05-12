package com.example.entity;

import java.math.BigInteger;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Table(name = "BOOK2")
@Entity
@SequenceGenerator(name = "SEQ_BOOK2_NO", sequenceName = "SEQ_BOOK2_NO", initialValue = 1, allocationSize=1)

public class Book2 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOOK2_NO")
    private BigInteger bcode;
    
    private String btitle;

    private String bwriter;

    private BigInteger bprice;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
    @CreationTimestamp //변경시 날짜정보 변경
    @Column(updatable = false)
    private Date regdate;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name="lcode", referencedColumnName="lcode")
    private Library2 libcode;


    @ToString.Exclude
    @OneToMany(mappedBy = "bkcode", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Checkout2> checkout2 = new ArrayList<>();


    

    
}
