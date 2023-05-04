package com.example.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerUser  extends User{
    private String id; //username
	public String password; //password
    private Collection<GrantedAuthority> authorities; //role
	public String name;
	public int age;

    // User의 생성자 기본구조
    public CustomerUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
       
    }

    // 기본구조에서 추가할 내용포함(이름, 나이)
    public CustomerUser(String username, String password, Collection<GrantedAuthority> authorities, String name, int age){
        super(username, password,authorities);
        this.authorities=authorities;
        this.id = username;
        this.password = password;
        this.name = name;
        this.age = age;
    }
	
    
}
