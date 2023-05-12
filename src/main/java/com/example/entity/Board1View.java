package com.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import lombok.Data;
// DB에서 뷰 생성 
// CREATE VIEW BOARD1_VIEW AS SELECT NO, TITLE FROM BOARD1;
 
@Data
@Immutable // 뷰일경우 추가 -> 조회만 가능한 엔티티
@Entity
@Table(name = "BOARD1_VIEW")
public class Board1View {

    @Id
    @Column(name = "NO")
    private Long no = 0L;

    @Column(name = "TITLE")
    private String title = null;

}
