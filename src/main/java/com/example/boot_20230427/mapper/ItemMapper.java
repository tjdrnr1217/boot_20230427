package com.example.boot_20230427.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.boot_20230427.dto.Item;
import com.example.boot_20230427.dto.ItemImage;

@Mapper
public interface ItemMapper {
    // 물품 판매자 조회
    public List<Item> selectItemListID(String seller);

    // 물품 전체 조회
    public List<Item> selectItemList();

    // 물품 이미지 추가
    public int insertItemImageOne(ItemImage itemimage);

    // 물품 이미지 정보 조회 (이미지 번호가 전송되면 1개의 이미지 정보 반환) 
    public ItemImage selectItemImageOne(long no);

    // 물품 번호를 전송하면 해당하는 이미지 번호n개를 반환
    public List<Long> selectItemImageNo(long itemno);

    // 일괄 추가
    public int insertItemBatch(List<Item> list );

    // 일괄 삭제
    public int deleteItemBatch(long[] no);
    
    // 물품 번호에 해당하는 항목 반환
    public List<Item> selectItemNoList(long[] no);

    // 물품 일괄 수정
    public int updateItemBatch(List<Item> list);
}
