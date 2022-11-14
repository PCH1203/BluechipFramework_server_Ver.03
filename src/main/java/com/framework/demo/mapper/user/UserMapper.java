package com.framework.demo.mapper.user;

import com.framework.demo.model.user.dto.AddSessionDto;
import com.framework.demo.model.user.dto.JoinDto;
import com.framework.demo.model.user.dto.ModifyMyAccountDto;
import com.framework.demo.model.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
        UserVo findUserByUid(String uid);
        UserVo findUserByPhone(String phone);
        UserVo findUserByUsername(String username);
        UserVo findUserByUserEmail(String userEmail);
        void join(JoinDto joinDto);
        void addSession(AddSessionDto addSessionDto);
        void modifyIsLogin(String uid, String loginStatus);
        int modifyMyAccount(ModifyMyAccountDto modifyMyAccountDto);

}
