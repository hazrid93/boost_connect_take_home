package com.example.demo.service;

import com.example.demo.model.Owner;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService extends BaseService{

    List<Owner> getAllUsers();

    long getAllUserCount();

    void updateUser(Owner fetchedOwne);

}
