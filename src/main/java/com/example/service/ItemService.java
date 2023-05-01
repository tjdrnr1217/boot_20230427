package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Item;
import com.example.dto.ItemImage;

// 컨트롤러에서 실행하는 클래스
@Service
public interface ItemService {
    
    // 물품전체조회
    public List<Item> selectItemList();

    // 이미지 등록
    public int insertItemImageOne(ItemImage obj);

    //  // 이미지번호가 전송되면 1개의 이미지 정보 변환
    //  public ItemImage selectItemImageOne(long no);

    //  // 물품번호를 전송하면 해당하는 이미지번호 n개를 반환
    //  public List<Long> selectItemImageNo(long itemno);
    
}