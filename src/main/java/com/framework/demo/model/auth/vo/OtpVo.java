package com.framework.demo.model.auth.vo;

import lombok.Data;

@Data
public class OtpVo {
    private Long otpId;
    private String otpPassword;
    private String creator;
    private String sendTo;
    private String createDt;
    private String expireDt;


//    private String to;

//    private String template;
//    private String refId;	// 연관ID
//    private String refEntity;  // 관련명
//    private int expireState;
}
