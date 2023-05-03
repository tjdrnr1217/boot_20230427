package com.example.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.Item;
import com.example.dto.ItemImage;

@Service
public interface ItemService {
    

//컨트롤러에서 실행하는 클래스
    public List<Item> itemSelectList();


    //
    public int insertitemimage(ItemImage obj);
}
