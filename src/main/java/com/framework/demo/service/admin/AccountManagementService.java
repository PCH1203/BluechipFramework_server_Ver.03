package com.framework.demo.service.admin;

import com.framework.demo.domain.BcfUser;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.admin.dto.ModifyAccountDto;
import com.framework.demo.repository.auth.AuthRepository;
import com.framework.demo.repository.user.LoginRepository;
import com.framework.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountManagementService {

    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final AuthRepository authRepository;



    public ResponseEntity<?> modifyUserAccount (ModifyAccountDto modifyAccountDto) {

/*        boolean isMatch = userRepository.findByUid(modifyAccountDto.getUid());

        if(isMatch && modifyAccountDto.getPassword() != null) {
            // Todo : 회원 정보 변경 쿼리 작성
            userRepository.


        return new ResponseEntity(new MessageResponseDto(1,"회원 정보 수정 완료"), HttpStatus.OK);

        } else {
            return new ResponseEntity(new MessageResponseDto(1,"사용자를 찾을 수 없습니다."), HttpStatus.OK);

        }*/

        // 사용자 이름 변경 테스트

        int a = userRepository.modifyName(modifyAccountDto.getUid(), modifyAccountDto.getName());



        return new ResponseEntity(new MessageResponseDto(a,"사용자 정보를 변경하였습니다."), HttpStatus.OK);

    }
    /**
     * 강제 로그아웃 API
     * @param userId
     * @return
     */
    public ResponseEntity<?> forceLogout(String userId) {

        // BcfUser 에서 유저 정보 조회
        BcfUser bcfUser = userRepository.findByUserEmail(userId);

        if(bcfUser != null) {

            String isLogin = loginRepository.findByUid(bcfUser.getUid()).getIsLogin();

            if(isLogin.equals("Y")) {
                // 로그아웃 시간 초기화
                String logoutDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

                // bcfLogin.isLogin = 'N'으로 수정
                loginRepository.modifyIsLogin("N", logoutDt, bcfUser.getUid());
                // bcfAuthorities.refreshToken 삭제
                authRepository.updateRefreshToken(bcfUser.getUid(),"", logoutDt);

                return new ResponseEntity(new MessageResponseDto(bcfUser.getUserEmail(), "로그아웃 완료"), HttpStatus.OK);

            } else {
                return new ResponseEntity(new MessageResponseDto(bcfUser.getUserEmail(), "이미 로그아웃 상태 입니다."), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(new MessageResponseDto(userId, "사용자를 찾을 수 없습니다."), HttpStatus.OK);
        }
    }





}
