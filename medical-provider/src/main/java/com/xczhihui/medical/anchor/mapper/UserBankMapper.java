package com.xczhihui.medical.anchor.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.anchor.vo.UserBank;
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

	 UserBank selectUserBankByUserIdAndAcctPan(@Param("userId") String userId, @Param("acctPan") String acctPan
             , @Param("certId") String certId);

	 void add(@Param("userBank") UserBank userBank);

	List<UserBank> selectUserBankByUserId(@Param("userId") String userId);

	void deleteBankCard(@Param("id") Integer id);

	void updateDefault(@Param("id") Integer id);

	void cancelDefault(@Param("userId") String userId);

	UserBank getDefault(@Param("userId") String userId);
	UserBank getCardById(@Param("id") Integer id);

	int getBankCount(@Param("userId") String userId);
}