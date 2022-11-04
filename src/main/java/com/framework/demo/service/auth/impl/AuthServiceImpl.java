package com.framework.demo.service.auth.impl;

import com.framework.demo.jwt.JwtTokenProvider;
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
    @Override
    public ResponseEntity<?> tokenRefresh(HttpServletRequest request) {
        
        System.out.println("어뜨임플 호출");

        // Request header에서 리프레시 토큰 호출
        String refreshToken = jwtTokenProvider.resolveToken(request);

        // 리프레시 토큰 유효성 검사
        if (jwtTokenProvider.validateToken(refreshToken)) {
            // 리프레시 토큰 검사 성공하면
            String tokenUser = jwtTokenProvider.getUserPk(refreshToken);
            // 리프레시 토큰에서 userPK를 꺼내와 DB에서 유저 정보 조회
            UserVo userInfo = userMapper.findUserByUserEmail(tokenUser);

            // db에 저장된 토큰과 리프레쉬 토큰이 일치하면 새로은 Access Token 발행
            boolean isTokenMatch = userInfo.getRefreshToken().equals(refreshToken);
            
            if(isTokenMatch) {
                String newAccessToken = jwtTokenProvider.createToken(userInfo.getUserEmail(), userInfo.getRoles());
                log.info("신규 엑세스 토큰 발급");
                return new ResponseEntity(new MessageResponseDto(newAccessToken, "Access token 발급"), HttpStatus.OK);
            }
        }
        return new ResponseEntity(new MessageResponseDto(1, "리프레시 토큰 만료 다시 로그인 필요"), HttpStatus.OK);
    }
}
