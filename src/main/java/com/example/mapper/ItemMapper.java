package com.example.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.dto.Item;
import com.example.dto.ItemImage;

@Mapper
public interface ItemMapper {

public int updateItemBatch(List<Item> list);

public List<Item> selectItemNoList(long[] no);


public List<Item> selectItemseller(Item item);

public int insertItemBatch(List<Item> list);
public int deleteItemBatch(long[] no);
@Insert({
	"  INSERT INTO item(no, name, content, price, quantity)  ",
	"  VALUES(SEQ_ITEM_NO.NEXTVAL, #{item.name},#{item.content},#{item.price}, #{item.quantity})  "
})
public int itemInsertOne(@Param("item") Item item);

//물품전체 조회
public List<Item> itemSelectList();

@Select({
	"  SELECT * FROM item   WHERE ${column} LIKE '%'||#{text}||'%' ORDER BY no DESC    "
	//#{}=> 값을 표현할때
	//${}=> 컬럼명, 테이블명 등.
})
public List<Item> itemSelectListsear(@Param("column") String column, @Param("text") String text);

@Select({
	"  SELECT i.* FROM item i WHERE i.no= #{itemno} "
})
public Item itemSelectOne(@Param("itemno") long itemno);


public int ItemImageInsertone(ItemImage obj);
//이미지 번호가 전송되면 1개의 이미지 정보 반환
public ItemImage selectItemImageOne(long no);

//물품 번호를 전송하면 해당하는 이미지번호를 반환
public List<Long> selectItemImageNo(long itemno);

}
