package com.framework.demo.service.user;

import com.framework.demo.model.user.dto.JoinDto;
import com.framework.demo.model.user.dto.ModifyMyAccountDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    ResponseEntity<?> join(JoinDto joinDto);
    public ResponseEntity<?> findMyAccount (HttpServletRequest request);
    public ResponseEntity<?> modifyMyAccount (HttpServletRequest request, ModifyMyAccountDto modifyMyAccountDto);


}
