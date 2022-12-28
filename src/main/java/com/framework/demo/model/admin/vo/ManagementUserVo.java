package com.framework.demo.model.admin.vo;

import lombok.Data;

@Data
public class ManagementUserVo {

    private int no;

    private String uid;

    private String userEmail;

    private String name;

    private String phone;

    private String type;

    private String role;

//    private String subRole;

    private String lockYn;
}
