package com.framework.demo.model.admin.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;

@Schema(description = "User 계정 정보 변경 Dto")
@Data
public class ModifyAccountDto {

    @Schema(description = "사용자 uid")
    private String uid;
    @Schema(description = "이메일(계정아이디)")
    private String userEmail;
    @Schema(description = "비밀번호")
    private String password;
    @Schema(description = "유저 이름")
    private String name;
    @Schema(description = "전화번호")
    private String phone;
    @Schema(description = "계정타입 web/app")
    private String type;
    @Schema(description = "권한 user/admin")
    private String role;
    @Schema(description = "권한 general/master")
    private String subRole;
    @Schema(description = "계정 잠금 상태")
    private String lockYn;
    @Schema(description = "비밀번호 실패 카운트")
    private String passwordFailCnt;



}
