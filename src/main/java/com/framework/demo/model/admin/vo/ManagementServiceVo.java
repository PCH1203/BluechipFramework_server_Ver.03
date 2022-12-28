package com.framework.demo.model.admin.vo;

import lombok.Data;

@Data
public class ManagementServiceVo {

    private String serviceId;

    private String serviceName;

    private String serviceUrl;

    private String serverPort;

    private String version;

    private String apiDocsPath;

    private String createDt;

    private String isOpen;

    private String userCount;
}
