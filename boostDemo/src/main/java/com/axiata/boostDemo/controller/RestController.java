package com.axiata.boostDemo.controller;

import com.axiata.boostDemo.service.ScrapeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    ScrapeService scrapeService;

    @GetMapping("/start")
    public String index() {
        log.info("Incoming Request : /start");
        return scrapeService.startJob();
    }
}