package com.crud.task.domain;

import lombok.Data;

import java.util.List;

@Data
public class TrelloBoardDto {

    private String name;
    private String id;
    private List<TrelloListDto> lists;
}