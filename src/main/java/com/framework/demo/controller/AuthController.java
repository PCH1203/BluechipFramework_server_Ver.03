package com.framework.demo.controller;

import com.framework.demo.model.user.dto.JoinDto;
import com.framework.demo.service.auth.AuthService;
import com.framework.demo.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/v2/api/framework/auth")
@RequiredArgsConstructor
@Tag(name = "[Auth] 사용자 인증 클래스", description = "Auth Controller")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/token-refresh")
    @Operation(description = "Access token을 갱신 합니다.", summary = "Token Refresh API")
    public ResponseEntity<?> tokenRefresh(HttpServletRequest request) {       
        log.info("Token Refresh API 호출");
        System.out.println("Refresh Token: " + request.getHeader("Authorization"));
        return authService.tokenRefresh(request);
    }

}
