package com.framework.demo.model.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserVo {

    private String uid;
    @Email
    private String userEmail;
    @Schema(description = "비밀번호")
    private String password;
    private String name;
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
