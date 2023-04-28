package com.example.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.dto.Board;

@Service
public interface BoardService {
    //게시판등록
    public int insertBoardOne(@Param("obj") Board obj);

    //게시글전체조회
    public List<Board> selectBoardList();

    // 게시글조회
    public Board selectBoardOne(@Param("no") long no);

    // 게시글 수정
    public int updateBoardOne(Board obj);

    //게시글 삭제
    public int deleteBoardOne(long no);
}
