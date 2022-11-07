package com.framework.demo.service.user;

import com.framework.demo.model.user.dto.JoinDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    ResponseEntity<?> join(JoinDto joinDto);
    public ResponseEntity<?> login (HttpServletRequest request, String userEmail, String password);
    public ResponseEntity<?> logout (HttpServletRequest request);

}
