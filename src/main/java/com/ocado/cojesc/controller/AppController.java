package com.ocado.cojesc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

    @GetMapping("/test")
    public String test() {
        return "Test";
    }
}
