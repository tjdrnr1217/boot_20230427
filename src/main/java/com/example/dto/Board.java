package com.example.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="regdate")
public class Board {
    private long no;
    private String title;
    private String content;
    private String writer;
    private long hit;
    private Date regdate;
}
