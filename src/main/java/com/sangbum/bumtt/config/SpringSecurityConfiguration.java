package com.sangbum.bumtt.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration  {

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//
//        return web -> web.ignoring()
//                .requestMatchers(PathRequest
//                        .toStaticResources()
//                        .atCommonLocations())
//                .requestMatchers("/css/**","/javascript/**");
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        //auth config
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers( "/","/main/main","/user/login","/css/**","/javascript/**").permitAll()
                        .requestMatchers("/admin","/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );

        //loginPage config
        http
                .formLogin((auth) -> auth.loginPage("/user/login")
                        .loginProcessingUrl("/login")
                        .permitAll()
                );

        http
                .csrf((auth) -> auth.disable());

        return http.build();
    }

}
