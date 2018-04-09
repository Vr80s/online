package com.xczhihui.course.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.CourseRecommend;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.course.dao.CourseRecommendDao;
import com.xczhihui.course.service.CourseRecommendService;
import com.xczhihui.course.vo.CourseRecommendVo;
import com.xczhihui.course.vo.CourseVo;

@Service
public class CourseRecommendServiceImpl extends OnlineBaseServiceImpl implements
		CourseRecommendService {

	@Autowired
	private CourseRecommendDao courseRecommendDao;

	@Override
	public Page<CourseVo> findCourseRecommendPage(
			CourseRecommendVo courseRecommendVo, Integer pageNumber,
			Integer pageSize) {
		Page<CourseVo> page = courseRecommendDao.findCourseRecommendPage(
				courseRecommendVo, pageNumber, pageSize);
		return page;
	}

	@Override
	public void updateSortUp(Integer id) {
		// TODO Auto-generated method stub
		String hqlPre = "from CourseRecommend where  isDelete=0 and id = ?";
		CourseRecommend courseRecommendPre = dao.findByHQLOne(hqlPre,
				new Object[] { id });
		Integer courseRecommendPreSort = courseRecommendPre.getSort();

		String hqlNext = "from CourseRecommend where sort < (select sort from CourseRecommend where id= ? ) and showCourseId = ? and isDelete=0 order by sort desc";
		CourseRecommend courseRecommendNext = dao.findByHQLOne(hqlNext,
				new Object[] { id, courseRecommendPre.getShowCourseId() });
		Integer courseRecommendNextSort = courseRecommendNext.getSort();

		courseRecommendPre.setSort(courseRecommendNextSort);
		courseRecommendNext.setSort(courseRecommendPreSort);

		dao.update(courseRecommendPre);
		dao.update(courseRecommendNext);
	}

	@Override
	public void updateSortDown(Integer id) {
		// TODO Auto-generated method stub
		String hqlPre = "from CourseRecommend where  isDelete=0 and id = ?";
		CourseRecommend courseRecommendPre = dao.findByHQLOne(hqlPre,
				new Object[] { id });
		Integer courseRecommendPreSort = courseRecommendPre.getSort();
		String hqlNext = "from CourseRecommend where sort > (select sort from CourseRecommend where id= ? ) and showCourseId = ?  and isDelete=0  order by sort asc";
		CourseRecommend courseRecommendNext = dao.findByHQLOne(hqlNext,
				new Object[] { id, courseRecommendPre.getShowCourseId() });
		Integer courseRecommendNextSort = courseRecommendNext.getSort();

		courseRecommendPre.setSort(courseRecommendNextSort);
		courseRecommendNext.setSort(courseRecommendPreSort);

		dao.update(courseRecommendPre);
		dao.update(courseRecommendNext);
	}

	@Override
	public void deletes(String[] ids) {
		// TODO Auto-generated method stub
		for (String id : ids) {
			String hqlPre = "from CourseRecommend where isDelete=0 and id = ?";
			CourseRecommend courseRecommend = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (courseRecommend != null) {
				courseRecommend.setDelete(true);
				dao.update(courseRecommend);
			}
		}
	}

	@Override
	public void addCourseRecommend(String showCourseId, String[] recCourseHids,
			String createPerson) {
		// 先把showCourseId的数据都删除 然后重新添加一次所有的
		String hql = "from CourseRecommend where showCourseId = ?";
		List<CourseRecommend> list = dao.findByHQL(hql,
				new Object[] { Integer.parseInt(showCourseId) });
		dao.getHibernateTemplate().deleteAll(list);
		// 然后重新遍历添加该课程下所有数据
		for (int i = 0; i < recCourseHids.length; i++) {
			CourseRecommend cr = new CourseRecommend();
			cr.setCreatePerson(createPerson);
			cr.setCreateTime(new Date());
			cr.setShowCourseId(Integer.parseInt(showCourseId));
			cr.setRecCourseId(Integer.parseInt(recCourseHids[i]));
			cr.setDelete(false);
			cr.setSort(i);
			dao.save(cr);
		}
	}
}
