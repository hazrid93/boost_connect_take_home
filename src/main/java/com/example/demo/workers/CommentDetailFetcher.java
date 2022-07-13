package com.example.demo.workers;

import com.example.demo.model.Comment;

import java.util.List;

public interface CommentDetailFetcher extends DetailFetcher {

    List<Comment> getAllComments();

    List<Comment> getCommentsByUserId(String userId);


}
