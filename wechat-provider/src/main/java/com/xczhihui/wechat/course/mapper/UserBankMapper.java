package com.xczhihui.wechat.course.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.model.OfflineCity;
import com.xczhihui.wechat.course.model.UserBank;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface UserBankMapper extends BaseMapper<UserBank> {

	 UserBank selectUserBankByUserIdAndAcctPan(@Param("userId") String userId,@Param("acctPan") String acctPan
			 ,@Param("certId") String certId);

	 void add(@Param("userBank") UserBank userBank);

}