package com.ocado.cojesc.controller;

import com.ocado.cojesc.services.LunchPollingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private final LunchPollingService lunchPollingService;

    public TestController(LunchPollingService lunchPollingService) {
        this.lunchPollingService = lunchPollingService;
    }

    @GetMapping("/test")
    public void test() {
        lunchPollingService.checkForMenus();
    }
}
