package com.framework.demo.model.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddSessionDto {

    @JsonIgnore
    private String uid;

    private String accessIp;

    private String sessionId;

    private String status;

    private String createDt;

    private String updateDt;



}
