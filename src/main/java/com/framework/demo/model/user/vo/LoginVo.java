package com.framework.demo.model.user.vo;

import lombok.Data;

@Data
public class LoginVo {

    private String userId;
    private String name;
    private String phone;
    private String accessToken;
    private String refreshToken;
}
