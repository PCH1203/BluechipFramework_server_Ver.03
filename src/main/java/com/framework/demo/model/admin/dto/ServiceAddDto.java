package com.framework.demo.model.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Service 등록 DTO")
@Data
public class ServiceAddDto {

    @Schema(description = "서비스 아이디")
    private String serviceId;

    @Schema(description = "서비스 명", example = "블루칩 서비스_01")
    private String serviceName;

    @Schema(description = "서비스 주소", example = "localhost:3000/bluechip/cms")
    private String serviceUrl;

    @Schema(description = "서버 포트 번호", example = "localhost:8080")
    private String serverPort;

    @Schema(description = "API 문서 주소", example = "/framework/service_01")
    private String apiDocsPath;

//    @Schema(description = "서비스 버전 정보", example = "1.0.0")
//    private String version;

//    @Schema(description = "isOpen" , example = )
//    private String isOpen;

}
