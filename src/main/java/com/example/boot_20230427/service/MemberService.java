package com.example.boot_20230427.service;

import org.springframework.stereotype.Service;

import com.example.boot_20230427.dto.Member;

@Service
public interface MemberService {
    public int memberJoin(Member member);
    public Member selectMemberOne(Member member);
    public int updateMemberOne(Member member);
    public int deleteMemberOne(Member member);
}
