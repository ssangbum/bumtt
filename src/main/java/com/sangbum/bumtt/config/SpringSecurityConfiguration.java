package com.sangbum.bumtt.config;

import com.sangbum.bumtt.authentication.model.service.AuthenticationService;
import com.sangbum.bumtt.authentication.model.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration  {

    private final AuthenticationService authenticationService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public SpringSecurityConfiguration(AuthenticationService authenticationService, CustomOAuth2UserService customOAuth2UserService) {
        this.authenticationService = authenticationService;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return web -> web.ignoring()
                .requestMatchers(PathRequest
                        .toStaticResources()
                        .atCommonLocations())
                .requestMatchers("/css/**","/javascript/**", "/images/**");
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http
                .csrf((auth) -> auth.disable());

        http
                .httpBasic((basic) -> basic.disable());


        //auth config
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/","/main/main","/user/login", "/oauth2/**", "/login/**" ,"/css/**","/javascript/**", "/images/**").permitAll()
                        .requestMatchers("/admin","/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

//        http
//                .formLogin((auth) -> auth.loginPage("/user/login")
//                        .loginProcessingUrl("/user/login")
//                        .permitAll()
//                );

        http
                .oauth2Login((oauth2)-> oauth2
                        .loginPage("/user/login")
                        .userInfoEndpoint(userInfoEndpointConfig ->
                                userInfoEndpointConfig.userService(customOAuth2UserService)));

        return http.build();
    }

}
