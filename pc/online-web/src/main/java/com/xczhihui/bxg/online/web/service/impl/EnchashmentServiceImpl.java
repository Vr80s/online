package com.xczhihui.bxg.online.web.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.xczhihui.bxg.online.common.enums.OrderForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.EnchashmentApplication;
import com.xczhihui.bxg.online.api.po.UserCoinConsumption;
import com.xczhihui.bxg.online.api.service.EnchashmentService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Apply;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.dao.EnchashmentDao;
import com.xczhihui.bxg.online.web.service.OnlineUserCenterService;

@Service
public class EnchashmentServiceImpl extends OnlineBaseServiceImpl implements EnchashmentService {

	@Autowired
	private EnchashmentDao enchashmentDao;
	@Autowired
	private UserCoinService userCoinService;
	@Autowired
	private OnlineUserCenterService onlineUserCenterService;
	@Value("${rate}")
    private int rate;


	@Override
	public void saveSettlement(String userId, int amount,OrderForm orderForm) {
		userCoinService.updateBalanceForSettlement(userId,amount, orderForm);
	}

	@Override
	public Page<EnchashmentApplication> enchashmentApplicationList(String userId,Integer pageNumber, Integer pageSize) {
		
		Page<EnchashmentApplication> pageList =  enchashmentDao.enchashmentApplicationList(userId,pageNumber,pageSize);
		for (EnchashmentApplication enchashmentApplication : pageList.getItems()) {
			Integer enchashmentAccountType  = enchashmentApplication.getEnchashmentAccountType();
			String enchashmentAccount = enchashmentApplication.getEnchashmentAccount();
			String newRemark ="提现到";
			//0:支付宝 1:微信 2:网银 3:余额
			if(enchashmentAccountType == 0){
				newRemark+="支付宝";
			}else if(enchashmentAccountType == 1){
				newRemark+="微信";
			}else if(enchashmentAccountType == 2){
				newRemark+="网银";
			}else if(enchashmentAccountType == 3){
				newRemark+="余额";
			}
			newRemark+="账户"+enchashmentAccount;
			enchashmentApplication.setNewRemark(newRemark);
			/**
			 * 当余额减去本次要提现的余额
			 */
			BigDecimal bdYe = enchashmentApplication.getEnableEnchashmentBalance();
			BigDecimal bdBc = enchashmentApplication.getEnchashmentSum();
			enchashmentApplication.setEnableEnchashmentBalance(bdYe.subtract(bdBc));
		}
		return pageList;
	}


	@Override
	public void saveEnchashmentApplication(EnchashmentApplication ea) {
		if(ea.getRealName()==null|| "".equals(ea.getRealName().trim())){
			throw new RuntimeException("真实姓名不可为空！");
		}
		if(ea.getEnchashmentSum()==null||ea.getEnchashmentSum().compareTo(new BigDecimal("0.01"))==-1){
			throw new RuntimeException("提现金额不低于0.01元");
		}
		if(ea.getPhone()==null|| "".equals(ea.getPhone().trim())){
			throw new RuntimeException("手机号不可为空");
		}
		if(ea.getEnchashmentAccount()==null|| "".equals(ea.getEnchashmentAccount().trim())){
			throw new RuntimeException("提现账号不可为空");
		}
		if(ea.getEnchashmentAccountType()==null||(ea.getEnchashmentAccountType()!=0&&ea.getEnchashmentAccountType()!=1&&ea.getEnchashmentAccountType()!=2)){
			throw new RuntimeException("提现账号类型有误");
		}
		ea.setEnchashmentStatus(0);
		ea.setTime(new Date());
		UserCoinConsumption ucc = new UserCoinConsumption();
		ucc.setChangeType(6);//6为提现
		ucc.setValue(ea.getEnchashmentSum().negate().multiply(new BigDecimal(rate)));
		BigDecimal enea = userCoinService.getEnableEnchashmentBalance(ea.getUserId());
		if(enea.add(ucc.getValue()).compareTo(BigDecimal.ZERO)==-1){
			throw new RuntimeException("用户账户可提现余额不足！");
		}
		ea.setEnableEnchashmentBalance(enea.divide(new BigDecimal(rate), 2,RoundingMode.DOWN));
		enchashmentDao.saveEnchashmentApplication(ea);
        ucc.setOrderNoEnchashment(ea.getId().toString());
        ucc.setUserId(ea.getUserId());
        //暂时扣除相应可充值余额
		userCoinService.updateBalanceForEnchashment(ucc);//此处为暂时扣除，若申请被驳回则余额再加回
	}


	@Override
	public String enableEnchashmentBalance(String userId) {
		//可提现熊猫币取整
		return userCoinService.getEnableEnchashmentBalance(userId).toString();
	}
	

	@Override
	public Double enableEnchashmentRmbBalance(String userId) {
		BigDecimal eeb = userCoinService.getEnableEnchashmentBalance(userId);
		eeb = eeb.divide(new BigDecimal(rate), 2,RoundingMode.DOWN);
		return eeb.doubleValue();
	}


	@Override
	public Map<String, Object> getEnableEnchashmentData(String userId) {
		Apply apply = dao.findOneEntitiyByProperty(Apply.class, "userId", userId);
		OnlineUser ou = onlineUserCenterService.getUser(userId);
		String name = null;
		if(apply!=null){
			name = apply.getRealName();
		}
		Double eeb = enableEnchashmentRmbBalance(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("phone", ou.getLoginName());
		map.put("enableEnchashmentBalance", eeb);
		return map;
	}


}
