package com.framework.demo.controller;

import com.framework.demo.model.admin.dto.ModifyAccountDto;
import com.framework.demo.service.admin.AccountManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/framework/api/admin/account")
@RequiredArgsConstructor
@Tag(name = "[ADMIN] Account Management", description = "[관리자] 사용자 계정 관리")
public class AdminAccountController {
    private final AccountManagementService accountManagementService;

    /**
     * 회원정보 수정 API
     * @param modifyAccountDto
     * @return
     */
    @PutMapping("/modify")
    @Operation(description = "(관리자 권한) 회원정보를 수정 합니다.", summary = "회원 정보 수정 API")
    public ResponseEntity<?> modifyUserAccount(@RequestBody ModifyAccountDto modifyAccountDto) {
        return accountManagementService.modifyUserAccount(modifyAccountDto);
    }

    /**
     * 강제 로그아웃 API
     * @param userId
     * @return
     */
    @PostMapping("/force-logout")
    @Operation(description = "관리자 권한이 필요한 강제 로그아웃 API입니다.", summary = "강제 로그아웃 API")
    public ResponseEntity<?> forceLogout (
            HttpSession session,
            @RequestParam(required = true) @Parameter(description = "userId")String userId
    ) {
        return accountManagementService.forceLogout(session, userId);
    }




}
