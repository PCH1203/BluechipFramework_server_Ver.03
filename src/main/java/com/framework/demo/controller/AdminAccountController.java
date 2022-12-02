package com.framework.demo.controller;

import com.framework.demo.enums.prameter.admin.AdminEnums;
import com.framework.demo.model.admin.dto.ModifyAccountDto;
import com.framework.demo.model.admin.vo.ManagementUserVo;
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

    private AdminEnums AdminEnums;

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

    /**
     * 사용자 목록 조회 API
     * @param session
     * @param userId
     * @return
     */
    @GetMapping("/user-list")
    @Operation(description = "사용자 목록을 조회 합니다.", summary = "사용자 목록 조회 API")
    public ResponseEntity<?> loadUserList (
            @RequestParam(required = false) @Parameter(description = "검색 옵션") AdminEnums.SearchOption searchOption,
            @RequestParam(required = false) @Parameter(description = "검색 값") String searchValue
    ) {
        System.out.println(">>>>> 사용자 목록조회 API Controller");
        log.info("SearchOption: " + searchOption );
        log.info("Value: " + searchValue );
        return accountManagementService.loadUserList(searchOption, searchValue);
    }

    @PostMapping("/modify/user/status")
    @Operation(description = "사용자 계정 정지 상태를 변경 합니다.", summary = "사용자 계정 정지 상태 변경 API")
    public ResponseEntity<?> modifyUserLockYn(
            @RequestParam(required = true) @Parameter(description = "uid") String uid,
            @RequestParam(required = true) @Parameter(description = "상태값") String lockYn
    ) {
        System.out.println(">>>>> 사용자 계정 정지 상태 변경 API Controller");
        log.info("uid: " + uid );
        return accountManagementService.modifyUserLockYn(uid, lockYn);
    }

}
