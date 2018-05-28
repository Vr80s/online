package com.xczhihui.bxg.online.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.domain.Reward;
import com.xczhihui.bxg.online.web.base.common.Broadcast;
import com.xczhihui.bxg.online.web.service.RewardService;



/** 
 * ClassName: RewardController.java <br>
 * Description: 打赏<br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月13日<br>
 */
@RestController
@RequestMapping(value = "/reward")
public class RewardController {

	@Autowired
	private RewardService rewardService;
	@Autowired
	private Broadcast broadcast;

	/** 
	 * Description：赠送礼物接口（做礼物赠送余额扣减和检验）
	 * @param giftStatement
	 * @return
	 * @return ResponseObject
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	@RequestMapping(value = "/getReward")
	public ResponseObject getReward() {
		List<Reward> rewards = rewardService.findAll();
		return ResponseObject.newSuccessResponseObject(rewards);
	}
	
}
