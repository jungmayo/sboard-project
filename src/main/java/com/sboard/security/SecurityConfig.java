package com.sboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    /*
        스프링 시큐리티
            - Spring 프레임워크에서 제공하는 보안관련 처리를 위한 프레임 워크
            - Spring 기반 프로젝트는 Spring Security보안(인증, 인가)처리 필요

        인증설정
            - 로그인, 로그아웃 페이지 및 요청주소 설정
            - 기존 인증(로그인,로그아웃)은 Security가 제공하는 기본 인증페이지
            - REST API 기반 인증에서는 토큰방식을 이용하기 때문에 로그인,로그아웃 미충족


       인가설정
            - MyUserDetails의 사용자 권한 목록을 제공하는 getAuthorities()에서 반드시 접두어로 ROLE_E라고 보면됨
            - ROLE_ 접두어를 안붙이면 hasAuthority(), hasAnyAutority()로 권한 설정
            - 존재하지 않은 요청주소에 대해서 시큐리티는 로그인페이지로 기본 Redirect 수행하기 때문에 마지막 anyRequest().permiAll() 권한설정
    */


    //최초실행 , 컨테이너에 등록
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        // 로그인 설정
        http.formLogin(login -> login
                                    .loginPage("/user/login") //컨트롤러 요청주소
                                    .defaultSuccessUrl("/user/success") //컨트롤러 요청주소
                                    .failureUrl("/user/login?success=100")
                                    .usernameParameter("uid")
                                    .passwordParameter("pass"));


        // 로그아웃 설정
        http.logout(logout -> logout
                                .invalidateHttpSession(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                                .logoutSuccessUrl("/user/login?success=101"));


        // 인가 설정
        http.authorizeHttpRequests(authorize -> authorize
                                    .requestMatchers("/").permitAll()
                                    .requestMatchers("/admin/**").hasRole("ADMIN")
                                    .requestMatchers("/manager/**").hasAnyRole("ADMIN","MANAGER")
                                    .requestMatchers("/staff/**").hasAnyRole("ADMIN","MANAGER","STAFF")
                                    .anyRequest().permitAll());

        // 기본 보안 설정
        http.csrf(configure -> configure.disable());

        return http.build();
    }
    // 비밀번호 암호화를 위해서
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
