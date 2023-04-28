package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


import com.example.dto.Board;

@Mapper
public interface BoardMapper {

    // sql문이 없음 => resources/mappers/파일명Mapper.xml
	public int updateBoardOne(Board obj);

    // sql문이 없음 => resources/mappers/파일명Mapper.xml
    public int deleteBoardOne(long no);
    
    // 글쓰기
    @Insert({
        " INSERT INTO board(title, content, writer) ",
		" VALUES(#{obj.title},#{obj.content},#{obj.writer}) "
    })
    public int insertBoardOne(@Param("obj") Board obj);

    // 전체조회
    @Select({
        " SELECT b.* FROM board b ORDER BY no DESC "
    })
    public List<Board> selectBoardList();

    // 게시글조회
    @Select({
		" Select b.* FROM board b WHERE NO=#{no}"	
	})
    public Board selectBoardOne(@Param("no") long no);

   

}
