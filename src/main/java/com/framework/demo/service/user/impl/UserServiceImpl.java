package com.framework.demo.service.user.impl;

import com.framework.demo.domain.BcfUser;
import com.framework.demo.enums.HttpStatusCode;
import com.framework.demo.jwt.JwtTokenProvider;
import com.framework.demo.mapper.auth.AuthMapper;
import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.user.dto.AddSessionDto;
import com.framework.demo.model.user.dto.JoinDto;
import com.framework.demo.model.user.dto.ModifyMyAccountDto;
import com.framework.demo.model.user.vo.UserVo;
import com.framework.demo.repository.auth.AuthRepository;
import com.framework.demo.repository.user.LoginRepository;
import com.framework.demo.repository.user.UserRepository;
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
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * 회원가입 API
     * @param joinDto
     * @return
     */
    @Override
    public ResponseEntity<?> join(JoinDto joinDto) {

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
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.BAD_REQUEST,1, "사용중인 전화번호 입니다."), HttpStatus.OK);
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

    /**
     * 나의 회원 정보 조회 API
     * @param request
     * @return
     */
    @Override
    public ResponseEntity<?> findMyAccount(HttpServletRequest request) {

        System.out.println(">>>>> findMyAccountImpl");

        // request header의 userPk로 유저 정보 조회
        BcfUser userInfo = jwtTokenProvider.findUserInfoByRequest(request);

        return new ResponseEntity(new MessageResponseDto(userInfo, "나의 계정 정보를 조회 합니다."), HttpStatus.OK);

    }

    /**
     * 나의 회원 정보 수정 API
     * @param request
     * @param modifyMyAccountDto
     * @return
     */
    @Override
    public ResponseEntity<?> modifyMyAccount(HttpServletRequest request, ModifyMyAccountDto modifyMyAccountDto) {

        // header의 userPk로 유저 정보 조회
        BcfUser userInfo = jwtTokenProvider.findUserInfoByRequest(request);
        
        if(userInfo != null) { // 유저 정보 조회에 성공하면
            // modifyMyAccountDto에 uid 넣고
            modifyMyAccountDto.setUid(userInfo.getUid());
            int modifyCount = userMapper.modifyMyAccount(modifyMyAccountDto);
            
            if(modifyCount > 0 ) {
                return new ResponseEntity(new MessageResponseDto(modifyMyAccountDto,"유저 정보 변경 성공"), HttpStatus.OK);
            } else {
                return new ResponseEntity(new MessageResponseDto(0,"유저 정보 변경 실패"), HttpStatus.OK);
            }
            
        } else {
            return new ResponseEntity(new MessageResponseDto(0,"해당 유저를 찾을 수 없습니다."), HttpStatus.OK);
        }

    }
}
