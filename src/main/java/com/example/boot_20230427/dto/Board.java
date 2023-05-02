package com.example.boot_20230427.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= {"regdate"}) // Tostring으로 출력할때 regdate는 출력하지 않겠다 제외함
@NoArgsConstructor
@AllArgsConstructor
public class Board {
    
    private long no;
	private String title;
	private String content;
	private String writer;
	private long hit;
	private Date regdate;
    
}
