package com.sangbum.bumtt.content.model.service;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sangbum.bumtt.config.S3Configuration;
import com.sangbum.bumtt.content.model.dao.ContentDAO;
import com.sangbum.bumtt.content.model.dto.ContentAttachmentDTO;
import com.sangbum.bumtt.content.model.dto.ContentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ContentServiceImpl implements ContentService{

    private final ContentDAO contentDAO;
    private final S3Configuration s3Configuration;

    private final String localLocation = "C:\\bumttStorage\\thumbnail";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Autowired
    public ContentServiceImpl(ContentDAO contentDAO, S3Configuration s3Configuration) {

        this.contentDAO = contentDAO;
        this.s3Configuration = s3Configuration;
    }

    @Override
    @Transactional
    public void createContent(ContentDTO contentDTO, Map<String, MultipartFile> fileMap) throws IOException {

        contentDAO.createContent(contentDTO);

        for(String category : fileMap.keySet()) {

            MultipartFile file = fileMap.get(category);

            if(!file.isEmpty()){

                String fileName = file.getOriginalFilename();

                String ext = fileName.substring(fileName.indexOf("."));

                String uuidFileName = UUID.randomUUID() + ext;

                String localPath = localLocation + uuidFileName;

                File localFile = new File(localPath);
                file.transferTo(localFile);

                s3Configuration.amazonS3Client().putObject(new PutObjectRequest(bucket, uuidFileName, localFile).withCannedAcl(CannedAccessControlList.PublicRead));

                String s3Url = s3Configuration.amazonS3Client().getUrl(bucket, uuidFileName).toString();

                ContentAttachmentDTO contentAttachmentDTO = new ContentAttachmentDTO();
                contentAttachmentDTO.setContentAttachmentOriginalName(fileName);
                contentAttachmentDTO.setContentAttachmentSaveName(uuidFileName);
                contentAttachmentDTO.setContentAttachmentSavePath(s3Url);
                contentAttachmentDTO.setContentAttachmentCategory(category);
                contentAttachmentDTO.setContentNo(contentDTO.getContentNo());

                contentDAO.createContentAttachment(contentAttachmentDTO);
            }

        }

    }

    @Override
    public List<ContentDTO> selectAllContentList() {

        return contentDAO.selectAllContentList();
    }

    @Override
    public ContentDTO selectContentByContentNo(int contentNo) {

        return contentDAO.selectContentByContentNo(contentNo);
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<String> updateContentStatusYnByContentNo(Map<String, Object> content) {

        return CompletableFuture.supplyAsync(() -> {

            int result  = contentDAO.updateContentStatusYnByContentNo(content);

            if(result == 1) {

                return "success";
            } else {

                return "fail";
            }
        });
    }
}
