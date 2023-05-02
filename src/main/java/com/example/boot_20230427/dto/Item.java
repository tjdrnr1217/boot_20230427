package com.example.boot_20230427.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= {"regdate"})
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    
    private long no;
	private String iname;
	private String icontent; // clob
	private long iprice;
	private long iquantity;
	private Date regdate;
	private String seller;
	private long imageNo; // 대표이미지 번호를 저장할 임시 변수
    
}
