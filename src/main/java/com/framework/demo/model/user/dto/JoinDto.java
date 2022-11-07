package com.framework.demo.model.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class JoinDto {

    @JsonIgnore
    private String uid;

    private String userEmail;

    private String password;

    private String name;

    private String phone;

    private String type;

    private String role;



}
