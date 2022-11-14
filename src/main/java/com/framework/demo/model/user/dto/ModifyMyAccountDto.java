package com.framework.demo.model.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "회원 정보 수정 DTO")
@Data
public class ModifyMyAccountDto {

    @JsonIgnore
    @Schema(description = "uid")
    private String uid;
    @Schema(description = "유저 이름")
    private String name;
    @Schema(description = "전화번호")
    private String phone;


}
