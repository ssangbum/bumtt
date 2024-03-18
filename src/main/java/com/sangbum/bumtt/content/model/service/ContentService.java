package com.sangbum.bumtt.content.model.service;

import com.sangbum.bumtt.content.model.dto.ContentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface ContentService {

    void createContent(ContentDTO contentDTO, Map<String, MultipartFile> fileMap) throws IOException;
}
