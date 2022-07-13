package com.axiata.boostDemo.repository;

import com.axiata.boostDemo.entity.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository <Comment, String> {
}
