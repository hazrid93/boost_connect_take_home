package com.example.demo.service;

import com.example.demo.model.BaseModel;
import com.example.demo.model.Owner;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseService {

    void saveAll(List<? extends BaseModel> objects);

    List<? extends BaseModel> getAll(Pageable pageable);

    List<? extends BaseModel> getAll();



}
