package com.xczhihui.bbs.service.impl;

import java.util.List;

import com.xczhihui.bbs.dao.BBSLabelDao;
import com.xczhihui.bbs.service.BBSLabelService;
import com.xczhihui.bbs.vo.BBSLabelVo;
import com.xczhihui.utils.TableVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.BBSLabel;

@Service
public class BBSLabelServiceImpl implements BBSLabelService {

	private static final Logger logger = LoggerFactory
			.getLogger(BBSLabelServiceImpl.class);

	@Autowired
	private BBSLabelDao bbsLabelDao;

	@Override
	public TableVo list(TableVo tableVo) {
		Page<BBSLabelVo> list = bbsLabelDao.list(tableVo.getCurrentPage(),
				tableVo.getiDisplayLength());
		return tableVo.fetch(list);
	}

	@Override
	public int delete(List<Integer> ids) {
		return bbsLabelDao.deleteByIds(ids);
	}

	@Override
	public boolean create(BBSLabel label) {
		bbsLabelDao.save(label);
		return true;
	}

	@Override
	public boolean update(BBSLabel label) {
		bbsLabelDao.update(label);
		return true;
	}

	@Override
	public int updateStatus(List<Integer> ids) {
		return bbsLabelDao.updateStatusByIds(ids);
	}
}
