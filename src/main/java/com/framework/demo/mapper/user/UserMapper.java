package com.framework.demo.mapper.user;

import com.framework.demo.model.user.dto.IsLoginDto;
import com.framework.demo.model.user.dto.JoinDto;
import com.framework.demo.model.user.vo.IsLoginVo;
import com.framework.demo.model.user.vo.UserVo;
import com.framework.demo.model.user.dto.AddSessionDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
        UserVo findUserByUid(String uid);
        UserVo findUserByPhone(String phone);
        UserVo findUserByUsername(String username);
        UserVo findUserByUserEmail(String userEmail);

        void join(JoinDto joinDto);
        void addSession(AddSessionDto addSessionDto);

        IsLoginVo findIsLoginByUid(String uid);

        void modifyIsLogin(String uid, String loginStatus);
        void saveIsLogin(IsLoginDto isLoginDto);
}
