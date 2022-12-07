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
import javax.servlet.http.HttpSession;
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
            @RequestParam(required = true) @Parameter(description = "서비스 아이디") String serviceId,
            @RequestParam(required = true) @Parameter(description = "아이디")String userEmail ) {
        System.out.println(">>>>> 아이디 중복검사 API");
        return userService2.emailCheck(userEmail, serviceId);
    }

    /**
     * 계정 연동을 위한 로그인 인증 API
     * @param userEmail
     * @param password
     * @return
     */
    @PostMapping("/join/interlock/login")
    @Operation(description = "계정 연동을 위한 로그인", summary = "계정 연동을 위한 로그인 API")
    public ResponseEntity<?> interlockLogin(
            HttpSession session,
            @RequestParam(required = true) @Parameter(description = "아이디")String userEmail,
            @RequestParam(required = true) @Parameter(description = "비밀번호")String password
    ) {
        System.out.println(">>>>> 계정 연동 로그인 API (controller)");
        return userService2.interlockLogin(session, userEmail, password);
    }

    @GetMapping("/my-account")
    @Operation(description = "나의 프로필 조회.", summary = "MY PROFILE 조회 API")
    @ApiResponse(responseCode = "200", description = "나의 계정 정보를 조회 합니다.", content = @Content(schema = @Schema(implementation = User.class)))
    public ResponseEntity<?> findMyAccount (HttpServletRequest request) {
        return userService.findMyAccount(request);
    }

    @GetMapping("/my/service-list")
    @Operation(description = "나의 서비스 목록 조회.", summary = "나의 서비스 목록 조회 API")
    public ResponseEntity<?> signedUpServiceList (
//            @RequestParam(required = false) @Parameter(description = "uid")String uid
            HttpSession session
    ) {
        System.out.println(">>>>> myServiceList API (controller)");
        System.out.println(">>>>> uid: " + (String) session.getAttribute("uid"));
        return userService2.signedUpServiceList(session);
    }


}
