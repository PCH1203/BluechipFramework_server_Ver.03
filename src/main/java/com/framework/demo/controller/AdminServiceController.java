package com.framework.demo.controller;

import com.framework.demo.enums.prameter.admin.AdminEnums;
import com.framework.demo.enums.prameter.admin.AdminServiceEnums;
import com.framework.demo.model.admin.dto.ServiceAddDto;
import com.framework.demo.service.admin.ServiceManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/framework/api/admin/service")
@RequiredArgsConstructor
@Tag(name = "[ADMIN] Service Management", description = "[관리자] 서비스 관리")
public class AdminServiceController {
    private final ServiceManagementService serviceManagementService;

    @PostMapping("/add")
    @Operation(description = "신규 서비스를 등록합니다.", summary = "서비스 등록 API")
    public ResponseEntity<?> serviceAdd(@RequestBody ServiceAddDto dto) {
        return serviceManagementService.serviceAdd(dto);
    }

    /**
     * 서비스 목록 조회 API
     * @return
     */
    @GetMapping("/load-list")
    @Operation(description = "서비스 목록을 조회 합니다.", summary = "서비스 목록 조회 API")
    public ResponseEntity<?> getServiceList(
//            @RequestParam(required = false) @Parameter(description = "검색 옵션") AdminServiceEnums.SearchOption searchOption,
            @RequestParam(required = false) @Parameter(description = "검색 옵션") String searchOption,
            @RequestParam(required = false) @Parameter(description = "검색 값") String searchValue
    ) {
        System.out.println("서비스 목록을 조회 합니다.");
        return serviceManagementService.getServiceList(searchOption, searchValue);
    }

    @PostMapping("/modify/status")
    @Operation(description = "서비스 상태 변경", summary = "서비스 상태 변경")
    public ResponseEntity<?> modifyServiceStatus(
            @RequestParam(required = true) @Parameter(description = "service_id") String serviceId
    ) {
        System.out.println(">>>>> 서비스 상태 변경 API");
        return serviceManagementService.modifyServiceStatus(serviceId);
    }

}
