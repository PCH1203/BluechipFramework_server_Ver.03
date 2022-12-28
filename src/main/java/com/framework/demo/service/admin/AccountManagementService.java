package com.framework.demo.service.admin;

import com.framework.demo.domain.User;
import com.framework.demo.enums.HttpStatusCode;
import com.framework.demo.enums.prameter.admin.AdminEnums;
import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.model.admin.dto.ModifyAccountDto;
import com.framework.demo.model.admin.vo.ManagementUserVo;
import com.framework.demo.repository.auth.AuthRepository;
import com.framework.demo.repository.user.LoginRepository;
import com.framework.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountManagementService {

    // JPA Repository
    private final UserRepository userRepository;
    private final LoginRepository loginRepository;
    private final AuthRepository authRepository;

    // MYBATIS mapper
    private final UserMapper userMapper;

    private AdminEnums adminEnums;



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

//        userRepository.modifyName(modifyAccountDto.getUid(), modifyAccountDto.getName());



        return new ResponseEntity(new MessageResponseDto(1,"사용자 정보를 변경하였습니다."), HttpStatus.OK);

    }
    /**
     * 강제 로그아웃 API
     * @param userId
     * @return
     */
    public ResponseEntity<?> forceLogout(HttpSession session, String userId) {

        // User 에서 유저 정보 조회
        User user = userRepository.findByUserEmail(userId);

        if(user != null) {

//            String isLogin = loginRepository.findByUid(user.getUid()).getIsLogin();
            String isLogin = loginRepository.findByUid(user.getUid()).getStatus();

            if(isLogin.equals("Y")) {
                // 로그아웃 시간 초기화
                String logoutDt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

                // bcfLogin.isLogin = 'N'으로 수정
                loginRepository.modifyIsLogin("N", logoutDt, user.getUid());
                // bcfAuthorities.refreshToken 삭제
                authRepository.updateRefreshToken(user.getUid(),"", logoutDt);
                // session 특정 Attribute 삭제
                session.removeAttribute("uid");
                session.removeAttribute("phone");
//                session.invalidate(); //세션의 모든 속성을 삭제

                return new ResponseEntity(new MessageResponseDto(user.getUserEmail(), "로그아웃 완료"), HttpStatus.OK);

            } else {
                return new ResponseEntity(new MessageResponseDto(user.getUserEmail(), "이미 로그아웃 상태 입니다."), HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(new MessageResponseDto(userId, "사용자를 찾을 수 없습니다."), HttpStatus.OK);
        }
    }

    /**
     * 사용자 목록 조회 API
     * @return
     */
    public ResponseEntity<?> loadUserList(AdminEnums.SearchOption searchOption, String searchValue) {

        System.out.println(">>>>> 사용자 목록조회 API service");
        log.info("SearchOption: " + searchOption );
        log.info("Value: " + searchValue );

        String option = null;

        if(searchOption != null) {

        option = searchOption.getStr();

        }

        List<ManagementUserVo> users = userMapper.findManagementUsers(option, searchValue);

        if(users == null || users.isEmpty()) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.NOT_CONTENT, users, "사용자를 찾을 수 없습니다."), HttpStatus.OK);
        }

        return new ResponseEntity(new MessageResponseDto(HttpStatusCode.OK, users, "사용자 목륵을 조회합니다."), HttpStatus.OK);
    }

    public ResponseEntity<?> modifyUserLockYn(String uid) {
        System.out.println(">>>>> 사용자 계정 정지 상태 변경 API Service");

        User user = userRepository.findByUid(uid);

        if (user == null) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.NOT_FOUND,uid,"사용자를 찾을 수 없습니다."), HttpStatus.OK);
        }

        if(user.getLockYn().equals("Y")) {
            userRepository.modifyLockYnByUid2(uid, "N");
            userRepository.resetPasswordFailCnt(uid);
            return new ResponseEntity(new MessageResponseDto(0,"사용자 계정을 복구 하였습니다."), HttpStatus.OK);
        }

        userRepository.modifyLockYnByUid2(uid, "Y");

        return new ResponseEntity(new MessageResponseDto(0,"사용자 계정을 정지 시켰습니다."), HttpStatus.OK);






    }





}
