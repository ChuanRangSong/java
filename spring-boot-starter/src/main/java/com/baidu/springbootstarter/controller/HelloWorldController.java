package com.baidu.springbootstarter.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello !";
    }

    @RequestMapping("/helloworld")
    public String helloworld() {
        return "Hello World!";
    }

}
