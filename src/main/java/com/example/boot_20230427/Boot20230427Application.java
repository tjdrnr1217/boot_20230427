package com.example.boot_20230427;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//새로운 패키지를 생성하기 역할을 부여하면 반드시 실행파일에 등록해야함.
//classpath==> resource와 동일함.
@SpringBootApplication
@PropertySource(value = {"classpath:global.properties"})//직접만든 환경설정파일위치
@MapperScan(basePackages = {"com.example.mapper"})
@ComponentScan(basePackages = {"com.example.controller","com.example.service","com.example.config", "com.example.restcontroller","com.example.filter"})

@EntityScan(basePackages ={"com.example.entity"})// 엔티티 위치
@EnableJpaRepositories(basePackages = {"com.example.repository"})//저장소 위치

public class Boot20230427Application {

	public static void main(String[] args) {
		SpringApplication.run(Boot20230427Application.class, args);
	}

}
