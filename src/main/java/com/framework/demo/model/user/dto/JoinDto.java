package com.framework.demo.model.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Schema(description = "회원가입 DTO")
@Data
public class JoinDto {

    @JsonIgnore
    private String uid;

    @Schema(description = "이메일(계정아이디)", example = "test01@naver.com")
    @Email(message = "이메일 형식이 맞지 않습니다.")
//    @Email(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$", message = "유효한 전화번호 형식이 아닙니다.")
    private String userEmail;

    @Schema(description = "비밀번호", example = "abc1234")
    private String password;

    @Schema(description = "유저 이름")
    private String name;

    @Schema(description = "전화번호", example = "01011112222")

//    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "유효한 전화번호 형식이 아닙니다.")
    private String phone;

//    @JsonIgnore
    @Schema(description = "계정타입 web/app", example = "web")
    private String type;

    @Schema(description = "권한 설정", example = "4")
    private Integer roleId;

}
