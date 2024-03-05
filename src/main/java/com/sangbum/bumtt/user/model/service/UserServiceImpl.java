package com.sangbum.bumtt.user.model.service;

import com.sangbum.bumtt.user.model.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService{

    private final UserDAO userDAO;

//    @Autowired
    public UserServiceImpl(UserDAO userDAO) {

        this.userDAO = userDAO;
    }

}
