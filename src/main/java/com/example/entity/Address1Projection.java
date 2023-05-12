package com.example.entity;

public interface Address1Projection {
    
    long getNo(); //주소번호

    String getAddress(); //주소

    Member1 getMember1(); //회원정보

    interface Member1 {//외래키 항목
        String getId(); //아이디

        String getName(); //이름
    }
    //조합(주소번호 + 주소정보 합치기)
    default String getNoAddress(){
        return getNo()+","+getAddress();
    }
}
