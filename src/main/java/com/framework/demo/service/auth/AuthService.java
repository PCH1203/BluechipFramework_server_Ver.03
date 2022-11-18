package com.framework.demo.service.auth;

import com.framework.demo.domain.Authorities;
import com.framework.demo.domain.Login;
import com.framework.demo.domain.Session;
import com.framework.demo.domain.User;
import com.framework.demo.jwt.JwtTokenProvider;
import com.framework.demo.mapper.auth.AuthMapper;
import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.user.vo.LoginVo;
import com.framework.demo.model.user.vo.UserVo;
import com.framework.demo.repository.auth.AuthRepository;
import com.framework.demo.repository.session.SessionRepository;
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
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserMapper userMapper;
    private final AuthMapper authMapper;
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final AuthRepository authRepository;
    private final AuthBaseService authBaseService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final SessionRepository sessionRepository;



    /**
     * 로그인 API 입니다.
     * @param request
     * @param userEmail
     * @param password
     * @return
     */
    public ResponseEntity<?> login(HttpServletRequest request, String userEmail, String password)  {

        // 1. 회원 가입 여부 확인
        User member = userRepository.findByUserEmail(userEmail);

        String loginDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

        LoginVo loginInfo = new LoginVo();


        if (member != null) {

            // 비밀번호 입력 실패
            if (!bCryptPasswordEncoder.matches(password, member.getPassword())) {
                // 비밀번호 실패시 실패카운트 증가.
                userRepository.addPasswordFailCnt(member.getUid());
                //
                int failCnt = userRepository.findByUid(member.getUid()).getPasswordFailCnt();

                if(failCnt >= 10) {
                    userRepository.modifyLockYnByUid(member.getUid());
                    return new ResponseEntity(new MessageResponseDto(1, "비밀번호 실패 횟수" + failCnt +"회, 계정 잠김"), HttpStatus.OK);
                }
                return new ResponseEntity(new MessageResponseDto(1, "비밀번호를 확인하세요. 비밀번호 실패 횟수 " + failCnt +"회."), HttpStatus.OK);

                // 비밀번호 입력 성공
            } else {
        
                // 계정 잠김 상태 확인
                if(userRepository.findByUid(member.getUid()).getLockYns().equals("Y")) {
                    return new ResponseEntity(new MessageResponseDto(1, "비밀번호 실패 횟수를 초과하여 로그인 할 수 없습니다."), HttpStatus.OK);
                }

                // 로그인 상태 확인
                Login loginStatus = loginRepository.findByUid(member.getUid());

                if (loginStatus != null && loginStatus.getIsLogin().equals("Y")) {
                    return new ResponseEntity(new MessageResponseDto(1, "이미 로그인된 계정 입니다."), HttpStatus.OK);

                } else if (loginStatus != null && loginStatus.getIsLogin().equals("N")) {
                    // Access token 발급
                    loginInfo.setAccessToken(jwtTokenProvider.createToken(member.getUserEmail(), member.getRoleId()));
//                    loginInfo.setAccessToken(jwtTokenProvider.createToken(member.getUserEmail(), member.getRole()));
                    // Refresh token 발급
                    loginInfo.setRefreshToken(jwtTokenProvider.createRefreshToken(member.getUserEmail(), member.getRoleId()));
//                    loginInfo.setRefreshToken(jwtTokenProvider.createRefreshToken(member.getUserEmail(), member.getRole()));
                    loginInfo.setUserId(member.getUserEmail());
                    loginInfo.setName(member.getName());
                    loginInfo.setPhone(member.getPhone());


                    Authorities authorities = Authorities.builder()
                            .uid(member.getUid())
                            .refreshToken(loginInfo.getRefreshToken())
                            .createDt(loginDt)
                            .build();

                    // RefreshToken 저장.
                    authRepository.save(authorities);
                    // bcf_login.isLogin 변경.
                    loginRepository.modifyIsLogin("Y", loginDt ,loginStatus.getUid());
                    // 로그인 실패횟수 초기화.
                    userRepository.resetPasswordFailCnt(member.getUid());

                    return new ResponseEntity(new MessageResponseDto(loginInfo, "로그인 성공"), HttpStatus.OK);

                } else {
                    // Access token 발급
                    loginInfo.setAccessToken(jwtTokenProvider.createToken(member.getUserEmail(), member.getRoleId()));
//                    loginInfo.setAccessToken(jwtTokenProvider.createToken(member.getUserEmail(), member.getRole()));
                    // Refresh token 발급
                    loginInfo.setRefreshToken(jwtTokenProvider.createRefreshToken(member.getUserEmail(), member.getRoleId()));
                    loginInfo.setUserId(member.getUserEmail());
                    loginInfo.setName(member.getName());
                    loginInfo.setPhone(member.getPhone());

                    Authorities authorities = Authorities.builder()
                            .uid(member.getUid())
                            .refreshToken(loginInfo.getRefreshToken())
                            .createDt(loginDt)
                            .build();

                    Login login = Login.builder()
                            .uid(member.getUid())
                            .isLogin("Y")
//                            .createDt(loginDt)
                            .build();

                    // RefreshToken 저장.
                    authRepository.save(authorities);
                    // Login 상태 저장.
                    loginRepository.save(login);

                    return new ResponseEntity(new MessageResponseDto(loginInfo, "로그인 성공"), HttpStatus.OK);
                }
            }
        } else {
            return new ResponseEntity(new MessageResponseDto(userEmail, "사용자를 찾을 수 없습니다."), HttpStatus.OK);
        }
    }

    /**
     * 로그아웃 API
     * @param request
     * @return
     */
    public ResponseEntity<?> logout(HttpServletRequest request) {

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
                
                String newAccessToken = jwtTokenProvider.createToken(userInfo.getUserEmail(), userInfo.getRoleId());
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
        if(userRepository.findByUid(member.getUid()).getLockYns().equals("Y")) {
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
