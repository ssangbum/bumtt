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
    @Transactional
    @DisplayName("유저 생성")
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