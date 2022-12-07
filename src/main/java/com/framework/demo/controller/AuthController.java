package com.framework.demo.controller;

import com.framework.demo.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/v2/api/framework/auth")
@RequiredArgsConstructor
@Tag(name = "[AUTH] Authentication / Authorization", description = "계정 인증 / 인가")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login/id-pw")
    @Operation(description = "ID/PW 로그인", summary = "로그인 STEP_1")
    public ResponseEntity<?> appLoginStep1 (
            HttpServletRequest request,
            HttpSession session,
            @RequestParam(required = true) @Parameter(description = "서비스 아이디")String serviceId,
            @RequestParam(required = true) @Parameter(description = "아이디")String userEmail,
            @RequestParam(required = true) @Parameter(description = "비밀번호")String password
    ) {
        System.out.println(">>>>> ID/PW 로그인 API");
        return authService.appLoginStep1(request,session, serviceId, userEmail, password);
    }

    @PostMapping("/login/send-otp")
    @Operation(description = "otp 전송.", summary = "로그인 STEP_2")
    public ResponseEntity<?> appLoginStep2 (
            HttpServletRequest request,
            HttpSession session,
            @RequestParam(required = false) @Parameter(description = "OTP 전송 목적", example = "login, signUp") String type,
            @RequestParam(required = true) @Parameter(description = "수신자 번호") String sendTo
    ) {
        return authService.loginSendOtp(request, session, sendTo);

    }

    @PostMapping("/login/check-otp")
    @Operation(description = "otp 인증", summary = "로그인 STEP_3")
    public ResponseEntity<?> appLoginStep3 (
            HttpServletRequest request,
            HttpSession session,
            @RequestParam(required = true) @Parameter(description = "otp 인증번호 입력.") String otpPassword
    ) {
        System.out.println(">>>>> OTP 인증 API");
        return authService.loginCheckOtp(request, session, otpPassword);
    }

    @PostMapping("/logout")
    @Operation(description = "로그아웃", summary = "로그아웃 API")
    public ResponseEntity<?> logout (HttpServletRequest request, HttpSession session) {
        System.out.println(">>>>> 로그아웃 API");
        return authService.logout(request, session);
    }
    @PostMapping("/token-refresh")
    @Operation(description = "Access token을 갱신 합니다.", summary = "토큰 리프래쉬 API")
    public ResponseEntity<?> tokenRefresh(HttpServletRequest request) {
        return authService.tokenRefresh(request);
    }

    @PostMapping("/session/login")
    @Operation(description = "Session 로그인", summary = " session 로그인 API")
    public ResponseEntity<?> webLogin (
            HttpServletRequest request,
            @RequestParam(required = true) @Parameter(description = "아이디")String userEmail,
            @RequestParam(required = true) @Parameter(description = "비밀번호")String password
    ) {
        return authService.webLogin(request, userEmail, password);
    }

    @PostMapping("/session/test")
    @Operation(description = "session test API", summary = "session test API")
    public ResponseEntity<?> webLogin (
            HttpServletRequest request,
            HttpSession session
    ) {
        System.out.println("session_id: " + session.getId());
        System.out.println("접근 ip: " + request.getRemoteAddr());
        return null;
    }

}
