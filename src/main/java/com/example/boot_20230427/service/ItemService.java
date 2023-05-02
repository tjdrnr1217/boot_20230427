package com.example.boot_20230427.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.boot_20230427.dto.Item;
import com.example.boot_20230427.dto.ItemImage;

@Service
public interface ItemService {

    // 물품 전체 조회
    public List<Item> selectItemList();
    // 물품 이미지 추가
    public int insertItemImageOne(ItemImage itemimage);
    // 물품 이미지 정보 조회
    public ItemImage selectItemImageOne(long no);
    // 물품 이미지 번호 반환
    public List<Long> selectItemImageNo(long itemno);
    // 물품 일괄 추가
    public int insertItemBatch(List<Item> list);
    // 물품 아이디별 목록
    public List<Item> selectItemListID(String seller);
    // 물품 일괄 삭제
    public int deleteItemBatch(long[] no);
    // 물품 번호에 해당하는 항목 반환
    public List<Item> selectItemNoList(long[] no);
    // 물품 일괄 수정
    public int updateItemBatch(List<Item> list);
}
