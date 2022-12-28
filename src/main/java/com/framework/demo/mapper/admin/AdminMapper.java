package com.framework.demo.mapper.admin;

import com.framework.demo.model.admin.vo.ManagementServiceVo;
import com.framework.demo.model.auth.vo.OtpVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminMapper {
    List<ManagementServiceVo> getServiceList(String searchOption, String searchValue);




}
