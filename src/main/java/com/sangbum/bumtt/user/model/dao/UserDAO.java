package com.sangbum.bumtt.user.model.dao;

import com.sangbum.bumtt.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    UserDTO findByUsername(String username);

    UserDTO findUser();

    UserDTO findByEmail(String email);

    int saveOAuth2User(UserDTO newUser);

    int updateOAuth2User(UserDTO existData);
}
