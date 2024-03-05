package com.sangbum.bumtt.authentication.model.service;

import com.sangbum.bumtt.authentication.model.dto.CustomOAuth2User;
import com.sangbum.bumtt.authentication.model.dto.CustomUser;
import com.sangbum.bumtt.authentication.model.dto.NaverResponse;
import com.sangbum.bumtt.authentication.model.dto.OAuth2Response;
import com.sangbum.bumtt.user.model.dao.UserDAO;
import com.sangbum.bumtt.user.model.dto.UserDTO;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserDAO userDAO;

    public CustomOAuth2UserService(UserDAO userDAO) {

        this.userDAO = userDAO;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println(oAuth2User.getAttributes());

        //네이버인지 카카오인지 확인
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2Response oAuth2Response = null;

        if(registrationId.equals("naver")) {

            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
        else if (registrationId.equals("kakao")) {

            return null;
        }

        UserDTO existData = userDAO.findByEmail(oAuth2Response.getEmail());

        String role = null;

        if (existData == null) {

            UserDTO newUser = new UserDTO();
            newUser.setUserType(registrationId);
            newUser.setUserName(oAuth2Response.getName());
            newUser.setUserEmail(oAuth2Response.getEmail());
            newUser.setUserPhone(oAuth2Response.getMobile());
            newUser.setUserBirth(oAuth2Response.getBirthYear());

            role = newUser.getUserCategoryName();

            userDAO.saveOAuth2User(newUser);

        }
        else {

            role = existData.getUserCategoryName();

            userDAO.updateOAuth2User(existData);
        }

        return new CustomOAuth2User(oAuth2Response, role);
    }
}
