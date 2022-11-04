package com.framework.demo.service.security.impl;

import com.framework.demo.mapper.user.UserMapper;
import com.framework.demo.model.user.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("userDetails.loadUserByUsername");
        UserVo userVo = userMapper.findUserByUsername(username);

        System.out.println("userVo:"+ userVo.toString() );

        return User.builder()
                .username(userVo.getUserEmail())
                .password(userVo.getPassword())
                .roles("USER")
                .build();


    }
}
