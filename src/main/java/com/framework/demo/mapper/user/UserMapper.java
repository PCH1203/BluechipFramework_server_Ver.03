package com.framework.demo.mapper.user;

import com.framework.demo.model.admin.vo.ManagementUserVo;
import com.framework.demo.model.user.dto.AddSessionDto;
import com.framework.demo.model.user.dto.JoinDto;
import com.framework.demo.model.user.dto.ModifyMyAccountDto;
import com.framework.demo.model.user.vo.SignedUpServiceListVo;
import com.framework.demo.model.user.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {


        UserVo findUserByUid(String uid);
        UserVo findUserByPhone(String phone);
        UserVo findUserByUsername(String username);
        UserVo findUserByUserEmail(String userEmail);
        UserVo findUserByUserEmailAndServiceId(JoinDto joinDto);
        void join(JoinDto joinDto);
        void addSession(AddSessionDto addSessionDto);
        void modifyIsLogin(String uid, String loginStatus);
        int modifyMyAccount(ModifyMyAccountDto modifyMyAccountDto);

        List<ManagementUserVo> findManagementUsers(String option, String searchValue);

        List<SignedUpServiceListVo> getSignedServiceList(String uid);

}
