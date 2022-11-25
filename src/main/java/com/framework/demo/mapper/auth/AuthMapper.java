package com.framework.demo.mapper.auth;

import com.framework.demo.model.auth.vo.OtpVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {

        void saveRefreshToken(String uid, String refreshToken);
        void modifyRefreshToken(String uid, String refreshToken);
        void removeRefreshToken(String uid);

        String findByRefreshToken(String refreshToken);
        void addOtp(OtpVo otpVo);


}
