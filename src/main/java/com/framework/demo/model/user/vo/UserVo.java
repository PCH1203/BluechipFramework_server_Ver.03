package com.framework.demo.model.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UserVo {

    private String uid;
    private String userEmail;
    private String password;
    private String userNickname;
    private String phone;
//    private List<String> roles;
    private String role;
    private String accessToken;
    private String refreshToken;

//    private boolean isAccountNonExpired;
//    private boolean isAccountNonLocked;
//    private boolean isCredentialsNonExpired;
//    private boolean isEnabled;

    public String getUsername() {
        return userEmail;
    }
}
