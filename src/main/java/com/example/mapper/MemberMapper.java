package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.dto.Member;

@Mapper
public interface MemberMapper {
    // 회원가입
    public int insertMemberOne(Member member);

    // 로그인
    public Member selectMemberOne(Member member);

    // 정보수정
    public int updateMember(Member member);

    //회원탈퇴
    public int deleteMember(Member id);

    //
    public Member updateMemberOne(Member member);
}
