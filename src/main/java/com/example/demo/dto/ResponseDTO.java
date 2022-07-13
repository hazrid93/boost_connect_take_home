package com.example.demo.dto;

import com.example.demo.model.Comment;

import java.util.List;

public class ResponseDTO extends BaseDto {

    public List<Comment> data;

    public List<Comment> getData() {
        return data;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

}
