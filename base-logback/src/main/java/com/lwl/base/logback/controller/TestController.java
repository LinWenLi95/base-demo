package com.lwl.base.logback.controller;

import com.lwl.base.logback.annotation.ApiLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @ApiLog
    @GetMapping("/get")
    public String get() {
        return "success";
    }
}
