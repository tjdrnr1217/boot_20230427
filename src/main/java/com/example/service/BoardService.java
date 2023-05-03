package com.example.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.dto.Board;

@Service
public interface BoardService {
    //게시글등록
    public int insertBoardOne(@Param("obj") Board obj);
//게시글전체 조회
public List<Board> selectBoardList();

 public Board SelectBoardOne(@Param("no") long no);

 public int UpdateBoard(@Param("obj") Board obj);


 public int DeleteBoard(@Param("no") long no);
}
