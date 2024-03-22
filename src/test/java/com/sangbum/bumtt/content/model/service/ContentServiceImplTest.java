package com.sangbum.bumtt.content.model.service;

import com.sangbum.bumtt.config.BumttApplication;
import com.sangbum.bumtt.config.ContextConfiguration;
import com.sangbum.bumtt.config.MybatisConfiguration;
import com.sangbum.bumtt.content.model.dao.ContentDAO;
import com.sangbum.bumtt.content.model.dto.ContentDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = {
        BumttApplication.class,
        ContextConfiguration.class,
        MybatisConfiguration.class
})
class ContentServiceImplTest {

    @Autowired
    private ContentDAO contentDAO;

    @Test
    public void initTest() {

        assertNotNull(contentDAO);
    }

    @Test
    @Transactional
    @DisplayName("컨텐츠 생성")
    public void createContent() throws ParseException {

        //given
        ContentDTO contentDTO = new ContentDTO();
        contentDTO.setContentTitle("재벌형사");
        contentDTO.setContentStartDate("2024-03-01");
        contentDTO.setContentComment("철부지 재벌3세가 강력팀 형사가 되어 보여주는 '돈에는 돈, 빽에는 빽' FLEX 수사기");
        contentDTO.setContentDirector("김재홍");
        contentDTO.setContentActors("안보현, 박지현, 강상준, 김신비");
        contentDTO.setContentType("드라마");
        contentDTO.setContentGrade("15세 이상 관람가");

        //when
        int result = contentDAO.createContent(contentDTO);

        //then
        System.out.println(result);

        assertEquals(result, 1);
    }

    @Test
    @DisplayName("모든 컨텐츠 조회")
    public void selectAllContentList() {

        //give

        //when
        List<ContentDTO> contentList = contentDAO.selectAllContentList();

        //then
        for(ContentDTO content: contentList) {
            System.out.println(content);
        }
        assertNotEquals(contentList.size(), 0);
    }

    @Test
    @DisplayName("컨텐츠 번호의 컨텐츠 조회")
    public void selectContentByContentNo() {

        //given
        int contentNo = 9;

        //when
        ContentDTO contentDTO = contentDAO.selectContentByContentNo(contentNo);

        String[] actors = contentDTO.getContentActors().split(",");
        List<String> actorList = Arrays.asList(actors);

        //then
        for(String actor : actorList) {

            System.out.println(actor);
        }
        System.out.println(contentDTO);
        assertNotNull(contentDTO);
    }

    @Test
    @Transactional
    @DisplayName("비동기 컨텐츠 활성화, 비활성화 상태변경")
    @Async
    public void updateContentStatusYnByContentNo() throws ExecutionException, InterruptedException {

        //given
        int contentNo = 9;
        String contentStatusYn = "Y";
        Map<String, Object> contentMap =  new HashMap<>();
        contentMap.put("contentNo", contentNo);
        contentMap.put("contentStatusYn", contentStatusYn);

        //when
        CompletableFuture<Integer> contentFuture = CompletableFuture.supplyAsync(() -> {

                int result = 0;

                try{
                    result = contentDAO.updateContentStatusYnByContentNo(contentMap);

                    Thread.sleep(1000); // orderInfoRepository.findByOrderNo(orderNo);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return result;
        });

        //then
        System.out.println(contentFuture.get());
        assertEquals(1, contentFuture.get());
    }
}