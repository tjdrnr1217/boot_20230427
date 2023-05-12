package com.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.dto.Member;

@Mapper
public interface MemberMapper {
	
	public int insertMemberOne(Member obj);
	
    public Member selectMemberone(Member obj);
	public Member selectMemberone1(String userid);
    public int updateMember(Member obj);

    public int deleteMember(String id);
	public Member loginMember(Member obj);

	@Select({
		"  SELECT m.id, m.name,m.role,m.age, FROM member m WHERE ID=#{obj.id} AND PASSWORD=#{obj.password}   "
	})
	public Member selectMemberLogin(@Param("obj") Member obj);
	


	
	public int updatepwMember(Member obj);
	
	
	@Select({
		"  SELECT COUNT(*) cnt FROM member WHERE ID=#{id}  "
	})
	public int selectMemberIDcheck(@Param("id" )String id);
	
	@Select({
		"  SELECT COUNT(*) cnt FROM member WHERE ID=#{obj.id} AND password=#{obj.password}  "
	})
	public int loginmember(@Param("obj") Member obj);
	
	@Select({
		"  SELECT role FROM member WHERE ID=#{obj.id} AND password=#{obj.password}  "
	})
	public Member selectrole(@Param("obj") Member obj);
	
}