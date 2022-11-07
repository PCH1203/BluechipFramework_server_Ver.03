package com.framework.demo.model.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IsLoginDto {

    @JsonIgnore
    private String uid;

    private String userId;

    private String type;

    private String isLogin;

}
