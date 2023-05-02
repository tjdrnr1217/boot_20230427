package com.example.boot_20230427.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.boot_20230427.dto.Item;
import com.example.boot_20230427.dto.ItemImage;
import com.example.boot_20230427.mapper.ItemMapper;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemMapper iMapper;

    @Override
    public List<Item> selectItemList() {
        try {
            return iMapper.selectItemList();
        } catch (Exception e) {
            return null;            
        }
    }

    @Override
    public int insertItemImageOne(ItemImage itemimage) {
        try {
            return iMapper.insertItemImageOne(itemimage);
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public ItemImage selectItemImageOne(long no) {
        try {
            return iMapper.selectItemImageOne(no);            
        } catch (Exception e) {
            return null;
        }
        
    }

    @Override
    public List<Long> selectItemImageNo(long itemno) {
        try {
            return iMapper.selectItemImageNo(itemno);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int insertItemBatch(List<Item> list) {
        try {
            return iMapper.insertItemBatch(list);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Item> selectItemListID(String seller) {
        try {
            return iMapper.selectItemListID(seller);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteItemBatch(long[] no) {
        try {
            return iMapper.deleteItemBatch(no);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Item> selectItemNoList(long[] no) {
        try {
            return iMapper.selectItemNoList(no);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int updateItemBatch(List<Item> list) {
        try {
            return iMapper.updateItemBatch(list);
        } catch (Exception e) {
            return 0;
        }
    }
    
}
