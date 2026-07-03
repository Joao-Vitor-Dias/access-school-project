package com.accesses.administrative_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ThymleafController {

    @GetMapping("/home")
    public String home(){
        return "home";
    }

}
