package com.sangbum.bumtt.user.controller;

import com.sangbum.bumtt.user.model.dto.UserDTO;
import com.sangbum.bumtt.user.model.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {

        return "/user/login";
    }

    @GetMapping("/signUp")
    public String signUpPage() {

        return "/user/signUp";
    }

    @PostMapping("/signUp")
    @ResponseBody
    public String signUpUser(@RequestParam("userName")String userName, @RequestParam("userPwd")String userPwd) {

        UserDTO userDTO = new UserDTO();
        userDTO.setUserName(userName);
        userDTO.setUserPwd(userPwd);

        userService.createUser(userDTO);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout() {

        return "redirect:/";
    }
}
