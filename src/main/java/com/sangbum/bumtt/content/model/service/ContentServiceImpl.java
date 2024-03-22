package com.sangbum.bumtt.content.model.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.sangbum.bumtt.config.S3Configuration;
import com.sangbum.bumtt.content.model.dao.ContentDAO;
import com.sangbum.bumtt.content.model.dto.ContentAttachmentDTO;
import com.sangbum.bumtt.content.model.dto.ContentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.concurrent.CompletableFuture;

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

    @Override
    @Transactional
    public void updateContent(ContentDTO contentDTO, Map<String, MultipartFile> fileMap) throws IOException {

        contentDAO.updateContent(contentDTO);

        System.out.println(fileMap);

        //s3에 있는 파일이름 불러오기
        Map<String, String> s3saveNameMap = contentDAO.selectContentAttachmentSaveNameByContentNo(contentDTO.getContentNo());

        //MultipartFile로 변환한 다음 비교
        for(String category : fileMap.keySet()) {

            // S3 객체 가져오기
            S3Object s3Object = s3Configuration.amazonS3Client().getObject(new GetObjectRequest(bucket, s3saveNameMap.get(category)));

            // S3 객체의 내용을 InputStream으로 읽어들임
            InputStream inputStream = s3Object.getObjectContent();

            // InputStream을 바이트 배열로 읽어들임
            byte[] byteArray = StreamUtils.copyToByteArray(inputStream);

            // MultipartFile로 변환
            MultipartFile multipartFile = new MockMultipartFile(s3saveNameMap.get(category), s3saveNameMap.get(category), null, byteArray);

            if(!Objects.deepEquals(fileMap.get(category).getBytes(), multipartFile.getBytes())) {

                String fileName = fileMap.get(category).getOriginalFilename();

                String ext = fileName.substring(fileName.indexOf("."));

                String uuidFileName = UUID.randomUUID() + ext;

                String localPath = localLocation + uuidFileName;

                File localFile = new File(localPath);
                fileMap.get(category).transferTo(localFile);

                s3Configuration.amazonS3Client().putObject(new PutObjectRequest(bucket, uuidFileName, localFile).withCannedAcl(CannedAccessControlList.PublicRead));

                String s3Url = s3Configuration.amazonS3Client().getUrl(bucket, uuidFileName).toString();

                ContentAttachmentDTO contentAttachmentDTO = new ContentAttachmentDTO();
                contentAttachmentDTO.setContentAttachmentOriginalName(fileName);
                contentAttachmentDTO.setContentAttachmentSaveName(uuidFileName);
                contentAttachmentDTO.setContentAttachmentSavePath(s3Url);
                contentAttachmentDTO.setContentAttachmentCategory(category);
                contentAttachmentDTO.setContentNo(contentDTO.getContentNo());

                contentDAO.updateContentAttachment(contentAttachmentDTO);
            }
        }
    }
}
