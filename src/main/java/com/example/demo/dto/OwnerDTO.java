package com.example.demo.dto;

import com.example.demo.model.Owner;

import java.util.List;

public class OwnerDTO extends BaseDto {
    
    public List<Owner> data;

    public List<Owner> getData() {
        return data;
    }

    public void setData(List<Owner> data) {
        this.data = data;
    }


}
