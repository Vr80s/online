package com.xczhihui.bxg.online.manager.anchor.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.CourseAnchor;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.manager.anchor.dao.AnchorDao;
import com.xczhihui.bxg.online.manager.anchor.service.AnchorService;
import com.xczhihui.bxg.online.manager.anchor.vo.AnchorIncomeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *   CourseServiceImpl:课程业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class CourseAnchorServiceImpl extends OnlineBaseServiceImpl implements AnchorService {

    @Autowired
    private AnchorDao anchorDao;

    @Override
	public Page<CourseAnchor> findCourseAnchorPage(CourseAnchor courseAnchor, int pageNumber, int pageSize) {
    	Page<CourseAnchor> page = anchorDao.findCourseAnchorPage(courseAnchor, pageNumber, pageSize);
    	return page;
	}

	@Override
	public CourseAnchor findCourseAnchorById(Integer id) {
		return anchorDao.findCourseAnchorById(id);
	}

	@Override
	public void updateCourseAnchor(CourseAnchor courseAnchor) {
		CourseAnchor ca = dao.findOneEntitiyByProperty(CourseAnchor.class, "id", courseAnchor.getId());
		ca.setLiveDivide(courseAnchor.getLiveDivide());
		ca.setVodDivide(courseAnchor.getVodDivide());
		ca.setOfflineDivide(courseAnchor.getOfflineDivide());
		ca.setGiftDivide(courseAnchor.getGiftDivide());
		dao.update(ca);
	}

	@Override
	public void updatePermissions(Integer id) {
		CourseAnchor ca = dao.findOneEntitiyByProperty(CourseAnchor.class, "id", id);
		ca.setStatus(!ca.getStatus());
		dao.update(ca);
	}

	@Override
	public Page<AnchorIncomeVO> findCourseAnchorIncomePage(CourseAnchor searchVo, int currentPage, int pageSize) {
		Page<AnchorIncomeVO> page = anchorDao.findCourseAnchorIncomePage(searchVo, currentPage, pageSize);
		return page;
	}

}
