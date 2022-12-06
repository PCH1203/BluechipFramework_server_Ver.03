package com.framework.demo.service.user;

import com.framework.demo.domain.User;
import com.framework.demo.enums.HttpStatusCode;
import com.framework.demo.model.MessageResponseDto;
import com.framework.demo.repository.admin.UserServiceRepository;
import com.framework.demo.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService2 {

    // JPA Repository
    private final UserRepository userRepository;
    private final UserServiceRepository userServiceRepository;

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

}
