package com.example.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@SequenceGenerator(name = "SEQ_BOARD_NO", sequenceName = "SEQ_BOARD_NO", initialValue = 1, allocationSize = 1)
public class Board {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BOARD_NO")
	@Column(name = "NO") 
	private BigDecimal no;

	private String title;
	
	@Lob
	private String content;
	
	private String writer;
	
	private BigDecimal hit;
	
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @CreationTimestamp
    private Date regdate;
}
