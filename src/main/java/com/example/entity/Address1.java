package com.example.entity;

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

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
@Entity
@Table(name = "ADDRESS1")
@SequenceGenerator(name = "SEQ_A1", sequenceName = "SEQ_ADDRESS1_NO", initialValue = 1, allocationSize=1)
public class Address1 {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_A1")
    @Column(name = "NO")
    private long no;

    @Column(name = "POSTCODE", length = 10)
    private String postcode;

    private String address;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
    @UpdateTimestamp //변경시 날짜정보 변경
    private Date regdate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMID", referencedColumnName = "ID")
    private Member1 member1;
}
