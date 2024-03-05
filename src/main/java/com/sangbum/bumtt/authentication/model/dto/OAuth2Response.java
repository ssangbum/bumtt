package com.sangbum.bumtt.authentication.model.dto;

public interface OAuth2Response {

    String getProvider();

    String getProviderID();

    String getEmail();

    String getName();

    String getMobile();

    String getBirthYear();
}
