package com.example.demo.jpa;

import com.example.demo.model.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, String> {

    @Query("SELECT COUNT(c) FROM Comment c")
    long countAll();
    List<Comment> findAll(Pageable pageable);
}
