package com.example.boot_20230427;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

// 새로운 패키지를 생성하기 역할을 부여하면 반드시 실행파일에 등록해야함
// classpath ==> resources와 동일함.
@SpringBootApplication
@PropertySource(value = {"classpath:global.properties"}) // 직접만든 환경설정 파일 위치
@MapperScan(basePackages = {"com.example.boot_20230427.mapper"}) // 맵퍼 위치 설정
@ComponentScan(basePackages = {"com.example.boot_20230427.controller","com.example.boot_20230427.service","com.example.boot_20230427.config"}) // 컨트롤러, 서비스 위치, 시큐리티 환경설정 
public class Boot20230427Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot20230427Application.class, args);         
	}
}
