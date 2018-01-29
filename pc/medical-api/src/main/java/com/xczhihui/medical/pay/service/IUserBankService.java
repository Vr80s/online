package com.xczhihui.medical.pay.service;

import com.xczhihui.medical.pay.model.UserBank;

import java.util.List;

public interface IUserBankService {

	  public UserBank selectUserBankByUserIdAndAcctPan(String userId, String acctPan, String certId);

	  void addUserBank(UserBank userBank);

	  public List<UserBank> selectUserBankByUserId(String userId);

}
