package com.example.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

@Data
@Entity
@Table(name = "CHECKOUT2")
@SequenceGenerator(name = "SEQ_CHECKOUT2_NO", sequenceName = "SEQ_CHECKOUT2_NO", initialValue = 1, allocationSize=1)

public class Checkout2 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHECKOUT2_NO")
    
    private BigInteger ccode;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
    @CreationTimestamp //변경시 날짜정보 변경
    @Column(updatable = false)
    private Date regdate;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cbcode", referencedColumnName = "bcode")
    private Book2 bkcode;


    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="semail", referencedColumnName = "semail")
    private Student2 stdemail;

    
}
