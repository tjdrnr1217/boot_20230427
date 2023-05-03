package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.Board;
import com.example.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService{
    @Autowired BoardMapper boardMapper; //
    @Override
    public int insertBoardOne(Board obj) {
        try {
        return boardMapper.insertBoardOne(obj);            
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'insertBoardOne'");
        }
    }
    @Override
    public List<Board> selectBoardList() {
        try {
            return boardMapper.selectBoardList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Board SelectBoardOne(long no) {
       try {
        return boardMapper.selectBoardOne(no);
       } catch (Exception e) {
        e.printStackTrace();
        return null;
    }

    }
    @Override
    public int UpdateBoard(Board obj) {
        try {
            return boardMapper.updateBoard(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    @Override
    public int DeleteBoard(long no) {
        try {
            return boardMapper.deleteBoard(no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
  
}
