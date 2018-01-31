package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.util.BeanUtil;
import com.xczhihui.bxg.online.api.po.EnchashmentApplyInfo;
import com.xczhihui.bxg.online.api.service.EnchashmentService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.enums.ApplyStatus;
import com.xczhihui.bxg.online.common.enums.OrderFrom;
import com.xczhihui.bxg.online.common.utils.OrderNoUtil;
import com.xczhihui.bxg.online.web.base.utils.RandomUtil;
import com.xczhihui.bxg.online.web.base.utils.TimeUtil;
import com.xczhihui.bxg.online.web.dao.EnchashmentApplyDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class EnchashmentServiceImpl extends OnlineBaseServiceImpl implements EnchashmentService {

	@Autowired
	private EnchashmentApplyDao enchashmentApplyDao;
	@Autowired
	private UserCoinService userCoinService;
	@Value("${rate}")
    private int rate;

	@Override
	public void saveSettlement(String userId, int amount,OrderFrom orderFrom) {
		userCoinService.updateBalanceForSettlement(userId,amount, orderFrom);
	}

	@Override
	public void saveEnchashmentApplyInfo(String userId, BigDecimal enchashmentSum, int bankCardId, OrderFrom orderFrom){
		EnchashmentApplyInfo enchashmentApplyInfo = new EnchashmentApplyInfo();
		String enchashmentApplyId = BeanUtil.getUUID();
		enchashmentApplyInfo.setId(enchashmentApplyId);
		enchashmentApplyInfo.setUserId(userId);
		enchashmentApplyInfo.setEnchashmentSum(enchashmentSum);
		enchashmentApplyInfo.setBankCardId(bankCardId);
		enchashmentApplyInfo.setOrderFrom(orderFrom.getCode());
		enchashmentApplyInfo.setDeleted(false);
		enchashmentApplyInfo.setTime(new Date());
		enchashmentApplyInfo.setStatus(ApplyStatus.UNTREATED.getCode());
		String enchashmentOrderNo = OrderNoUtil.getEnchashmentOrderNo();
		enchashmentApplyInfo.setOrderNo(enchashmentOrderNo);
		validateEnchashmentApplyInfo(enchashmentApplyInfo);
		//更新用户人民币余额
		userCoinService.updateBalanceForEnchashment(userId,enchashmentSum,orderFrom,enchashmentApplyId);
		enchashmentApplyDao.save(enchashmentApplyInfo);
	}

	/**
	 * Description：提现申请校验
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 4:04 2018/1/29 0029
	 **/
	private void validateEnchashmentApplyInfo(EnchashmentApplyInfo enchashmentApplyInfo) {
		if(StringUtils.isBlank(enchashmentApplyInfo.getUserId())){
			throw new RuntimeException("用户不可为空");
		}
		if(enchashmentApplyInfo.getEnchashmentSum()==null){
			throw new RuntimeException("提现金额不可为空");
		}else if(enchashmentApplyInfo.getEnchashmentSum().compareTo(BigDecimal.ZERO)!=1){
			throw new RuntimeException("提现金额必须大于0");
		}

	}

}
