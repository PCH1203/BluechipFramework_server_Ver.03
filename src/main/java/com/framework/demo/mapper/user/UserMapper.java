package com.framework.demo.mapper.user;

import com.framework.demo.model.user.dto.JoinDto;
import com.framework.demo.model.user.vo.UserVo;
import com.framework.demo.model.user.dto.AddSessionDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
        UserVo loadUser(String username);

        UserVo findUserByUsername(String username);
        UserVo findUserByUserEmail(String userEmail);

        void join(JoinDto joinDto);
        void addSession(AddSessionDto addSessionDto);
        void saveRefreshToken(String uid, String refreshToken);
}
