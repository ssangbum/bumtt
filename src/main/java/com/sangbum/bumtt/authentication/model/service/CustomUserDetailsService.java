package com.sangbum.bumtt.authentication.model.service;

import com.sangbum.bumtt.authentication.model.dto.CustomUser;
import com.sangbum.bumtt.authentication.model.dto.CustomUserDetails;
import com.sangbum.bumtt.user.model.dao.UserDAO;
import com.sangbum.bumtt.user.model.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDAO userDAO;

    public CustomUserDetailsService(UserDAO userDAO) {

        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserDTO userDTO = userDAO.findUserByUsername(userName);

        if(userDTO == null) {

            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(userDTO.getUserCategoryName()));

        return new CustomUser(userDTO, authorities);
    }

    public Map<String, List<String>> getPermitLstMap() {

        Map<String, List<String>> permitListMap = new HashMap<>();
        List<String> adminPermitList = new ArrayList<>();
        List<String> userPermitList = new ArrayList<>();

        adminPermitList.add("/content/create");
        adminPermitList.add("/content/management");
        adminPermitList.add("/content/detail");
        adminPermitList.add("/content/update");

        userPermitList.add("/user/purchase");
        userPermitList.add("/user/watch");
        userPermitList.add("/user/logout");

        permitListMap.put("adminPermitList", adminPermitList);
        permitListMap.put("userPermitList", userPermitList);

        return permitListMap;
    }
}
