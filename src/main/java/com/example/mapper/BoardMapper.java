package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.dto.Board;

@Mapper
public interface BoardMapper {
    
    @Insert({
		"  INSERT INTO board(title, content, writer) VALUES ( #{obj.title},#{obj.content},#{obj.writer})  "
	})
	public int insertBoardOne(@Param("obj") Board obj);


    @Select({
		"  SELECT b.* FROM board b ORDER BY b.no DESC  "
	})
	public List<Board> selectBoardList();

    @Select({
		"  SELECT b.* FROM board b WHERE NO=#{no}  "
	})
	public Board selectBoardOne(@Param("no") long no);

    @Update({
      " UPDATE board SET hit=hit+1 WHERE no=#{no}"
    })
  public int updatehit(Board board);

   

     public int deleteBoard(long no);
     
    //  @Update({
    //     " UPDATE board SET title=#{obj.title}, content = #{content} WHERE no = #{obj.no} "
    //  })
    //sql문이 없고 xml 로 resources/mappers/파일명Mapper.xml
     public int updateBoard(Board obj);
}
