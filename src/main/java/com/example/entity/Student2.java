package com.example.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.ToString;

@Data
@Table(name = "STUDENT2")
@Entity
public class Student2 {
    @Id
    private String semail;

    private String sname;

    private String sphone;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:ss.SSS")
    @CreationTimestamp //변경시 날짜정보 변경
    @Column(updatable = false)
    private Date regdate;

    @ToString.Exclude
    @OneToMany(mappedBy = "stdemail", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
    private List<Checkout2> checkout2 = new ArrayList<>();
}
