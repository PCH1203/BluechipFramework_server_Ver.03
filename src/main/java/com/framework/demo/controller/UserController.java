package com.framework.demo.controller;

import com.framework.demo.domain.User;
import com.framework.demo.enums.prameter.auth.ServiceEnums;
import com.framework.demo.model.user.dto.JoinDto;
import com.framework.demo.model.user.dto.ModifyMyAccountDto;
import com.framework.demo.service.user.UserService;
import com.framework.demo.service.user.UserService2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/framework/api/user")
@RequiredArgsConstructor
@Tag(name = "[USER] Account", description = "사용자 기능")
public class UserController {
    private final UserService userService;
    private final UserService2 userService2;

    /**
     * 화원가입 API
     * @param joinDto
     * @param serviceId
     * @return
     */
    @PostMapping("/join")
    @Operation(description = "회원가입을 진행 합니다.", summary = "회원가입 API")
    public ResponseEntity<?> join(@RequestBody @Valid JoinDto joinDto) {
        System.out.println("회원가입 API");
        return userService.join(joinDto);
    }

    @GetMapping("/join/id-check")
    @Operation(description = "회원가입시 아이디 중복 검사를 실행합니다.", summary = "아이디 중복검사 API")
    public ResponseEntity<?> emailCheck(
//            @RequestParam(required = true) @Parameter(description = "서비스 아이디") ServiceEnums.ServiceId serviceId,
            @RequestParam(required = true) @Parameter(description = "서비스 아이디") String serviceId,
            @RequestParam(required = true) @Parameter(description = "아이디")String userEmail ) {
        System.out.println(">>>>> 아이디 중복검사 API");
        return userService2.emailCheck(userEmail, serviceId);
    }

    @GetMapping("/my-account")
    @Operation(description = "나의 프로필 조회.", summary = "MY PROFILE 조회 API")
    @ApiResponse(responseCode = "200", description = "나의 계정 정보를 조회 합니다.", content = @Content(schema = @Schema(implementation = User.class)))
    public ResponseEntity<?> findMyAccount (HttpServletRequest request) {
        return userService.findMyAccount(request);
    }

    @PutMapping("/modify-account")
    @Operation(description = "회원 정보 수정", summary = "회원 정보 수정 API")
    public ResponseEntity<?> modifyMyAccount (HttpServletRequest request, @RequestBody ModifyMyAccountDto modifyMyAccountDto) {
        return userService.modifyMyAccount(request, modifyMyAccountDto);
    }

}
