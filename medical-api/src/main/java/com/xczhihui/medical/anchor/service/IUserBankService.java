package com.xczhihui.medical.anchor.service;

import com.xczhihui.medical.anchor.vo.UserBank;

import java.util.List;

public interface IUserBankService {

	/**
	 * Description：通过登录人id，银行卡号，身份证号查询银行卡信息
	 * creed: Talk is cheap,show me the code
	 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	 * @Date: 2018/2/2 20:55
	 **/
	  public UserBank selectUserBankByUserIdAndAcctPan(String userId, String acctPan, String certId);

	  /**
	   * Description：添加银行卡
	   * creed: Talk is cheap,show me the code
	   * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	   * @Date: 2018/2/2 20:57
	   **/
	  void addUserBank(String userId,String acctName, String acctPan,String certId,String tel);

    void addUserBank4Lock(String lockKey, String userId, String acctName, String acctPan, String certId, String tel);

    /**
	   * Description：获取用户所有绑定的银行卡
	   * creed: Talk is cheap,show me the code
	   * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	   * @Date: 2018/2/2 20:57
	   **/
	  public List<UserBank> selectUserBankByUserId(String userId,boolean complete);

	  /**
	   * Description：
	   * creed: Talk is cheap,show me the code
	   * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	   * @Date: 2018/2/2 20:58
	   **/
	  void deleteBankCard(String userId,Integer id);
	  /**
	   * Description：设置为默认
	   * creed: Talk is cheap,show me the code
	   * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	   * @Date: 2018/2/5 13:53
	   **/
	  void  updateDefault(String userId,Integer id);
	  /**
	   * Description：获取默认银行卡卡
	   * creed: Talk is cheap,show me the code
	   * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	   * @Date: 2018/2/5 14:07
	   **/
	  UserBank getDefault(String userId);

	  /**
	   * Description：获取绑定银行卡数量
	   * creed: Talk is cheap,show me the code
	   * @author name：yuxin <br>email: yuruixin@ixincheng.com
	   * @Date: 2018/2/10 0010 下午 3:56
	   **/
	  int getBankCount(String id);

	public Integer validateBankInfo(String id, String acctName, String acctPan,
			String certId, String tel, Integer code);
}
