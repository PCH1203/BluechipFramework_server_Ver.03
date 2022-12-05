package com.framework.demo.service.user;

import com.framework.demo.enums.HttpStatusCode;
import com.framework.demo.enums.prameter.auth.ServiceEnums;
import com.framework.demo.model.MessageResponseDto;
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

    private final UserRepository userRepository;

//    public ResponseEntity<?> emailCheck(String userEmail, ServiceEnums.ServiceId serviceId) {
    public ResponseEntity<?> emailCheck(String userEmail, String serviceId) {

        System.out.println(">>>>> 아이디 중복검사 API(service)");

//        String joinServiceId = serviceId.getStr();

        if(userRepository.existsByServiceIdAndUserEmail(serviceId, userEmail)) {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.DUPLICATE_ID, userEmail,"사용중인 이메일 입니다."), HttpStatus.OK);

        } else {
            return new ResponseEntity(new MessageResponseDto(HttpStatusCode.SUCCESS_ID, userEmail, "사용가능한 이메일 입니다."), HttpStatus.OK);
        }
    }
}
