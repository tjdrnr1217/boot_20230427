package com.example.dto;
//2023.04.028
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"regdate"})
@NoArgsConstructor
@AllArgsConstructor
public class Item {
	private long no; //
	private String name;
	private String content;
	private long price;
	private long quantity; //
	private Date regdate;

    private long imageNo; // 이미지 번호를 저장할 임시변수
	

}
