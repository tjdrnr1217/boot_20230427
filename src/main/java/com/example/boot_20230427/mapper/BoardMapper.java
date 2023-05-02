package com.example.boot_20230427.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.boot_20230427.dto.Board;

@Mapper
public interface BoardMapper {
    
    // 글쓰기
    @Insert({
		" INSERT INTO board(title,content,writer) ", 
		" VALUES(#{obj.title},#{obj.content},#{obj.writer}) "
	})
	public int insertBoardOne(@Param("obj") Board board);

    // 전체조회
    @Select({
        " SELECT * FROM board ORDER BY no DESC "
    })
    public List<Board> selectBoardList();

    // 글 하나 조회
    @Select({
        " SELECT * FROM board WHERE no = #{no} "
    })
    public Board selectBoardOne(@Param("no") long no);

    // sql문이 없음 => resources/mappers/파일명Mapper.xml
    public int updateBoardOne(Board obj);

    public int deleteBoardOne(long no);
}
