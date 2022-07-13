package com.example.demo.workers;

import com.example.demo.dto.BaseDto;
import com.example.demo.model.BaseModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface DetailFetcher {

    CompletableFuture<List<? extends BaseModel>> makeGetRequest(HttpEntity httpEntity, String url);

    default HttpEntity setHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-id", "62cbe7898b4294930d03a226");
        return new HttpEntity<>(null, headers);
    }

}
