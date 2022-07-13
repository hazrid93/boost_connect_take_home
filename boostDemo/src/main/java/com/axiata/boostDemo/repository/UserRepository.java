package com.axiata.boostDemo.repository;

import com.axiata.boostDemo.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, String>{
}
