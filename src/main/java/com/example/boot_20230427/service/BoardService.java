package com.example.boot_20230427.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.example.boot_20230427.dto.Board;

@Service
public interface BoardService {
    
    // 게시글 등록
    public int insertBoardOne(@Param("obj") Board obj);

    // 게시글 전체 확인
    public List<Board> selectBoardList();

    // 게시글 하나 확인
    public Board selectBoardOne(@Param("no") long no);

    // 게시글 하나 업데이트
    public int updateBoardOne(@Param("obj") Board board);

    // 게시글 하나 삭제
    public int deleteBoardOne(@Param("no") long no);
}
