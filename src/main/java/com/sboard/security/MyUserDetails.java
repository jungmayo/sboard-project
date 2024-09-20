package com.sboard.security;


import com.sboard.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@ToString

public class MyUserDetails implements UserDetails {
    // User 엔티티 선언
    //DTO같은 역할
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //계정이 갖는 권한 목록(리스트로) 생성
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole())); //계정 권한 앞에 접두어 ROLE_ 붙여야 됨
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPass();
    }

    @Override
    public String getUsername() {
        return user.getUid();
    }

    @Override
    public boolean isAccountNonExpired() {
        //계정 만료 여부(true : 만료 안됨, false: 만료)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //계정 잠금 (true : 잠김아님, false : 잠김)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //비밀번호 만료 여부(true : 만료안됨 , false : 만료됨)
        return true;
    }

    @Override
    public boolean isEnabled() {
        //계정 활성화 여부 (탈퇴회원이면 비활성화 해야하는, true : 활성화, flase : 비활성화)
        return true;
    }
}
