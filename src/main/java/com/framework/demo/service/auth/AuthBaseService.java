package com.framework.demo.service.auth;

import com.framework.demo.domain.Authorities;
import com.framework.demo.domain.Login;
import com.framework.demo.domain.User;
import com.framework.demo.jwt.JwtTokenProvider;
import com.framework.demo.mapper.auth.AuthMapper;
import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.user.vo.LoginVo;
import com.framework.demo.repository.auth.AuthRepository;
import com.framework.demo.repository.user.LoginRepository;
import com.framework.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthBaseService {
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;

    /**
     * 비밀번호 실패 관리
     *
     * @param uid
     * @return
     */
    public ResponseEntity<?> passwordFailManagement(String uid) {

        userRepository.addPasswordFailCnt(uid);

        int passwordFailCnt = userRepository.findByUid(uid).getPasswordFailCnt();

        if (passwordFailCnt >= 10) {
            userRepository.modifyLockYnByUid(uid);
            return new ResponseEntity(new MessageResponseDto(1, "비밀번호 실패 횟수" + passwordFailCnt + "회, 계정 잠김"), HttpStatus.OK);
        }
        return new ResponseEntity(new MessageResponseDto(1, "비밀번호를 확인하세요. 비밀번호 실패 횟수 " + passwordFailCnt + "회."), HttpStatus.OK);

    }



}
