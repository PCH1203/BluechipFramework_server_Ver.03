package com.framework.demo.service.user.impl;

import com.framework.demo.mapper.auth.AuthMapper;
import com.framework.demo.model.user.dto.IsLoginDto;
import com.framework.demo.model.user.vo.IsLoginVo;
import com.framework.demo.model.user.vo.UserVo;
import com.framework.demo.jwt.JwtTokenProvider;
import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.user.dto.AddSessionDto;
import com.framework.demo.model.user.dto.JoinDto;
import com.framework.demo.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final JwtTokenProvider jwtTokenPorvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<?> login(HttpServletRequest request, String userEmail, String password) {

        // 1. 회원 가입 여부 확인
        UserVo member = userMapper.findUserByUserEmail(userEmail);

        if(member != null) {

//            boolean isMatches = bCryptPasswordEncoder.matches(password, member.getPassword());

            // 비밀번호 확인
            if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {

                return new ResponseEntity(new MessageResponseDto(1, "비밀번호를 확인하세요."), HttpStatus.OK);

            } else {

                // 로그인 여부 확인
                IsLoginVo isLogin = userMapper.findIsLoginByUid(member.getUid());
                
                if(isLogin != null) {

                    if(isLogin.getIsLogin().equals("Y")) {

                        return new ResponseEntity(new MessageResponseDto(1, "이미 로그인된 계정 입니다."), HttpStatus.OK);

                    }else {

                        // Access token 발급
                        member.setAccessToken(jwtTokenPorvider.createToken(member.getUsername(), member.getRole()));
                        // Refresh token 발급
                        member.setRefreshToken(jwtTokenPorvider.createRefreshToken(member.getUsername(), member.getRole()));
                        // Authorities 테이블 RefreshToken 저장.
                        authMapper.modifyRefreshToken(member.getUid(), member.getRefreshToken());

                        String loginStatus = "Y";
                        
                        // IsLogin 상태 변경
                        userMapper.modifyIsLogin(isLogin.getUid(), loginStatus);
                        
                        return new ResponseEntity(new MessageResponseDto(member, "로그인 성공"), HttpStatus.OK);
                    }


                } else {

                    // Access token 발급
                    member.setAccessToken(jwtTokenPorvider.createToken(member.getUsername(), member.getRole()));
                    // Refresh token 발급
                    member.setRefreshToken(jwtTokenPorvider.createRefreshToken(member.getUsername(), member.getRole()));
                    // User 테이블 RefreshToken 저장.
                    authMapper.saveRefreshToken(member.getUid(), member.getRefreshToken());

                    IsLoginDto isLoginDto = IsLoginDto.builder()
                                    .uid(member.getUid())
                                    .userId(member.getUserEmail())
                                    .type("web")
                                    .isLogin("Y").build();

                    // IsLogin 유저 정보 변경
                    userMapper.saveIsLogin(isLoginDto);

                    return new ResponseEntity(new MessageResponseDto(member, "로그인 성공"), HttpStatus.OK);
                }
            }
        }else {
            return new ResponseEntity(new MessageResponseDto(1, "사용자를 찾을 수 없습니다."), HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<?> join(JoinDto joinDto) {
        log.info("포탈 회원 가입 서비스임플");

        String rawPassword = joinDto.getPassword();
        String uid = null;

        // email 중복 검사
        if(userMapper.findUserByUserEmail(joinDto.getUserEmail()) == null && userMapper.findUserByPhone(joinDto.getPhone()) == null) {

            uid = RandomStringUtils.randomAlphanumeric(20);
            String password = bCryptPasswordEncoder.encode(joinDto.getPassword());
            joinDto.setPassword(password);
            joinDto.setUid(uid);

            userMapper.join(joinDto);


        } else if(userMapper.findUserByUserEmail(joinDto.getUserEmail()) != null) {
            return new ResponseEntity(new MessageResponseDto(1, "사용중인 email 입니다."), HttpStatus.OK);

        } else if(userMapper.findUserByPhone(joinDto.getPhone()) != null) {
            return new ResponseEntity(new MessageResponseDto(1, "사용중인 전화번호 입니다."), HttpStatus.OK);
        }

        joinDto.setPassword(rawPassword);

        return new ResponseEntity(new MessageResponseDto(joinDto, "회원가입 성공"), HttpStatus.OK);
    }

    public void addSession(HttpServletRequest request, UserVo member) {

        AddSessionDto addSessionDto = AddSessionDto.builder()
                .uid(member.getUid())
                .accessIp(request.getRemoteAddr())
                .sessionId(RandomStringUtils.randomAlphanumeric(20))
                .status("Y")
                .build();
        userMapper.addSession(addSessionDto);
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {

        // 1. request로부터 Access 토큰을 가져온다.
        String accessToken = jwtTokenPorvider.resolveToken(request);

        //2. Access 토큰 유효성 검사.
        if (jwtTokenPorvider.validateToken(accessToken)) {
            // 2-1 Access Token 으로 부터 userPk 조회
            String tokenUserId = jwtTokenPorvider.getUserPk(accessToken);
            // 2-2 userPk를 사용하여 userInfo 조회
            UserVo userInfo = userMapper.findUserByUserEmail(tokenUserId);

            String loginStatus = "N";

            // User Table 리프레시 토큰 삭제
            authMapper.removeRefreshToken(userInfo.getUid());
            // Login Status 로그인 상태 변경
            userMapper.modifyIsLogin(userInfo.getUid(), loginStatus);

            return new ResponseEntity(new MessageResponseDto(tokenUserId, "로그아웃 완료."), HttpStatus.OK);

        } else {

        return new ResponseEntity(new MessageResponseDto(1, "Access Token 만료."), HttpStatus.OK);
        }

    }

    @Override
    public ResponseEntity<?> forceLogout(String uid) {

            UserVo user = userMapper.findUserByUid(uid);

            if(user != null) {

                String loginStatus = "N";
                userMapper.modifyIsLogin(uid, loginStatus);
                authMapper.removeRefreshToken(uid);

                return new ResponseEntity(new MessageResponseDto(user, "강제로그인 완료"), HttpStatus.OK);

            } else {
                return new ResponseEntity(new MessageResponseDto(uid, "해당 사용자를 찾을 수 없습니다."), HttpStatus.OK);
            }
    }
}
