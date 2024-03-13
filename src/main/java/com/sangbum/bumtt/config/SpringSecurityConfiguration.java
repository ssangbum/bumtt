package com.sangbum.bumtt.config;

import com.sangbum.bumtt.authentication.model.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final CustomUserDetailsService customUserDetailsService;

//    private final JWTUtil jwtUtil;
//    private final CustomOAuth2UserService customOAuth2UserService;
//    private final CustomSuccessHandler customSuccessHandler;

    @Autowired
    public SpringSecurityConfiguration(AuthenticationConfiguration authenticationConfiguration, CustomUserDetailsService customUserDetailsService) {

        this.authenticationConfiguration = authenticationConfiguration;
        this.customUserDetailsService = customUserDetailsService;
//        this.jwtUtil = jwtUtil;
//        this.customOAuth2UserService = customOAuth2UserService;
//        this.customSuccessHandler = customSuccessHandler;
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
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        Map<String, List<String>> permitListMap = customUserDetailsService.getPermitLstMap();
        List<String> adminPermitList = permitListMap.get("adminPermitList");
        List<String> userPermitList = permitListMap.get("userPermitList");

        http
                .csrf((auth) -> auth.disable());

        http
                .httpBasic((basic) -> basic.disable());

        http
                .formLogin(Customizer.withDefaults()
                );

        //auth config
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(userPermitList.toArray(new String[userPermitList.size()])).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(adminPermitList.toArray(new String[adminPermitList.size()])).hasRole("ADMIN")
                        .anyRequest().permitAll()
                );

        //jwt filter config
//        http
//                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        //login filter config
//        http
//                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil), UsernamePasswordAuthenticationFilter.class);


//        http
//                .logout((logout) -> logout
//                        .logoutSuccessHandler()
//                        .logoutUrl("/user/logout")
//                        .invalidateHttpSession(true)
//                        .permitAll());

        //JWTFilter 추가
//        http
//                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);


//        http
//                .oauth2Login((oauth2)-> oauth2
//                        .loginPage("/user/login")
//                        .userInfoEndpoint(userInfoEndpointConfig ->
//                                userInfoEndpointConfig.userService(customOAuth2UserService))
////                        .successHandler(customSuccessHandler)
//                );

        //session config
//        http
//                .sessionManagement((session -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)));


        return http.build();
    }

}
