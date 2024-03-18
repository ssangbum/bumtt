package com.sangbum.bumtt.user.model.service;


import com.sangbum.bumtt.config.BumttApplication;
import com.sangbum.bumtt.config.ContextConfiguration;
import com.sangbum.bumtt.config.MybatisConfiguration;
import com.sangbum.bumtt.config.SpringSecurityConfiguration;
import com.sangbum.bumtt.user.model.dao.UserDAO;
import com.sangbum.bumtt.user.model.dto.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(classes = {
        BumttApplication.class,
        ContextConfiguration.class,
        MybatisConfiguration.class
})
class UserServiceImplTest{

    @Autowired
    private UserDAO userDAO;

    @Test
    public void initTest() {
        assertNotNull(userDAO);
    }

    @Test
    @DisplayName("username을 가진 user 찾기")
    public void findUserByUsername() {

        //given
        String username = "tkdjawlwhs@nate.com";

        //when
        UserDTO user = userDAO.findUserByUsername(username);

        //then
        assertNotNull(user);
    }

    @Test
    @DisplayName("네이버를 통한 회원가입")
    public void createOAuth2User() {

        //given
        UserDTO user = new UserDTO();
        user.setUserType("NAVER");
        user.setUserName("박범상");
        user.setUserPhone("01062019811");
        user.setUserBirth("1997");

        //when
        int result = userDAO.createOAuth2User(user);

        //then
        System.out.println(result);
        assertEquals(result,1);
    }

    @Test
    @DisplayName("네이버 회원 업데이트")
    public void updateOAuth2User() {

        //given
        UserDTO user = new UserDTO();
        user.setUserName("관리자");
        user.setUserPhone("01062029811");

        //when
        int result = userDAO.updateOAuth2User(user);

        //then
        System.out.println(result);
        assertEquals(result, 1);
    }

    @Test
    @Transactional
    public void createUser() {

        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setUserPwd("a33196417@");

        //when
        int result = userDAO.createUser(userDTO);

        //then
        System.out.println(result);
        assertEquals(result, 1);
    }

}