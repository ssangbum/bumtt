package com.sangbum.bumtt.user.model.dao;

import com.sangbum.bumtt.user.model.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

    UserDTO findUserByUsername(String username);

    int createUser(UserDTO userDTO);

    int createOAuth2User(UserDTO userDTO);

    int updateOAuth2User(UserDTO userDTO);
}
