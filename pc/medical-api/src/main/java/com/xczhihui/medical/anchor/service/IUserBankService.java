package com.xczhihui.medical.anchor.service;

import com.xczhihui.medical.anchor.vo.UserBank;

import java.util.List;

public interface IUserBankService {

	  public UserBank selectUserBankByUserIdAndAcctPan(String userId, String acctPan, String certId);

	  void addUserBank(String userId,String acctName, String acctPan,String certId,String tel);

	  public List<UserBank> selectUserBankByUserId(String userId);

	  void deleteBankCard(String userId,String acctName, String acctPan,String certId);

}
