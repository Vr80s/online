package com.xczhihui.bxg.online.manager.cloudClass.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.bxg.online.manager.cloudClass.dao.ApplyDao;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.ApplyService;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.LecturerService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service("applyService")
public class ApplyServiceImpl extends OnlineBaseServiceImpl implements ApplyService {

	@Autowired
	private ApplyDao applyDao;

	@Override
	public Page<ApplyVo> findPage(ApplyVo vo, int pageNumber, int pageSize) {
		Page<ApplyVo> page = applyDao.findApplyPage(vo, pageNumber, pageSize);
		return page;
	}

	@Override
	public void deleteStudents(String[] ids) {
		applyDao.deleteStudents(ids);
	}

	@Override
	public void updateChangePayment(ApplyVo vo) {
		String sql="update apply_r_grade_course set is_payment=:isPayment ,cost=:cost where id=:id and is_delete=0 ";
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", vo.getId());
		paramSource.addValue("isPayment", vo.getIsPayment());
		paramSource.addValue("cost", vo.getCost());
		dao.getNamedParameterJdbcTemplate().update(sql,paramSource);
	}
}