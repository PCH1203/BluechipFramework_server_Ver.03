package com.framework.demo.service.user;

import com.framework.demo.domain.User;
import com.framework.demo.domain.UserService;
import com.framework.demo.enums.HttpStatusCode;
import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.user.vo.SignedUpServiceListVo;
import com.framework.demo.repository.user.UserServiceRepository;
import com.framework.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService2 {

    // JPA Repository
    private final UserRepository userRepository;
    private final UserServiceRepository userServiceRepository;

    // MYBATIS mapper
    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * 아이디 중복 검사 API
     * @param userEmail
     * @param serviceId
     * @return
     */
    public ResponseEntity<?> emailCheck(String userEmail, String serviceId) {

        System.out.println(">>>>> 아이디 중복검사 API(service)");

        User user = userRepository.findByUserEmail(userEmail);

        // user 테이블에 없으면
        if(user == null) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.SUCCESS_ID, userEmail, "사용가능한 이메일 입니다."), HttpStatus.OK);
        }
        
        // 다른 서비스 회원일 경우
        if(!userServiceRepository.existsByUidAndServiceId(user.getUid(), serviceId)) {
            
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.DUPLICATE_SERVICE, userEmail, "다른 서비스에서 이용중인 이메일 입니다."), HttpStatus.OK);

        }

        return new ResponseEntity(new MessageResponseDto(HttpStatusCode.DUPLICATE_ID, userEmail, "이미 사용중인 이메일 입니다."), HttpStatus.OK);
    }

    public ResponseEntity<?> interlockLogin(HttpSession session, String userEmail, String password) {

        System.out.println(">>>>> 계정 연동 로그인 API (service)");

        User member = userRepository.findByUserEmail(userEmail);

        if(member == null) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.ID_FAIL,userEmail,"사용자를 찾을 수 없습니다."),HttpStatus.OK);
        }
        // 비밀번호 입력 실패
        if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            // 비밀번호 실패시 실패카운트 증가.
            userRepository.addPasswordFailCnt(member.getUid());
            //
            int failCnt = userRepository.findByUid(member.getUid()).getPasswordFailCnt();
            System.out.println("비밀번호 실패 카운트:" + failCnt );

            if(failCnt >= 10) {
                userRepository.modifyLockYnByUid(member.getUid());
                return new ResponseEntity(new MessageResponseDto(HttpStatusCode.USER_LOCK, null, "비밀번호 실패 횟수" + failCnt +"회, 계정 잠김"), HttpStatus.OK);
            }
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.PASSWORD_FAIL,null, "비밀번호를 확인하세요. 비밀번호 실패 횟수 " + failCnt +"회."), HttpStatus.OK);

            // 비밀번호 입력 성공
        } else {

            // 계정 잠김 상태 확인
            if(userRepository.findByUid(member.getUid()).getLockYn().equals("Y")) {
                return new ResponseEntity(new MessageResponseDto(HttpStatusCode.USER_LOCK, null, "비밀번호 실패 횟수를 초과하여 로그인 할 수 없습니다."), HttpStatus.OK);
            }
            // ID/PW 로그인 성공시 PW 실패 카운트 초기화
            userRepository.resetPasswordFailCnt((member.getUid()));

//            List<UserService> userServiceList = userServiceRepository.findByUid(member.getUid());

            // 세션에 uid 저장
            session.setAttribute("uid", member.getUid());
            session.setAttribute("type", "signUp");

            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.OK,userEmail, "ID/PW 인증 성공"), HttpStatus.OK);

        }
    }

    public ResponseEntity<?> signedUpServiceList (HttpSession session) {

        System.out.println(">>>>> myServiceList API (service)");

        String uid = (String) session.getAttribute("uid");

        List<SignedUpServiceListVo> signedUpList = userMapper.getSignedServiceList(uid);

        return new ResponseEntity(new MessageResponseDto(signedUpList, "가입된 서비스 목록 조회"), HttpStatus.OK);



    }

}
