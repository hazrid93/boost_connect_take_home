package com.example.demo.jobs;

import com.example.demo.model.Owner;
import com.example.demo.service.UserService;
import com.example.demo.workers.impl.UserDetailFetcherImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AsyncUserDetailFetchJob implements AsyncDetailsFetchJob {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailFetcherImpl userDetailFetcher;

    private static Logger log = LoggerFactory.getLogger(AsyncUserDetailFetchJob.class);

    @Override
    public void execute(){

        log.info("staring execute");

        List<Owner> dbUsers = userService.getAllUsers();
        List<Owner> fetchedUsers = userDetailFetcher.getUserCompleteDetails(dbUsers.parallelStream().map(owner -> owner.getId()).collect(Collectors.toList()));
        try {
        /*
            comparing current DB users and fetched users and update any missing information
        */
            List<List<Owner>> collect = dbUsers.parallelStream()
                    .map(
                            dbUser -> fetchedUsers.parallelStream()
                                    .filter(fetchedUser -> fetchedUser.getId().equals(dbUser.getId()) && !fetchedUser.equals(dbUser))
                                    .collect(Collectors.toList()))

                    .collect(Collectors.toList());


            /*all changed items*/
            List<Owner> changedOwners = collect.parallelStream().flatMap(Collection::parallelStream).collect(Collectors.toList());

            userService.saveAll(changedOwners);

            /*finding wheather any new user availabe*/
            fetchedUsers.removeIf((fetchedUser -> dbUsers.parallelStream().filter(owner -> true).findAny().isPresent()));

            /*saving if new user avauilabe*/
            if(!fetchedUsers.isEmpty()) {
                userService.saveAll(fetchedUsers);
            }

        }catch (Exception e){
            log.error("exception in excute -> {}", e);
        }

        log.info("finishing execute");


    }
}
