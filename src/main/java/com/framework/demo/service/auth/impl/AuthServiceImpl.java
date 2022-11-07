package com.framework.demo.service.auth.impl;

import com.framework.demo.jwt.JwtTokenProvider;
import com.framework.demo.mapper.auth.AuthMapper;
import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.user.vo.UserVo;
import com.framework.demo.service.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    @Override
    public ResponseEntity<?> tokenRefresh(HttpServletRequest request) {
        
        System.out.println("TokenRefresh-serviceimpl");

        // Request header에서 리프레시 토큰 추출.
        String refreshToken = jwtTokenProvider.resolveToken(request);

        // 리프레시 토큰 유효성 검사
        if (jwtTokenProvider.validateToken(refreshToken)) {

            // Authorities 테이블에서 리프레시 토큰으로 uid 조회.
            String uid = authMapper.findByRefreshToken(refreshToken);

            if(uid != null) {

                // uid를 통해 userInfo 조회
                UserVo userInfo = userMapper.findUserByUid(uid);
                
                String newAccessToken = jwtTokenProvider.createToken(userInfo.getUserEmail(), userInfo.getRole());
                log.info("신규 엑세스 토큰 발급");
                return new ResponseEntity(new MessageResponseDto(newAccessToken, "Access token 재발급"), HttpStatus.OK);

            } else {
                return new ResponseEntity(new MessageResponseDto(1, "사용자를 찾을 수 없습니다."), HttpStatus.OK);
            }
        }
        return new ResponseEntity(new MessageResponseDto(1, "리프레시 토큰 만료 다시 로그인 필요"), HttpStatus.OK);
    }
}
