package com.framework.demo.service.user.impl;

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
    private final JwtTokenProvider jwtTokenPorvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<?> login(HttpServletRequest request, String userEmail, String password) {

        System.out.println("Login Service 호출");
        System.out.println("email: " + userEmail);
        System.out.println("password: " + password);
        System.out.println("request: " + request.getRemoteAddr());

        // 1. 요청 받은 userEmail DB 조회 
        UserVo member = userMapper.findUserByUserEmail(userEmail);
        
        if(member != null) {
            
        //2. userEmail을 확인 했다면 비밀번호 유효성 검사
            boolean isMatches = bCryptPasswordEncoder.matches(password, member.getPassword());

            if (!isMatches) {
                return new ResponseEntity(new MessageResponseDto(1, "비밀번호를 확인하세요."), HttpStatus.OK);
            } else {
                member.setAccessToken(jwtTokenPorvider.createToken(member.getUsername(), member.getRoles()));
                member.setRefreshToken(jwtTokenPorvider.createRefreshToken(member.getUsername(), member.getRoles()));
                log.info("token 발급");
                userMapper.saveRefreshToken(member.getUid(),member.getRefreshToken());
//                addSession(request, member);
            }
        } else {
            return new ResponseEntity(new MessageResponseDto(1, "사용자를 찾을 수 없습니다."), HttpStatus.OK);
        }

        return new ResponseEntity(new MessageResponseDto(member, "로그인 성공"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> join(JoinDto joinDto) {
        log.info("포탈 회원 가입 서비스임플");

        String rawPassword = joinDto.getPassword();
        String uid = null;

        // email 중복 검사
        if(userMapper.findUserByUserEmail(joinDto.getUserEmail()) == null) {

            uid = RandomStringUtils.randomAlphanumeric(20);
            String password = bCryptPasswordEncoder.encode(joinDto.getPassword());
            joinDto.setPassword(password);
            joinDto.setUid(uid);

            userMapper.join(joinDto);


        } else {
            return new ResponseEntity(new MessageResponseDto(1, "사용중인 email 입니다."), HttpStatus.OK);
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
}
