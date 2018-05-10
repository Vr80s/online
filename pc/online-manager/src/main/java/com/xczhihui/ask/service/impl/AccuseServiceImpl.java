package com.xczhihui.ask.service.impl;

/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.ask.dao.AccuseDao;
import com.xczhihui.ask.service.AccuseService;
import com.xczhihui.ask.vo.AccuseVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 投诉管理层的实现类
 *
 * @author 王高伟
 * @create 2016-10-16 10:08:05
 */
@Service
public class AccuseServiceImpl extends OnlineBaseServiceImpl implements
		AccuseService {

	@Autowired
	private AccuseDao accuseDao;

	@Override
	public Page<AccuseVo> findAccusePage(AccuseVo accuseVo, Integer pageNumber,
			Integer pageSize) {
		Page<AccuseVo> page = accuseDao.findAccusePage(accuseVo, pageNumber,
				pageSize);
		return page;
	}

	@Override
	public Boolean checkAccuseStatus(AccuseVo accuseVo) {
		return accuseDao.checkAccuseStatus(accuseVo);
	}

}
