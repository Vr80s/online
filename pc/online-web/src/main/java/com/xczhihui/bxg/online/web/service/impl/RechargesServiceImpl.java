package com.xczhihui.bxg.online.web.service.impl;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.bxg.online.api.po.Recharges;
import com.xczhihui.bxg.online.api.service.RechargesService;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.web.dao.GiftDao;
import com.xczhihui.bxg.online.web.dao.QuestionDao;
import com.xczhihui.bxg.online.web.dao.RechargesServiceDao;
import com.xczhihui.bxg.online.web.service.QuestionService;
import com.xczhihui.bxg.online.web.vo.QuestionVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 充值实现类
 *
 * @author 康荣彩
 * @create 2016-08-29 16:46
 */
@Service
public class RechargesServiceImpl  extends OnlineBaseServiceImpl implements RechargesService {

	@Autowired
	private RechargesServiceDao rechargesServiceDao;
	
	
	@Override
	public List<Recharges> getRecharges() {
		
		return rechargesServiceDao.getRecharges();
	}
	
}
