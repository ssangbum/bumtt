//package com.sangbum.bumtt.jwt;
//
//import com.sangbum.bumtt.authentication.model.dto.CustomOAuth2User;
//import com.sangbum.bumtt.authentication.model.dto.CustomUser;
//import com.sangbum.bumtt.authentication.model.dto.CustomUserDetails;
//import com.sangbum.bumtt.user.model.dto.UserDTO;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//public class JWTFilter extends OncePerRequestFilter {
//
//    private final JWTUtil jwtUtil;
//
//    public JWTFilter(JWTUtil jwtUtil) {
//
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        //request header에서 Authorization을 찾음
//        String authorization = request.getHeader("Authorization");
//
//        //Authorization헤더 검증
//        if(authorization == null || authorization.startsWith("Bearer ")) {
//
//            System.out.println("token null");
//            filterChain.doFilter(request, response);
//
//            return;
//        }
//
//        System.out.println("토큰있음");
//
//        String token = authorization.split(" ")[1];
//
//        //토큰 소멸시간 검증
//        if(jwtUtil.isExpired(token)) {
//
//            System.out.println("token expired");
//            filterChain.doFilter(request,response);
//
//            return;
//        }
//
//        String username = jwtUtil.getUserEmail(token);
//        String role = jwtUtil.getRole(token);
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUserName(username);
//
//        //임시비밀번호
//        userDTO.setUserPwd("temppassword");
//        userDTO.setUserCategoryName(role);
//
//        CustomUserDetails customUserDetails = new CustomUserDetails(userDTO);
//
//        Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        filterChain.doFilter(request, response);
//    }
//}
