package com.framework.demo.controller;

import com.framework.demo.model.user.dto.JoinDto;
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
@RequestMapping("/v2/api/security/user")
@RequiredArgsConstructor
@Tag(name = "[USER] 클래스", description = "User Controller")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    @Operation(description = "회원가입을 진행 합니다.", summary = "회원가입 API")
    public ResponseEntity<?> join(@RequestBody JoinDto joinDto
//                                  @RequestHeader(value = 'serviceId', defaultValue = "bluchipFramework") String value
    ) {
        log.info("회원가입 API 호출");
        return userService.join(joinDto);
    }

    @PostMapping("/login")
    @Operation(description = "로그인 성공시 Access Token, Refresh Token 발급", summary = "로그인 API")
    public ResponseEntity<?> login (
            HttpServletRequest request,
            @RequestParam(required = true) @Parameter(description = "아이디")String userEmail,
            @RequestParam(required = true) @Parameter(description = "비밀번호")String password
    ) {
        log.trace("로그인 API 호출");
        System.out.println("sesson_id2: " + request.getRequestedSessionId());
        return userService.login(request, userEmail, password);
    }

    // 시큐리티 필터 테스트 API
    @PostMapping("/test")
    @Operation(description = "로그인을 통해 발급 받은 Access token을 통해 접근 가능하다.", summary = "Access 토큰 테스트 API")
    public String test () {
        System.out.println("token test API 호출");
        return "<h1>test 통과</h1>";
    }


}
