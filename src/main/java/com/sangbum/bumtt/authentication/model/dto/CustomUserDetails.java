package com.sangbum.bumtt.authentication.model.dto;

import com.sangbum.bumtt.user.model.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class CustomUserDetails implements UserDetails {

    private final UserDTO userDTO;

    public CustomUserDetails(UserDTO userDTO) {

        this.userDTO = userDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

                return userDTO.getUserCategoryName();
            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return userDTO.getUserPwd();
    }

    @Override
    public String getUsername() {

        return userDTO.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
