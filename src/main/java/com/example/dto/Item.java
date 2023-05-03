package com.example.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Item {
  

        private long no;
        private String name;
        private String content;
        private long price;
        private long quantity;
        private Date regdate;
        private String seller;// 판매자아이디
        
        private long imageNo; // 대표 이미지 번호를 저장할 임시변수
  
      
}
