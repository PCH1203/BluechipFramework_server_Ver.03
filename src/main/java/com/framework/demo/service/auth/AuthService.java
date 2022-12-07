package com.framework.demo.service.auth;

import com.framework.demo.domain.*;
import com.framework.demo.enums.HttpStatusCode;
import com.framework.demo.jwt.JwtTokenProvider;
import com.framework.demo.mapper.auth.AuthMapper;
import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.auth.vo.OtpVo;
import com.framework.demo.model.user.vo.LoginVo;
import com.framework.demo.model.user.vo.UserVo;
import com.framework.demo.repository.user.UserServiceRepository;
import com.framework.demo.repository.auth.AuthRepository;
import com.framework.demo.repository.session.SessionRepository;
import com.framework.demo.repository.user.LoginRepository;
import com.framework.demo.repository.user.UserRepository;
import com.framework.demo.repository.util.OtpRepository;
import com.framework.demo.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    // JPA Repository
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final AuthRepository authRepository;
    private final SessionRepository sessionRepository;
    private final OtpRepository otpRepository;

    private final UserServiceRepository userServiceRepository;

    // MYBATIS mapper
    private final UserMapper userMapper;
    private final AuthMapper authMapper;

    // SERVICE
    private final AuthBaseService authBaseService;

    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();




    /**
     * 로그인 API 입니다.
     * @param request
     * @param userEmail
     * @param password
     * @return
     */
    public ResponseEntity<?> appLoginStep1(HttpServletRequest request, HttpSession session, String serviceId, String userEmail, String password)  {

        System.out.println(">>>>> ID/PW 로그인 API (service)");

        // 1. 해당 서비스에 회원 여부 확인.

        User member = userRepository.findByUserEmail(userEmail);

        boolean isExist = userServiceRepository.existsByUidAndServiceId(member.getUid(), serviceId);

        if(member == null || !isExist) {
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

                // 로그인 상태 확인
                Login loginStatus = loginRepository.findByUid(member.getUid());

                // 로그인 한적이 있고 이미 로그인 상태
                if (loginStatus != null && loginStatus.getIsLogin().equals("Y")) {
                    return new ResponseEntity(new MessageResponseDto(HttpStatusCode.ALREADY_LOGIN, userEmail,  "이미 로그인된 계정 입니다."), HttpStatus.OK);

                } else {
                    
                    //세션에 uid,phone 저장
                    session.setAttribute("uid", member.getUid());
                    session.setAttribute("serviceId", serviceId);
                    session.setAttribute("type", "login");
//                    session.setAttribute("phone", member.getPhone());

                    return new ResponseEntity(new MessageResponseDto(member,"ID/PW 로그인 성공"), HttpStatus.OK);
                }
            }

    }

    /**
     *  로그인 STEP_2 OTP 전송
     * @param request
     * @param session
     * @param sendTo
     * @return
     */
    public ResponseEntity<?> loginSendOtp(HttpServletRequest request, HttpSession session, String sendTo) {

        /**
         * Todo
         * 화면단에서 인증번호 발송 후 버튼 비활성화 필요
         */

        System.out.println(">>>>> loginOtp API");
        System.out.println(">>>> session.uid: " + session.getAttribute("uid"));
        System.out.println(">>>> session.phone: " + session.getAttribute("phone"));


        //login_step_1 으로 부터 넘겨받은 uid 와 OTP 전송 타입
        String uid = (String) session.getAttribute("uid");
        String type = (String) session.getAttribute("type");

        if(uid == null) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.REQUIRED_LOGIN_STEP_1,null, "로그인 정보가 존재하지 않습니다."),HttpStatus.OK);
        }

        User user = userRepository.findByUid(uid);

        if(!user.getPhone().equals(sendTo)) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.MISS_MATCH_SEND_TO, sendTo,"전화번호가 일치하지 않습니다."), HttpStatus.OK);
        }
        // otp 생성 및 저장.
        System.out.println("otp 생성");
        String otpPassword = StringUtil.generateNumber(4);
        OtpVo otpVo = new OtpVo();
        otpVo.setOtpPassword(otpPassword);
        otpVo.setCreator(uid);
        otpVo.setSendTo(sendTo);
        otpVo.setType(type);
        authMapper.addOtp(otpVo);

        String enOtpPassword = bCryptPasswordEncoder.encode(otpPassword);

        // 세션에 암호화된 otpPassword 저장
        session.setAttribute("enOtpPassword", enOtpPassword);

        // otp message 생성
        String message = "[블루칩 프레임워크] \r\n 인증코드는 [" + otpPassword + "] 입니다.";

        return new ResponseEntity(new MessageResponseDto(otpPassword, message), HttpStatus.OK);
    }

    /**
     * 로그인 STEP_3 OTP 인증
     * @param request
     * @param session
     * @param otpPassword
     * @return
     */
    public ResponseEntity<?> loginCheckOtp(HttpServletRequest request, HttpSession session, String otpPassword) {

        System.out.println(">>>>> OTP 인증 API (service)");
        
        // 현재 날짜 생성
        String nowDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        // 세션으로 부터 serviceId와 OTP 타입 저장 
        String serviceId = (String) session.getAttribute("serviceId");
        String type = (String) session.getAttribute("type");
        System.out.println(">>>>>>>> type: " + type);

        // 세션으로 부터 OTP PW와 UID 저장
        String originOtp = (String) session.getAttribute("enOtpPassword");
        String uid = (String) session.getAttribute("uid");

        // 생성된 otp 만료시간 조회
        String otgExpireDt = otpRepository.findByCreator(uid);
        System.out.println("otgExpireDt: " + otgExpireDt);

        // 생성된 otp 만료 시간과 현재시간 비교
        if(Long.parseLong(otgExpireDt) < Long.parseLong(nowDateTime)) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.EXPIRED_OTP,0,"요청 시간을 만료 하였습니다."), HttpStatus.OK);
        }

        // 입력받은 번호와 session에 담긴 암호화 otpPassword와 비교
        if(!bCryptPasswordEncoder.matches(otpPassword, originOtp)) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.MISS_MATCH_OTP,0,"인증번호가 일치 하지 않습니다."), HttpStatus.OK);
        }

        // 유저 정보 조회
        User member = userRepository.findByUid(uid);

        if(type == "signUp") {
            System.out.println(">>>>> type: " + type);
            return new ResponseEntity(new MessageResponseDto(member.getUserEmail(),"계정 연동을 위한 로그인 인증 성공"), HttpStatus.OK);
        }

        LoginVo loginInfo = new LoginVo();

        // 토큰 생성 및 발급

        // Access token 발급 (userEmail, serviceId 매개변수로 전달)
        loginInfo.setAccessToken(jwtTokenProvider.createToken(member.getUserEmail(), serviceId));
//        loginInfo.setAccessToken(jwtTokenProvider.createToken(member.getUserEmail(), member.getRoleId()));
        // Refresh token 발급
        loginInfo.setRefreshToken(jwtTokenProvider.createRefreshToken(member.getUserEmail(), member.getRoleId()));
        loginInfo.setUserId(member.getUserEmail());
        loginInfo.setName(member.getName());
        loginInfo.setPhone(member.getPhone());

        Authorities authorities = Authorities. builder()
                .uid(member.getUid())
                .refreshToken(loginInfo.getRefreshToken())
                .createDt(nowDateTime)
                .build();
        // RefreshToken 저장.
        authRepository.save(authorities);

        Login loginStatus = loginRepository.findByUid(uid);

        if(loginStatus == null) {

            Login login = Login.builder()
                    .uid(member.getUid())
                    .isLogin("Y")
                    .createDt(nowDateTime)
                    .build();
            loginRepository.save(login);
        }

        if(loginStatus != null && loginStatus.getIsLogin().equals("N")) {
            // isLogin = 'Y'으로 수정
            loginRepository.modifyIsLogin("Y", nowDateTime, uid);
        }

        return new ResponseEntity(new MessageResponseDto(loginInfo, "로그인 성공"),HttpStatus.OK);

    }

    /**
     * 로그아웃 API
     * @param request
     * @return
     */
    public ResponseEntity<?> logout(HttpServletRequest request, HttpSession session) {

        System.out.println(">>>>> 로그아웃 API (service)");
        System.out.println(">>>>> accessToken: " + request.getHeader("Authorization"));

        // 1. request로부터 Access 토큰을 가져온다.
        String accessToken = jwtTokenProvider.resolveToken(request);

        //2. Access 토큰 유효성 검사.
        if (jwtTokenProvider.validateToken(accessToken)) {
            // 2-1 Access Token 으로 부터 userPk 조회
            String tokenUserId = jwtTokenProvider.getUserPk(accessToken);
            // 2-2 userPk를 사용하여 userInfo 조회
            UserVo userInfo = userMapper.findUserByUserEmail(tokenUserId);

            // 로그아웃 시간 초기화
            String logoutDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            // bcfLogin.isLogin = 'N'으로 수정
            loginRepository.modifyIsLogin("N", logoutDt, userInfo.getUid());
            // bcfAuthorities.refreshToken 삭제
            authRepository.updateRefreshToken(userInfo.getUid(),"", logoutDt);
            //세션의 모든 속성을 삭제
            session.invalidate();

            return new ResponseEntity(new MessageResponseDto(tokenUserId, "로그아웃 완료."), HttpStatus.OK);

        } else {

            return new ResponseEntity(new MessageResponseDto(1, "Access Token 만료."), HttpStatus.OK);
        }

    }

    /**
     * 토큰 리프레쉬 API
     * @param request
     * @return
     */
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
                
                // 토큰 생성시 userId(userEmail)과 serviceId를 파라미터로 전달해 준다.
                String newAccessToken = jwtTokenProvider.createToken(userInfo.getUserEmail(), userInfo.getServiceId());
//                String newAccessToken = jwtTokenProvider.createToken(userInfo.getUserEmail(), userInfo.getRoleId());
                log.info("신규 엑세스 토큰 발급");
                return new ResponseEntity(new MessageResponseDto(newAccessToken, "Access token 재발급"), HttpStatus.OK);

            } else {
                return new ResponseEntity(new MessageResponseDto(1, "사용자를 찾을 수 없습니다."), HttpStatus.OK);
            }
        }
        return new ResponseEntity(new MessageResponseDto(1, "리프레시 토큰 만료 다시 로그인 필요"), HttpStatus.OK);
    }

    /**
     * Session 방식 로그인 (web)
     * @param request
     * @param userId
     * @param password
     * @return
     */
    public ResponseEntity<?> webLogin(HttpServletRequest request, String userId, String password) {

        User member = userRepository.findByUserEmail(userId);
        String loginDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        if(member == null) {
            return new ResponseEntity(new MessageResponseDto(0,"사용자를 찾을 수 없습니다."), HttpStatus.OK);
        }

        if(!bCryptPasswordEncoder.matches(password, member.getPassword())) {
            return authBaseService.passwordFailManagement(member.getUid());
        }
        // 계정 잠김 상태 확인
        if(userRepository.findByUid(member.getUid()).getLockYn().equals("Y")) {
            return new ResponseEntity(new MessageResponseDto(1, "비밀번호 실패 횟수를 초과하여 로그인 할 수 없습니다."), HttpStatus.OK);
        }
        // 로그인 상태 확인
        Login loginStatus = loginRepository.findByUid(member.getUid());

        if (loginStatus != null && loginStatus.getIsLogin().equals("Y")) {
            return new ResponseEntity(new MessageResponseDto(0, "이미 로그인된 계정 입니다."), HttpStatus.OK);
        }

        Session session = Session.builder()
                .uid(member.getUid())
                .sessionId(request.getSession().getId())
                .accessIp(request.getRemoteAddr())
                .status("Y")
                .createDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")))
                .build();
        //세션db에 저장
        sessionRepository.save(session);
        request.setAttribute("userId", member.getUserEmail());
        request.setAttribute("password", member.getPassword());

        Map<String,String> resultMap = new HashMap<>();

        resultMap.put("UserId", member.getUserEmail());
        resultMap.put("SessionId", session.getSessionId());
        resultMap.put("AccessIp", session.getAccessIp());

        return new ResponseEntity(new MessageResponseDto(resultMap, "세션로그인 테스트"), HttpStatus.OK);
    }





}
