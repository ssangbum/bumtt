package com.sangbum.bumtt.user.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDTO {

    private int userNo;
    private String userType;
    private String userName;
    private String userEmail;
    private String userPwd;
    private String userPhone;
    private String userBirth;
    private String userBuyTicketYn;
    private String userCategoryName;

}
