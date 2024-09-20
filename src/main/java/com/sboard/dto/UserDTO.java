package com.sboard.dto;

import com.sboard.entity.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String uid;
    private String pass;
    private String name;
    private String nick;
    private String email;
    private String hp;
    private String role;
    private String zip;
    private String addr1;
    private String addr2;
    private String regip;
    private String regDate;
    private String leaveDate;
    private String auth;


    public User toEntity(){
        return User.builder()
                .uid(uid)
                .pass(pass)
                .name(name)
                .nick(nick)
                .hp(hp)
                .role(role)
                .email(email)
                .zip(zip)
                .addr1(addr1)
                .addr2(addr2)
                .regip(regip)

                .build();
    }



}
