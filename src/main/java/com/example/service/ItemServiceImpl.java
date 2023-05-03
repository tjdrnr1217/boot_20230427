package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Item;
import com.example.dto.ItemImage;
import com.example.mapper.ItemMapper;

import lombok.RequiredArgsConstructor;
//설계된 부분을 구현하는 클래스 매퍼를 호출함.
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    final ItemMapper itemmapper; //매퍼 객체 생성또는 @Autowired ItemMapper iMapper;
    @Override
    public List<Item> itemSelectList() {
        try {
            return  itemmapper.itemSelectList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public int insertitemimage(ItemImage obj) {
        try {
           return itemmapper.ItemImageInsertone(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }
    
}
