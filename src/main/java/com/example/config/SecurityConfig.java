package com.example.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.handler.CustomerLogoutSucessHandler;
import com.example.handler.LoginSuccessHandler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration//환경설정파일 서버가 구동되기전에 호출됨
@EnableWebSecurity//시큐리티를 사용
@Slf4j
@RequiredArgsConstructor
public class SecurityConfig {
    final UserDetailsService userDetailsService;
    @Bean   // 객체를 생성함. (자동으로 호출됨.)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info("SecurityConfig => {}", "start filter chain");

        // 권한 설정
        http.authorizeRequests()
        .antMatchers("/customer/join.do").permitAll()
        .antMatchers("/seller/join.do").permitAll()
        .antMatchers("/admin/join.do").permitAll()
        .antMatchers("/admin", "/admin/*").hasAuthority("ROLE_ADMIN") //주소가 9090/ROOT/admin ~~
        .antMatchers("/seller", "/seller/*").hasAnyAuthority("ROLE_ADMIN", "ROLE_SELLER")
        .antMatchers("/customer", "/customer/*").hasAnyAuthority("RoleCUSTOMER")
        .anyRequest().permitAll();

        //403페이지설정
        http.exceptionHandling().accessDeniedPage("/403page.do");
        
        // 로그인 처리
        http.formLogin()
            .loginPage("/login.do")  //
            .loginProcessingUrl("/loginaction.do") //action은 
            .usernameParameter("id") 
            .passwordParameter("password")
         //   .defaultSuccessUrl("/home.do") // 로그인 성공시 이동할 페이지
            .successHandler(new LoginSuccessHandler())
            .permitAll();



        // 로그아웃 처리(반드시post로 호출)
        http.logout()
        .logoutUrl("/logout.do")
        .logoutSuccessHandler( new CustomerLogoutSucessHandler())
        //.logoutSuccessUrl("/home.do")
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .permitAll();



        //post는 csrf를 전송해야하지만, 주소가 /api csrf가 없어도 허용하도록 설정
        http.csrf().ignoringAntMatchers("/api/**");



        //서비스 등록
        http.userDetailsService(userDetailsService); 

        return http.build();
    }


    //정적 자원에 스프링 시큐리티 필터 규칙을 적용하지 않도록 설정, resources/static 은 시큐리티 적용받지 않음
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
    //회원가입에서 사용했던 암호화 알고리즘 설정, 로그인에서도 같은것을 사용해야 하니까
    @Bean // 서버구동시자동으로 실행
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
