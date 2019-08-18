package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @RequestMapping("/")
    String index(){
        return "index";
    }
    @RequestMapping("/home")
    String home(){
        return "home";
    }
    @RequestMapping("/admin")
    String admin(){
        return "admin/admin";
    }
}
