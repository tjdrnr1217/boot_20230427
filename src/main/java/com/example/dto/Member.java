package com.example.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude= {"password"})
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	private String id;
	public String password;
	public String newpassword;
	public String name;
	public String role;
	public long age;
	private Date regdate;

	
	
	
}
