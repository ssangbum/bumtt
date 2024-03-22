package com.sangbum.bumtt.content.controller;

import com.sangbum.bumtt.content.model.dto.ContentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.sangbum.bumtt.content.model.service.ContentService;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

    @GetMapping("/admin/create")
    public String contentCreate() {

        return "/content/admin/create";
    }

    @GetMapping("/admin/management")
    public ModelAndView managementContent(ModelAndView mv) {

        List<ContentDTO> contentList = contentService.selectAllContentList();

        mv.addObject("contentList", contentList);
        mv.setViewName("content/admin/management");

        return mv;
    }

    @PostMapping("/admin/create")
    public String createContent(@ModelAttribute ContentDTO contentDTO, @RequestParam("basicThumbnail") MultipartFile basicThumbnail, @RequestParam("bannerThumbnail") MultipartFile bannerThumbnail) throws IOException {

        Map<String, MultipartFile> fileMap = new HashMap<>();
        fileMap.put("basic", basicThumbnail);
        fileMap.put("banner", bannerThumbnail);

        contentService.createContent(contentDTO, fileMap);

        return "redirect:/content/admin/management";
    }

    @GetMapping("/admin/detail/{contentNo}")
    public ModelAndView contentDetail(ModelAndView mv, @PathVariable int contentNo) {

        ContentDTO contentDTO = contentService.selectContentByContentNo(contentNo);

        String[] actors = contentDTO.getContentActors().split(",");
        List<String> actorList = Arrays.asList(actors);

        mv.addObject("content", contentDTO);
        mv.addObject("actorsList", actorList);
        mv.setViewName("content/admin/detail");

        return mv;
    }

    @PostMapping("/update/")
    public String updateContent(@ModelAttribute ContentDTO contentDTO, @RequestParam("basicThumbnail") MultipartFile basicThumbnail, @RequestParam("bannerThumbnail") MultipartFile bannerThumbnail) {


        return "";
    }

    @PostMapping("/update/contentStatus")
    @ResponseBody
    public CompletableFuture<String> updateContentStatus(@RequestBody Map<String, Object> content) throws ExecutionException, InterruptedException {

        return contentService.updateContentStatusYnByContentNo(content);
    }
}
