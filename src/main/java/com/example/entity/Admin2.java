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
@Table(name = "ADMIN2")
@Entity
@SequenceGenerator(name = "SEQ_ADMIN2_NO", sequenceName = "SEQ_ADMIN2_NO", initialValue = 1, allocationSize=1)

public class Admin2 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ADMIN2_NO")
    private BigInteger acode;

    private String aname;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
    @CreationTimestamp //변경시 날짜정보 변경
    @Column(updatable = false)
    private Date regdate;

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name="lcode", referencedColumnName="lcode")
    private Library2 library2;
    
    
}
