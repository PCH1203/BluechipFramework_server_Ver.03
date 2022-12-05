package com.framework.demo.model.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Service 등록 DTO")
@Data
public class ServiceAddDto {

    @Schema(description = "서비스 아이디")
    private String serviceId;

    @Schema(description = "서비스 명")
    private String serviceName;

    @Schema(description = "service version")
    private String version;

    @Schema(description = "isOpen")
    private String isOpen;

}
