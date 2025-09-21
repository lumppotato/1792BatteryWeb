package com.r1792.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {


    @GetMapping("/")
    public String index() {
        return "index"; // maps to index.html
    }

//    // log in screen
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
