package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.online.api.service.EnchashmentService;
import com.xczhihui.online.api.service.UserCoinService;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.common.util.enums.OrderFrom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EnchashmentServiceImpl extends OnlineBaseServiceImpl implements EnchashmentService {

	@Autowired
	private UserCoinService userCoinService;
	@Value("${rate}")
    private int rate;

	@Override
	public void saveSettlement(String userId, int amount,OrderFrom orderFrom) {
		userCoinService.updateBalanceForSettlement4Lock(userId,userId,amount, orderFrom);
	}

	@Override
	public void saveEnchashmentApplyInfo(String userId, BigDecimal enchashmentSum, int bankCardId, OrderFrom orderFrom){
		userCoinService.saveEnchashmentApplyInfo4Lock(userId,userId, enchashmentSum, bankCardId, orderFrom);
	}


}
