package com.framework.demo.model.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class SignedUpServiceListVo {

    private String serviceId;

    private String serviceName;

    private String status;

}
