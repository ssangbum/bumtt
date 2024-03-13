package com.sangbum.bumtt.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;

@Controller
public class MainController {

    @GetMapping(value = {"/","/main"})
    public String mainPage() {

        return "main/main";
    }
}
