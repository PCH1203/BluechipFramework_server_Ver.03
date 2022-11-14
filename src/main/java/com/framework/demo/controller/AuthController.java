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

@Slf4j
@RestController
@RequestMapping("/v2/api/framework/auth")
@RequiredArgsConstructor
@Tag(name = "[AUTH] 인증/인가 클래스", description = "Auth Controller")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @Operation(description = "로그인 성공시 Access Token, Refresh Token 발급", summary = "로그인 API")
    public ResponseEntity<?> login (
            HttpServletRequest request,
            @RequestParam(required = true) @Parameter(description = "아이디")String userEmail,
            @RequestParam(required = true) @Parameter(description = "비밀번호")String password
    ) {
        return authService.login(request, userEmail, password);
    }

    @PostMapping("/logout")
    @Operation(description = "로그아웃시 IsLogin status 값 변경 및 RefreshToken 삭제", summary = "로그아웃 API")
    public ResponseEntity<?> logout (HttpServletRequest request) {
        log.trace("로그아웃 API 호출");
        System.out.println("request.AccessToken: " + request.getHeader("Authorization"));
        return authService.logout(request);
    }

    @PostMapping("/token-refresh")
    @Operation(description = "Access token을 갱신 합니다.", summary = "토큰 리프래쉬 API")
    public ResponseEntity<?> tokenRefresh(HttpServletRequest request) {       
        log.info("Token Refresh API 호출");
        System.out.println("Refresh Token: " + request.getHeader("Authorization"));
        return authService.tokenRefresh(request);
    }

}
