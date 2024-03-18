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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
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
        assertEquals(result, 1);
    }



}