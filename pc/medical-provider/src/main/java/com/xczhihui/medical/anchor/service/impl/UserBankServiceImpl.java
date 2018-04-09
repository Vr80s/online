package com.xczhihui.medical.anchor.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.xczhihui.bxg.common.util.enums.BankCardType;
import com.xczhihui.bxg.common.util.IDCard;
import com.xczhihui.bxg.common.support.lock.Lock;
import com.xczhihui.medical.anchor.mapper.CourseAnchorMapper;
import com.xczhihui.medical.anchor.mapper.UserBankMapper;
import com.xczhihui.medical.anchor.model.CourseAnchor;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.anchor.vo.UserBank;
import com.xczhihui.medical.anchor.service.IUserBankService;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorApplyMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctorApply;
import com.xczhihui.utils.BankUtil;
import com.xczhihui.utils.HttpUtils;
import com.xczhihui.utils.MatchLuhn;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class UserBankServiceImpl extends ServiceImpl<UserBankMapper, UserBank>
		implements IUserBankService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserBankMapper userBankMapper;

	@Autowired
	private IUserBankService userBankService;

	@Autowired
	private IAnchorInfoService anchorInfoService;

	@Autowired
	private MedicalDoctorApplyMapper medicalDoctorApplyMapper;
	
	@Autowired
	private CourseAnchorMapper courseAnchorMapper;

	@Override
	public UserBank selectUserBankByUserIdAndAcctPan(String userId,
			String acctPan, String certId) {
		anchorInfoService.validateAnchorPermission(userId);
		return userBankMapper.selectUserBankByUserIdAndAcctPan(userId, acctPan,
				certId);
	}

	@Override
	public void addUserBank(String userId, String acctName, String acctPan,
			String certId, String tel) {

		userBankService.addUserBank4Lock(userId, userId, acctName, acctPan,
				certId, tel);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Lock(lockName = "addUserBankCard")
	public void addUserBank4Lock(String lockKey, String userId,
			String acctName, String acctPan, String certId, String tel) {

		UserBank userBank = new UserBank();
		userBank.setUserId(userId);
		userBank.setAcctName(acctName);
		userBank.setAcctPan(acctPan);
		userBank.setCertId(certId);
		userBank.setBankName(BankCardType.getBankCard(Integer.parseInt(tel)));
		userBank.setTel(tel);

		UserBank ub = selectUserBankByUserIdAndAcctPan(userBank.getUserId(),
				userBank.getAcctPan(), userBank.getCertId());
		if (ub != null) {
			throw new RuntimeException("此卡已添加");
		}
		String host = "https://ali-bankcard4.showapi.com";
		String path = "/bank3";

		String method = "GET";
		String appcode = "c46df1e69afe4de199c7ce7041277534";
		Map<String, String> headers = new HashMap<String, String>();
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE
		// 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		Map<String, String> querys = new HashMap<String, String>();
		querys.put("acct_name", userBank.getAcctName());
		querys.put("acct_pan", userBank.getAcctPan());
		querys.put("needBelongArea", "true");

		if (!StringUtils.isNotBlank(certId)) {// 如果不填写身份证号信息时就是二元素认证
			path = "/bank2";
		} else {
			querys.put("cert_id", userBank.getCertId());
			querys.put("cert_type", "01");
		}
		String bankInfo = "";
		try {
			HttpResponse response = HttpUtils.doGet(host, path, method,
					headers, querys);

			bankInfo = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加失败");
		}
		logger.info("银行卡校验返回信息：{}", bankInfo);
		JSONObject bankInfoJson = JSONObject.parseObject(bankInfo);

		String showapi_res_body = bankInfoJson.get("showapi_res_body")
				.toString();
		JSONObject showapi_res_bodyJson = JSONObject
				.parseObject(showapi_res_body);
		String code = showapi_res_bodyJson.get("code").toString();
		String msg = showapi_res_bodyJson.get("msg").toString();
		if (!"0".equals(code)) {
			throw new RuntimeException(msg);
		}
		String srb = bankInfoJson.get("showapi_res_body").toString();
		JSONObject srbJson = JSONObject.parseObject(srb);
		JSONObject belong = JSONObject.parseObject(srbJson.get("belong")
				.toString());
		String cardType = belong.get("cardType").toString();
		userBank.setCardType(cardType);

		List<UserBank> userBankList = selectUserBankByUserId(
				userBank.getUserId(), false);
		if (userBankList != null && userBankList.size() > 0) {
			userBank.setDefault(false);
		} else {
			userBank.setDefault(true);
		}
		userBank.setCertType("01");
		userBank.setCreateTime(new Date());
		userBankMapper.add(userBank);
		
		logger.info("-----》添加成功");
	}

	@Override
	public List<UserBank> selectUserBankByUserId(String userId, boolean complete) {
		anchorInfoService.validateAnchorPermission(userId);
		List<UserBank> userBankCards = userBankMapper
				.selectUserBankByUserId(userId);
		for (UserBank userBankCard : userBankCards) {
			if (complete) {
				userBankCard.setAcctPan(userBankCard.getAcctPan());
			} else {
				userBankCard
						.setAcctPan(dealBankCard(userBankCard.getAcctPan()));
			}
		}
		return userBankCards;
	}

	@Override
	public void deleteBankCard(String userId, Integer id) {
		anchorInfoService.validateAnchorPermission(userId);
		UserBank ub = userBankMapper.getCardById(id);
		// 判断删除的是否是默认的
		if (ub.isDefault()) {
			userBankMapper.deleteBankCard(id);
			List<UserBank> list = userBankMapper.selectUserBankByUserId(userId);
			if (list.size() > 0) {
				UserBank u = list.get(0);
				updateDefault(userId, u.getId());
			}
		} else {
			userBankMapper.deleteBankCard(id);
		}

	}

	@Override
	public void updateDefault(String userId, Integer id) {
		userBankMapper.cancelDefault(userId);
		userBankMapper.updateDefault(id);
	}

	@Override
	public UserBank getDefault(String userId) {
		anchorInfoService.validateAnchorPermission(userId);
		return userBankMapper.getDefault(userId);
	}

	@Override
	public int getBankCount(String userId) {
		anchorInfoService.validateAnchorPermission(userId);
		return userBankMapper.getBankCount(userId);
	}

	private void validateUserBank(UserBank userBank) {
		if (StringUtils.isBlank(userBank.getUserId())) {
			throw new RuntimeException("用户id不可为空");
		}
		if (StringUtils.isBlank(userBank.getAcctName())) {
			throw new RuntimeException("户名不可为空");
		}
		if (StringUtils.isBlank(userBank.getAcctPan())) {
			throw new RuntimeException("卡号不可为空");
		}

		// if(StringUtils.isBlank(userBank.getCertId())){
		// throw new RuntimeException("身份证号不可为空");
		// }else if(!IDCard.validator(userBank.getCertId())){
		// throw new RuntimeException("身份证号不正确");
		// }
	}

	private void validateUserBankCertId(UserBank userBank) {

		if (!IDCard.validator(userBank.getCertId())) {
			throw new RuntimeException("身份证号不正确");
		}

	}

	/**
	 * Description：处理银行卡显示 creed: Talk is cheap,show me the code
	 * 
	 * @author name：yuxin <br>
	 *         email: yuruixin@ixincheng.com
	 * @Date: 下午 4:05 2018/2/2 0002
	 **/
	public static String dealBankCard(String acctPan) {
		int length = acctPan.length();
		return "***** ***** **** " + acctPan.substring(length - 4);
	}

	@Override
	public Integer validateBankInfo(String userId, String acctName,
			String acctPan, String certId, String tel, Integer code) {

		anchorInfoService.validateAnchorPermission(userId);

		if (!MatchLuhn.matchLuhn(acctPan)) {
			throw new RuntimeException("银行卡格式有误");
		}
		boolean verifyBank  = false;
		String yzcx = BankCardType.getBankCard(Integer.valueOf(tel));
		if(yzcx.indexOf("邮储银行")!=-1){ //邮政储蓄银行需要在判断下
			if(BankUtil.getNameOfBank(acctPan).contains(yzcx) || BankUtil.getNameOfBank(acctPan).contains("邮政储蓄银行")){
				verifyBank = true;
			}
		}else{
			verifyBank = BankUtil.getNameOfBank(acctPan).contains(yzcx);
		}
		
		if (!verifyBank) {
			throw new RuntimeException("银行卡号与银行不匹配");
		}
		UserBank userBank = new UserBank();
		userBank.setUserId(userId);
		userBank.setAcctName(acctName);
		userBank.setAcctPan(acctPan);
		userBank.setCertId(certId);
		userBank.setBankName(BankCardType.getBankCard(Integer.parseInt(tel)));
		userBank.setTel(tel);
		validateUserBank(userBank);
		Integer fontCode = 200;
		/*
		 * 如果
		 */
		if (StringUtils.isNotBlank(certId)) {
			/**
			 * 验证身份证号 格式
			 */
			validateUserBankCertId(userBank);
			if (code == null || code != 1) { // 用户同意了这次操作
			    CourseAnchor courseAnchor = new CourseAnchor();
		        courseAnchor.setUserId(userId);
		        courseAnchor.setStatus(true);
		        CourseAnchor ca = courseAnchorMapper.selectOne(courseAnchor);
		        if(ca == null ){
		        	 throw new RuntimeException("不具备主播权限或主播权限被禁用");
		        }
				if(ca.getType() == 1){
					/**
					 * 如果是医师过的主播，验证主播的身份证号和这个是否相同 验证身份证号 是否相同
					 */
					MedicalDoctorApply medicalDoctorApply = medicalDoctorApplyMapper.getLastOne(userBank.getUserId());
					if (medicalDoctorApply != null) {
						if (!userBank.getCertId().equals(
								medicalDoctorApply.getCardNum())) {
							fontCode = 201;
						}
					}
				}
			}
		}
		return fontCode;
	}
}
