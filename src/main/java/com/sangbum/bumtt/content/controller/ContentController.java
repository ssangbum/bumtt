package com.sangbum.bumtt.content.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/content")
public class ContentController {

    @GetMapping("/list/**")
    public String contentList() {

        return "/content/list";
    }
}
