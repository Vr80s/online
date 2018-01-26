package com.xczhihui.wechat.course.service;

import com.xczhihui.wechat.course.model.UserBank;

public interface IUserBankService {

	  public UserBank selectUserBankByUserIdAndAcctPan(String userId,String acctPan,String certId);

	  void addUserBank(UserBank userBank);

}
