package com.example.demo.jpa;

import com.example.demo.model.Owner;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<Owner, String> {

    List<Owner> findAll(Pageable pageable);

    @Query("SELECT COUNT(u) FROM Owner u")
    long countAll();

}
