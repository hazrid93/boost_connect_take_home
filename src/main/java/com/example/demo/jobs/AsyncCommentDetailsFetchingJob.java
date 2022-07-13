package com.example.demo.jobs;

import com.example.demo.model.Comment;
import com.example.demo.model.Owner;
import com.example.demo.service.CommentService;
import com.example.demo.service.UserService;
import com.example.demo.workers.impl.CommentDetailFetcherImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsyncCommentDetailsFetchingJob implements AsyncDetailsFetchJob {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentDetailFetcherImpl commentFetcher;

    private static Logger log = LoggerFactory.getLogger(AsyncCommentDetailsFetchingJob.class);

    @Override
    public void execute() {

        log.info("staring execute job");

        List<Owner> allOwners = (List<Owner>) userService.getAll();

        if (allOwners != null) {

            try {

                allOwners.forEach(owner -> {
                    //finding is there any change happens in comment


                    List<Comment> commentsByUserId = commentFetcher.getCommentsByUserId(owner.getId());

                    //finding difference for any comment
                    owner.getComment().forEach(comment -> {

                        log.info("checking comments of @Id-> {}", comment.getId());

                        List<Comment> commentFind = commentsByUserId.stream().filter(comment1 -> comment1.getId().equals(comment.getId())).collect(Collectors.toList());

                        /* If new comment finds saving that comment*/
                        if (commentFind == null || commentFind.isEmpty()) {
                            commentService.saveComment(comment);
                        } else {
                            commentFind.get(0).equals(comment);

                            log.info("checking whether comments are equal -> " + commentFind.get(0).equals(comment));

                            if (!commentFind.get(0).equals(comment)) {
                                comment.setMessage(commentFind.get(0).getMessage());
                                commentService.saveComment(comment);
                            }
                        }

                    });


                });

            } catch (Exception e) {
                log.error("error in execute job -> {}", e);
            }
        }

        log.info("finishing execute job");

    }
}
