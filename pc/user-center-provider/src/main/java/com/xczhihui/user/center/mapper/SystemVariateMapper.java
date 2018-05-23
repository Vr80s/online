package com.xczhihui.user.center.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.user.center.model.SystemVariate;

public interface SystemVariateMapper extends BaseMapper<SystemVariate> {

    @Select("select t1.* from system_variate t1,system_variate t2 where t1.parent_id=t2.id and t2.name= 'message_provider'")
    List<SystemVariate> listMessageProviderByName();
}
