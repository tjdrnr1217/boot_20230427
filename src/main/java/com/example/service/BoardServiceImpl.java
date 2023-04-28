package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dto.Board;
import com.example.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardMapper bMapper;
    // Autowired는 interface에서 사용할 수가 없다

    // 게시글 추가
    @Override
    public int insertBoardOne(Board obj) {
        try{
            return bMapper.insertBoardOne(obj);
        }
        catch(Exception e){       
            throw new UnsupportedOperationException("Unimplemented method 'insertBoardOne'");
        }
    }

    // 게시글 전체 조회
    @Override
    public List<Board> selectBoardList() {
        try{
            return bMapper.selectBoardList();
        }
        catch(Exception e){
            throw new UnsupportedOperationException("Unimplemented method 'selectBoard'");
        }
        
    }

    @Override
    public Board selectBoardOne(long no) {
        try {
            return bMapper.selectBoardOne(no);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'selectBoardOne'"); 
        }
        
    }

    @Override
    public int updateBoardOne(Board obj) {
        try {
            return bMapper.updateBoardOne(obj);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'updateBoardOne'");
        }
        
    }

    @Override
    public int deleteBoardOne(long no) {
        try {
            return bMapper.deleteBoardOne(no);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Unimplemented method 'deleteBoardOne'");
        }
        
    }

}
