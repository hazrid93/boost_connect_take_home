package com.example.demo.workers.impl;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.model.BaseModel;
import com.example.demo.model.Comment;
import com.example.demo.service.impl.CommentServiceImpl;
import com.example.demo.workers.CommentDetailFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
public class CommentDetailFetcherImpl implements CommentDetailFetcher {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${comment.fetching.baseUrl}")
    private String commentFetchingBaseUrl;
    @Value("${user.fetching.baseUrl}")
    private String userFetchingBaseUrl;
    private static Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    private List<Comment> allcomments;



    public List<Comment> getAllComments(){
        String finalUrl = commentFetchingBaseUrl +("limit=50&page=0");

        HttpEntity<String> httpEntity = setHeaders();
        /*fetching all comment details*/
        ResponseEntity<ResponseDTO> response = restTemplate.exchange(finalUrl, HttpMethod.GET, httpEntity, ResponseDTO.class);

        /*if all cooment count lesstan 50 returning data*/
        if(response.getBody().getTotal() < 50){
            return response.getBody().getData();
        }else{
            allcomments = response.getBody().getData();
            //normal process
            int fetchCount = (int) Math.ceil(response.getBody().getTotal() / 50);

            List<String> urls = new ArrayList<>();
            for (int i = 0; i <= fetchCount ; i++) {
                urls.add(commentFetchingBaseUrl +("limit=50&page="+i));
            }

            /*parallel requesting data based on page no*/
            List<CompletableFuture<List<? extends BaseModel>>> completableFutureList = urls.parallelStream().map(url -> makeGetRequest(httpEntity, url)).collect(Collectors.toList());
            List<? extends BaseModel> allComments = completableFutureList.parallelStream().map(listCompletableFuture -> {
                try {
                    return listCompletableFuture.get();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).flatMap(Collection::parallelStream).collect(Collectors.toList());

            return (List<Comment>) allComments;
        }
    }

    public List<Comment> getCommentsByUserId(String userId){
        String finalUrl = userFetchingBaseUrl + userId + "/comment";
        HttpEntity<String> httpEntity = setHeaders();
        CompletableFuture<List<? extends BaseModel>> completableFutureList = makeGetRequest(httpEntity, finalUrl);

        List<? extends BaseModel> allComments;
        try {
           allComments = completableFutureList.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        return allcomments;
    }


    @Async
    @Override
    public CompletableFuture<List<? extends BaseModel>> makeGetRequest(HttpEntity httpEntity, String url) {

        log.info("make new request -> {}", url);
        ResponseEntity<ResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, ResponseDTO.class);
        log.info("request complete");
        return CompletableFuture.completedFuture((response.getBody().getData()));


    }

}
