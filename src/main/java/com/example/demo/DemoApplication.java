package com.example.demo;

import com.example.demo.jobs.AsyncCommentDetailsFetchingJob;
import com.example.demo.jobs.AsyncUserDetailFetchJob;
import com.example.demo.model.Comment;
import com.example.demo.service.CommentService;
import com.example.demo.workers.CommentDetailFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class DemoApplication {

    private static Logger log = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private AsyncCommentDetailsFetchingJob asyncCommentDetailsFetchingJob;

    @Autowired
    private AsyncUserDetailFetchJob asyncUserDetailFetchJob;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentDetailFetcher commentDetailFetcher;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    /*all comment fetching scheduler*/
    @Scheduled(fixedRateString = "${comment.fetching.scheduleInterval}")
    public void scheduleDbScanJob() {
        log.info("staring AsyncCommentDetailsFetchingJob with every 4000s staring @{}", LocalDateTime.now());
        asyncCommentDetailsFetchingJob.execute();
        log.info("ending AsyncCommentDetailsFetchingJob @{}", LocalDateTime.now());

    }

    /*all user fetching scheduler*/
    @Scheduled(fixedRateString = "${user.fetching.scheduleInterval}")
    public void scheduleuserDetailFetchJob() {
        log.info("staring AsyncUserDetailFetchJob with every 8000s staring @{}", LocalDateTime.now());
        asyncUserDetailFetchJob.execute();
        log.info("ending AsyncUserDetailFetchJob @{}", LocalDateTime.now());
    }

    /*Fetching all comment details at application start up*/
    @PostConstruct
    public void init(){
        log.info("@@@@@@@@@@   Application Started   @@@@@@@@@");

        log.info("starting fetching all comment at application startup");
        List<Comment> allComments = commentDetailFetcher.getAllComments();
        commentService.saveAll(allComments);
        log.info("finishing fetching all comment at application startup");

    }

}
