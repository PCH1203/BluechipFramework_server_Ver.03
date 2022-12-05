package com.framework.demo.controller;

import com.framework.demo.domain.Agreements;
import com.framework.demo.domain.Service;
import com.framework.demo.model.admin.dto.ServiceAddDto;
import com.framework.demo.service.admin.ServiceManagementService;
import com.framework.demo.service.common.CommonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/framework/api/admin/service")
@RequiredArgsConstructor
@Tag(name = "[ADMIN] Service Management", description = "[관리자] 서비스 관리")
public class AdminServiceController {
    private final ServiceManagementService serviceManagement;

    @PostMapping("/service/add")
    @Operation(description = "신규 서비스를 등록합니다.", summary = "서비스 등록 API")
    public ResponseEntity<?> serviceAdd(@RequestBody ServiceAddDto dto) {
        return serviceManagement.serviceAdd(dto);
    }

    @GetMapping("/service/load")
    @Operation(description = "서비스 목록을 조회 합니다.", summary = "서비스 목록 조회 API")
    public ResponseEntity<?> getServiceList() {
        log.info("서비스 목록 조회 API 실행");
        return serviceManagement.getServiceList();
    }

}
