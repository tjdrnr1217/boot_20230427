package com.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.Member;
import com.example.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
@RequiredArgsConstructor
public class SecurityServiceImpl implements UserDetailsService{
final String format ="SecurityServiceImpl => {}";
final MemberMapper memberMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(format, username);
        //아이디를 전달해서 정보를 받아옴 암호까지 받아옴
        //DB연동
        Member member = memberMapper.selectMemberone1(username);
        if(member != null){//가져올 정보가 있다. 
            //Member dto를 사용해서 처리하나 시큐리티는 user dto를 사용함.
            return  User.builder()
            .username(member.getId())
            .password(member.getPassword())
            .roles(member.getRole())
            .build();
        }
            return  User.builder()
            .username("_")
            .password("_")
            .roles("_")
            .build();
    }
}
