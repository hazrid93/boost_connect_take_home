package com.example.demo.workers.impl;

import com.example.demo.dto.OwnerDTO;
import com.example.demo.dto.BaseDto;
import com.example.demo.model.BaseModel;
import com.example.demo.model.Owner;
import com.example.demo.service.UserService;
import com.example.demo.workers.UserDetailFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class UserDetailFetcherImpl implements UserDetailFetcher {

    @Autowired
    private RestTemplate restTemplate;

    private int totalPages = 0;
    private int pageNo = 0;

    @Autowired
    private UserService userService;

    private static Logger log = LoggerFactory.getLogger(UserDetailFetcherImpl.class);

    @Value("${user.fetching.baseUrl}")
    private String baseUrl;

    public List<Owner> getAllUsers(){

        List<Owner> allFetchedOwners;
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-id", "62cbe7898b4294930d03a226");
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);
        ResponseEntity<OwnerDTO> response = restTemplate.exchange(baseUrl+pageNo, HttpMethod.GET, httpEntity, OwnerDTO.class);
        totalPages = (int) Math.ceil((response.getBody().getTotal()/50));

        allFetchedOwners =response.getBody().getData();

        for (int i = ++pageNo; i <= totalPages; i++) {
            ResponseEntity<OwnerDTO> responsee = restTemplate.exchange(baseUrl+pageNo, HttpMethod.GET, httpEntity, OwnerDTO.class);
            responsee.getBody().getData().forEach(owner -> {
                allFetchedOwners.add(owner);
            });
        }
        return allFetchedOwners;
    }

    @Override
    public List<Owner> getUserCompleteDetails(List<String> ids) {

        HttpEntity<String> httpEntity = setHeaders();

        List<CompletableFuture<List<? extends BaseModel>>> completableUserFutureList = ids.parallelStream()
                .map(userId -> makeGetRequest(httpEntity, baseUrl + userId)).collect(Collectors.toList());

        List<? extends BaseModel> user = completableUserFutureList.parallelStream().map(listCompletableFuture -> {
                try {
                    return listCompletableFuture.get();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }).flatMap(Collection::parallelStream).collect(Collectors.toList());


            return (List<Owner>) user;
    }

    @Override
    public CompletableFuture<List<? extends BaseModel>> makeGetRequest(HttpEntity httpEntity, String url) {

        log.info("make new request -> {}", url);
        ResponseEntity<Owner> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, Owner.class);
        log.info("request complete");
        List<Owner> owner = new ArrayList<>();
        owner.add(response.getBody());
        return CompletableFuture.completedFuture(owner);


    }
}
