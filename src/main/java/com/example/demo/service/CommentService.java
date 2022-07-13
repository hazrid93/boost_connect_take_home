package com.example.demo.service;

import com.example.demo.model.Comment;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService extends BaseService {

    long getAllCommentCount();

    void saveComment(Comment comment);

}
