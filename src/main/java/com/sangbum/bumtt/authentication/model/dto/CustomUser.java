package com.sangbum.bumtt.authentication.model.dto;

import com.sangbum.bumtt.user.model.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@ToString
public class CustomUser extends User {

    private int no;
    private String username;
    private String pwd;
    private String birth;
    private String phone;
    private String buyTicketYn;
    private String categoryName;
    private String type;

    public CustomUser(UserDTO userDTO, Collection<? extends GrantedAuthority> authorities) {
        super(userDTO.getUserName(), userDTO.getUserPwd(), authorities);
        setDetails(userDTO);
    }

    private void setDetails(UserDTO userDTO) {

        this.no = userDTO.getUserNo();
        this.username = userDTO.getUserName();
        this.pwd = userDTO.getUserPwd();
        this.birth = userDTO.getUserBirth();
        this.phone = userDTO.getUserPhone();
        this.buyTicketYn = userDTO.getUserBuyTicketYn();
        this.categoryName = userDTO.getUserCategoryName();
        this.type = userDTO.getUserType();
    }
}
