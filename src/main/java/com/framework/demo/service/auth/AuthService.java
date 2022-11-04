package com.framework.demo.service.auth;

import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

    ResponseEntity<?> tokenRefresh(HttpServletRequest request);
}

