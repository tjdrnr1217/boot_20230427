package com.example.boot_20230427.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boot_20230427.dto.Member;
import com.example.boot_20230427.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired MemberMapper mMapper;

    @Override
    public int memberJoin(Member member) {
        try {
            return mMapper.memberJoin(member);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Member selectMemberOne(Member member) {
        try {
            return mMapper.selectMemberOne(member);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int updateMemberOne(Member member) {
        try {
            return mMapper.updateMemberOne(member);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int deleteMemberOne(Member member) {
        try {
            return mMapper.deleteMemberOne(member);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
}
