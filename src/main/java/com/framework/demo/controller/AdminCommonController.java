package com.framework.demo.controller;

import com.framework.demo.domain.Agreements;
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
@RequestMapping("/framework/api/admin/common")
@RequiredArgsConstructor
@Tag(name = "[ADMIN] Common Management", description = "[관리자] 공통 관리")
public class AdminCommonController {
    private final CommonService commonService;

    @GetMapping("/agreements/load")
    @Operation(description = "약관 동의 내용을 조회 합니다.", summary = "약관 동의 내용 조회 API")
    public ResponseEntity<?> loadAgreements() {
        return commonService.loadAgreements();
    }

    @PostMapping("/agreements/save")
    @Operation(description = "약관 동의 내용을 등록 합니다..", summary = "약관 동의 내용 등록 API")
    public ResponseEntity<?> saveAgreements(@RequestBody @Valid List<Agreements> agreementsList) {
        return commonService.saveAgreements(agreementsList);
    }

}
