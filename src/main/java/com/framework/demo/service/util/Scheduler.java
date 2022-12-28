package com.framework.demo.service.util;

import com.framework.demo.domain.Authorities;
import com.framework.demo.jwt.JwtTokenProvider;
import com.framework.demo.repository.auth.AuthRepository;
import com.framework.demo.repository.user.LoginRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class Scheduler {

    private final AuthRepository authRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final LoginRepository loginRepository;

    /**
     * 리프레시토큰 만료 계정에 대한 로그아웃 처리.
     */
    @Scheduled(fixedDelay = 1000 * 60 * 10) // 10분마다
    public void logout() {

        List<Authorities> authorities = authRepository.findAll();
        
        for(int i = 0; i < authorities.size(); i++ ) {

            boolean isPass = jwtTokenProvider.validateToken(authorities.get(i).getRefreshToken());

            String nowDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            if(!isPass) {
                String uid = authorities.get(i).getUid();
                
                // 로그인 상태 변경
                loginRepository.modifyIsLogin("N", nowDateTime, uid);
                // 리프레시 토큰 삭제
                authRepository.updateRefreshToken(uid,"", nowDateTime);
               
                log.info("유저{}로그아웃 처리",uid);
            }
        }
    }
}


