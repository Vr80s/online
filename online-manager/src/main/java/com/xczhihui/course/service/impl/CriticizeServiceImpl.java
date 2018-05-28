package com.xczhihui.course.service.impl;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Criticize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.course.dao.CriticizeDao;
import com.xczhihui.course.service.CriticizeService;
import com.xczhihui.course.vo.CourseVo;
import com.xczhihui.course.vo.CriticizeVo;

@Service
public class CriticizeServiceImpl extends OnlineBaseServiceImpl implements
		CriticizeService {

	@Autowired
	private CriticizeDao criticizeDao;

	@Override
	public Page<CriticizeVo> findCriticizePage(CriticizeVo criticizeVo,
			Integer pageNumber, Integer pageSize) {
		Page<CriticizeVo> page = criticizeDao.findCriticizePage(criticizeVo,
				pageNumber, pageSize);
		return page;
	}

	@Override
	public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber,
			int pageSize) {
		Page<CourseVo> page = criticizeDao.findCloudClassCoursePage(courseVo,
				pageNumber, pageSize);
		return page;

	}

	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for (String id : ids) {
			String hqlPre = "from Criticize where isDelete=0 and id = ?";
			Criticize criticize = dao.findByHQLOne(hqlPre, new Object[] { id });
			if (criticize != null) {
				criticize.setDelete(true);
				dao.update(criticize);
			}
		}
	}

	@Override
	public void addResponse(String id, String content) {
		content = content == null ? null : content.trim();
		dao.getNamedParameterJdbcTemplate()
				.getJdbcOperations()
				.update("update oe_criticize set response=?,response_time=now() where id=?",
						content, id);
	}
}
