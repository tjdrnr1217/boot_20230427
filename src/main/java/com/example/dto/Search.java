package com.example.dto;

import lombok.Data;

@Data
public class Search {
    final String[] typeCode ={"phone", "name", "address", "type"};
    final String[] typeName ={"연락처", "상호명", "주소", "종류"};

    private int page=1 ;
    private String text;
    private String type="phone";
}
