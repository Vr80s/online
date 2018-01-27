package com.xczhihui.wechat.course.service;

import com.xczhihui.wechat.course.model.UserBank;

import java.util.List;

public interface IUserBankService {

	  public UserBank selectUserBankByUserIdAndAcctPan(String userId,String acctPan,String certId);

	  void addUserBank(UserBank userBank);

	  public List<UserBank> selectUserBankByUserId(String userId);

}
