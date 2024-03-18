package com.sangbum.bumtt.content.controller;

import com.sangbum.bumtt.content.model.dto.ContentDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.sangbum.bumtt.content.model.service.ContentService;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/content")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {

        this.contentService = contentService;
    }


    @GetMapping("/list/{contentType}")
    public String contentList(@PathVariable String contentType) {



        return "/content/list";
    }

    @GetMapping("/create")
    public String contentCreate() {

        return "/content/create";
    }

    @GetMapping("/management")
    public String managementContent() {

        return "/content/management";
    }

    @PostMapping("/create")
    public String createContent(@ModelAttribute ContentDTO contentDTO, @RequestParam("basicThumbnail") MultipartFile basicThumbnail, @RequestParam("bannerThumbnail") MultipartFile bannerThumbnail, HttpServletRequest request) throws IOException {

        Map<String, MultipartFile> fileMap = new HashMap<>();
        fileMap.put("basic", basicThumbnail);
        fileMap.put("banner", bannerThumbnail);

        contentService.createContent(contentDTO, fileMap);

        return "redirect:/content/management";
    }
}
