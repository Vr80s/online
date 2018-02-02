package com.xczhihui.medical.anchor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.bxg.online.common.enums.BankCardType;
import com.xczhihui.medical.anchor.mapper.UserBankMapper;
import com.xczhihui.medical.anchor.vo.UserBank;
import com.xczhihui.medical.anchor.service.IUserBankService;
import com.xczhihui.utils.HttpUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper,UserBank> implements IUserBankService {

	@Autowired
	private UserBankMapper userBankMapper;

	@Override
	public UserBank selectUserBankByUserIdAndAcctPan(String userId, String acctPan,String certId) {
		return userBankMapper.selectUserBankByUserIdAndAcctPan(userId,acctPan,certId);
	}

	@Override
	public void addUserBank(String userId,String acctName, String acctPan,String certId,String tel) {
        UserBank userBank = new UserBank();
        userBank.setUserId(userId);
        userBank.setAcctName(acctName);
        userBank.setAcctPan(acctPan);
        userBank.setCertId(certId);
        userBank.setBankName(BankCardType.getBankCard(Integer.parseInt(tel)));
        userBank.setTel(tel);
		validateUserBank(userBank);
		UserBank ub = selectUserBankByUserIdAndAcctPan(userBank.getUserId(),userBank.getAcctPan(),userBank.getCertId());
		if(ub!=null){
			throw new RuntimeException("此卡已添加");
		}
		String host = "https://ali-bankcard4.showapi.com";
		String path = "/bank3";
		String method = "GET";
		String appcode = "c46df1e69afe4de199c7ce7041277534";
		Map<String, String> headers = new HashMap<String, String>();
		//最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("acct_name", userBank.getAcctName());
		querys.put("acct_pan", userBank.getAcctPan());
		querys.put("cert_id", userBank.getCertId());
		querys.put("cert_type", "01");
		querys.put("needBelongArea", "true");
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method, headers,querys);

			String bankInfo = EntityUtils.toString(response.getEntity());
			JSONObject bankInfoJson = JSONObject.parseObject(bankInfo);
			String code = bankInfoJson.get("showapi_res_code").toString();
			String srb = bankInfoJson.get("showapi_res_body").toString();
			JSONObject srbJson = JSONObject.parseObject(srb);
			JSONObject belong = JSONObject.parseObject(srbJson.get("belong").toString());
			String t = belong.get("tel").toString();

		if(!"0".equals(code)){
			throw new RuntimeException("银行卡信息有误");
		}
		if(!t.equals(userBank.getTel())){
			throw new RuntimeException("银行卡号与银行不匹配");
		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加失败");
		}
		List<UserBank> userBankList = selectUserBankByUserId(userBank.getUserId());
		if(userBankList!=null&&userBankList.size()>0){
			userBank.setDefault(true);
		}else {
			userBank.setDefault(false);
		}
		userBank.setCertType("01");
		userBank.setCreateTime(new Date());
		userBankMapper.add(userBank);
	}

	@Override
	public List<UserBank> selectUserBankByUserId(String userId) {
		List<UserBank> userBankCards = userBankMapper.selectUserBankByUserId(userId);
		for (UserBank userBankCard : userBankCards) {
			userBankCard.setAcctPan(dealBankCard(userBankCard.getAcctPan()));
		}
		return userBankCards;
	}


	private void validateUserBank(UserBank userBank) {
		if(StringUtils.isBlank(userBank.getUserId())){
			throw new RuntimeException("用户id不可为空");
		}
		if(StringUtils.isBlank(userBank.getAcctName())){
			throw new RuntimeException("户名不可为空");
		}
		if(StringUtils.isBlank(userBank.getAcctPan())){
			throw new RuntimeException("卡号不可为空");
		}
		if(StringUtils.isBlank(userBank.getCertId())){
			throw new RuntimeException("身份证号不可为空");
		}
	}

	/**
	 * Description：处理银行卡显示
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 4:05 2018/2/2 0002
	 **/
	public static String dealBankCard(String acctPan){
		int length=acctPan.length();
		return "***** ***** **** "+acctPan.substring(length-4);
	}
}
