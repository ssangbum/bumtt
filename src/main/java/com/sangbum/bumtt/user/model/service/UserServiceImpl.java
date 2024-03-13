package com.sangbum.bumtt.user.model.service;

import com.sangbum.bumtt.user.model.dao.UserDAO;
import com.sangbum.bumtt.user.model.dto.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

@Service
public class UserServiceImpl implements UserService{

    private final UserDAO userDAO;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserDAO userDAO, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userDAO = userDAO;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void createUser(UserDTO userDTO) {

        String pwd = userDTO.getUserPwd();

        UserDTO isExist = userDAO.findUserByUsername(userDTO.getUserName());

        if(isExist != null) {

            return;
        }

        userDTO.setUserPwd(bCryptPasswordEncoder.encode(userDTO.getUserPwd()));

        userDAO.createUser(userDTO);
    }
}
