package com.framework.demo.service.user;

import com.framework.demo.domain.Otp;
import com.framework.demo.domain.User;
import com.framework.demo.domain.UserService;
import com.framework.demo.enums.HttpStatusCode;
import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.user.dto.JoinDto;
import com.framework.demo.model.user.vo.SignedUpServiceListVo;
import com.framework.demo.repository.user.UserServiceRepository;
import com.framework.demo.repository.user.UserRepository;
import com.framework.demo.repository.util.OtpRepository;
import com.framework.demo.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService2 {

    // JPA Repository
    private final UserRepository userRepository;
    private final UserServiceRepository userServiceRepository;
    private final OtpRepository otpRepository;

    // MYBATIS mapper
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     *  회원가입 API service
     * @param joinDto
     * @return
     */
    public ResponseEntity<?> join(JoinDto joinDto) {
        System.out.println(">>>> 회원가입 서비스 진입");

        // 비밀번호 암호화
        String password = bCryptPasswordEncoder.encode(joinDto.getPassword());
        // UID 생성
        String uid = RandomStringUtils.randomAlphanumeric(20);
        //
        String lockYn = "N";

        User userInfo = User.builder()
                .uid(uid)
                .userEmail(joinDto.getUserEmail())
                .password(password)
                .name(joinDto.getName())
                .phone(joinDto.getPhone())
                .type(joinDto.getType())
                .lockYn(lockYn)
                .role("user") // 계정 권한
                .build();
        userRepository.save(userInfo);

        UserService userService = UserService.builder()
                .uid(uid)
                .serviceId(joinDto.getServiceId())
                .build();
        userServiceRepository.save(userService);
        
        return new ResponseEntity(new MessageResponseDto(HttpStatusCode.OK, userInfo, "회원가입 성공"), HttpStatus.OK);
    }
    

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

    /**
     * 회원가입 전화번호 인증 OTP 전송
     * @param session
     * @param phone
     * @return
     */
    public ResponseEntity<?> joinPhoneOtpSend(HttpSession session, String phone){

        System.out.println(">>>> 회원가입 전화번호 인증 서비스 진입");

        if(userRepository.existsByPhone(phone)) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.DUPLICATE_PHONE, phone, "사용중인 전화번호 입니다."), HttpStatus.OK);
        }
        // OTP 빌더 구성

        String otpPassword = StringUtil.generateNumber(4);

        Otp otp = Otp.builder()
                .otpPassword(otpPassword)
                .sendTo(phone)
                .type("join")
                .expireDt(LocalDateTime.now().plusMinutes(3).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .build();

        otpRepository.save(otp);

        String enOtpPassword = bCryptPasswordEncoder.encode(otpPassword);

        // 세션에 암호화된 otpPassword 저장
        session.setAttribute("enOtpPassword", enOtpPassword);
        // 세션에 암호화된 otpPassword 저장
        session.setAttribute("phone", phone);


        // otp message 생성
        String message = "[블루칩 프레임워크] \r\n 인증코드는 [" + otpPassword + "] 입니다.";

        return new ResponseEntity(new MessageResponseDto(HttpStatusCode.OK, message), HttpStatus.OK);
    }

    public ResponseEntity<?> joinPhoneOtpCheck (HttpSession session, String otpPassword) {

        System.out.println(">>>> JOIN OTP CHECK service 진입");

        // 세션으로 부터 OTP PW와 UID 저장
        String originOtp = (String) session.getAttribute("enOtpPassword");
        // 세션으로 phone 저장
        String phone = (String) session.getAttribute("phone");

        // 생성된 otp 만료시간 조회
        String otpExpireDt = otpRepository.findBySendTo(phone);
        System.out.println("otpExpireDt: " + otpExpireDt);

        if(Long.parseLong(otpExpireDt) < Long.parseLong(otpExpireDt)) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.EXPIRED_OTP,0,"요청 시간을 만료 하였습니다."), HttpStatus.OK);
        }

        // 입력받은 번호와 session에 담긴 암호화 otpPassword와 비교
        if(!bCryptPasswordEncoder.matches(otpPassword, originOtp)) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.MISS_MATCH_OTP,0,"인증번호가 일치 하지 않습니다."), HttpStatus.OK);
        }
        //세션의 모든 속성을 삭제
        session.invalidate();

        return new ResponseEntity(new MessageResponseDto(HttpStatusCode.OK, "전화번호 인증 성공"), HttpStatus.OK);
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

    /**
     * 계정 연동시 서비스 리스트 조회
     * @param session
     * @return
     */
    public ResponseEntity<?> signedUpServiceList (HttpSession session) {

        System.out.println(">>>>> myServiceList API (service)");

        String uid = (String) session.getAttribute("uid");

        List<SignedUpServiceListVo> signedUpList = userMapper.getSignedServiceList(uid);

        return new ResponseEntity(new MessageResponseDto(signedUpList, "가입된 서비스 목록 조회"), HttpStatus.OK);

    }

    /**
     * 계정 + 서비스 연동
     * @param session
     * @param serviceId
     * @return
     */

    public ResponseEntity<?> linkService(HttpSession session, String serviceId) {

        System.out.println(">>>>> linkService API (service)");

        String adminService = "bluechip_service_management";

        String uid = (String) session.getAttribute("uid");

        User user = userRepository.findByUid(uid);

        System.out.println("사용자 권한: " + user.getRole());

        if (serviceId.equals(adminService) && !"admin".equals(user.getRole())) {
            System.out.println("접근 차단.");
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.REQUIRED_ROLE_ADMIN,0,"일반 사용자는 연동할 수 없습니다."), HttpStatus.OK);
        }

        UserService userService = UserService.builder()
                .uid((String) session.getAttribute("uid"))
                .serviceId(serviceId)
                .build();

        userServiceRepository.save(userService);

        return new ResponseEntity(new MessageResponseDto(HttpStatusCode.OK, serviceId, "계정 연동 성공"), HttpStatus.OK);



    }

}
