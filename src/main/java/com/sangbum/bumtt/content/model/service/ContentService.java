package com.sangbum.bumtt.content.model.service;

import com.sangbum.bumtt.content.model.dto.ContentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public interface ContentService {

    void createContent(ContentDTO contentDTO, Map<String, MultipartFile> fileMap) throws IOException;

    List<ContentDTO> selectAllContentList();

    ContentDTO selectContentByContentNo(int contentNo);

    CompletableFuture<String> updateContentStatusYnByContentNo(Map<String, Object> data) throws ExecutionException, InterruptedException;
}
