package com.example.demo.service.impl;


import com.example.demo.jpa.CommentRepository;
import com.example.demo.jpa.UserRepository;
import com.example.demo.model.BaseModel;
import com.example.demo.model.Comment;
import com.example.demo.model.Owner;
import com.example.demo.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository ownerRepository;

    private static Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    /*saving all comment data*/
    @Override
    public void saveAll(List<? extends BaseModel> commentList) {

        try {
            List<Comment> data = (List<Comment>) commentList;
            log.info("staring saving all comments");
            List<Owner> users = data.stream().map(Comment::getOwner).collect(Collectors.toList());
            ownerRepository.saveAll(users);
            commentRepository.saveAll(data);
            log.info("finishing saving all comments");
        }catch (Exception e){
            log.error("error in saving all comment data -> {}", e);
        }

    }

    @Override
    public List<? extends BaseModel> getAll(Pageable pageable) {
        return commentRepository.findAll(pageable);
    }

    @Override
    public List<? extends BaseModel> getAll() {
        return (List<? extends BaseModel>) commentRepository.findAll();
    }

    @Override
    public long getAllCommentCount() {
        return commentRepository.countAll();
    }

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

}
