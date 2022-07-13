package com.axiata.boostDemo.service;

import com.axiata.boostDemo.dto.CommentDTO;
import com.axiata.boostDemo.dto.CommentResDTO;
import com.axiata.boostDemo.entity.Comment;
import com.axiata.boostDemo.entity.User;
import com.axiata.boostDemo.repository.CommentRepository;
import com.axiata.boostDemo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@Service
public class ScrapeService {

    @Value("${service.base-url}")
    private String baseUrl;
    @Value("${service.app-id}")
    private String appId;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UserRepository userRepository;

    private WebClient getWebClient()
    {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    private Mono<CommentResDTO> getComments(int page){
        WebClient client = getWebClient();
        return client.get()
                .uri(uriBuilder -> uriBuilder.path("/comment")
                        .queryParam("page", page)
                        .build())
                .header("app-id", appId)
                .retrieve()
                .bodyToMono(CommentResDTO.class);
    }

    private void getInParallel(int end) {
        IntStream.rangeClosed(1, end)
                .parallel()
                .mapToObj(this::getComments)
                .map(data -> data.share().block())
                .forEach(data -> writeToDb(data.getData()));
    }

    public String startJob(){
        CommentResDTO data = getComments(0).block();
        if(data.getData().size()>0){
            log.info("Data : {}", data); //process first page
            writeToDb(data.getData());
            int pageCount = Integer.parseInt(data.getTotal())/Integer.parseInt(data.getLimit());
            log.info("Page Count : {}",pageCount);
            if(pageCount>0){
                log.info("Additional pages found!");
                getInParallel(pageCount); //process remaining pages
            }else{
                log.info("No additional pages found!");
            }
            return "Completed !";
        }else{
            log.info("No data found in the response!");
            return "No data found in the response!";
        }
    }

    private void writeToDb(List<CommentDTO> comments){
        log.info("Page Data : {}", comments);
        for (CommentDTO comment : comments) {
            userRepository.save(new User(comment.getOwner().getId(),comment.getOwner().getTitle(),comment.getOwner().getFirstName(),comment.getOwner().getLastName(),comment.getOwner().getPicture()));
            commentRepository.save(new Comment(comment.getId(),comment.getMessage(),comment.getOwner().getId(),comment.getPost(),comment.getPublishDate()));
        }
    }
}