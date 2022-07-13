package com.example.demo.workers;

import com.example.demo.model.Owner;
import com.example.demo.workers.DetailFetcher;

import java.util.List;

public interface UserDetailFetcher extends DetailFetcher {
    List<Owner> getUserCompleteDetails(List<String> ids);


}
