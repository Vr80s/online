package com.xczhihui.course.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.course.dao.EssenceRecommendDao;
import com.xczhihui.course.dao.PublicCourseDao;
import com.xczhihui.course.service.EssenceRecommendService;
import com.xczhihui.course.vo.CourseVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("essenceRecommendServiceImpl")
public class EssenceRecommendServiceImpl extends OnlineBaseServiceImpl
		implements EssenceRecommendService {
	@Autowired
	private EssenceRecommendDao essenceRecommendDao;
	@Autowired
	private PublicCourseDao publicCourseDao;

	@Value("${ENV_FLAG}")
	private String envFlag;
	@Value("${LIVE_VHALL_USER_ID}")
	private String liveVhallUserId;
	@Value("${vhall_callback_url}")
	String vhall_callback_url;
	@Value("${vhall_private_key}")
	String vhall_private_key;

	@Override
	public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber,
			int pageSize) {
		Page<CourseVo> page = essenceRecommendDao.findEssenceRecCoursePage(
				courseVo, pageNumber, pageSize);
		return page;
	}

	@Override
	public boolean updateEssenceRec(String[] ids, int isEssence) {
		// TODO Auto-generated method stub
		List<String> ids2 = new ArrayList();
		if (isEssence == 1)// 如果是推荐
		{
			// 校验是否被引用
			String hqlPre = "from Course where isDelete=0 and isEssence = 1";
			List<Course> list = dao.findByHQL(hqlPre);
			if (list.size() > 0) {// 只有原来大于0才执行
				for (int i = 0; i < ids.length; i++) {
					int j = 0;
					Iterator<Course> iterator = list.iterator();
					while (iterator.hasNext()) {// 剔除本次推荐的与已经推荐的重复的

						Course course = iterator.next();
						if (course.getId() == Integer.parseInt(ids[i])) {// 如果存在就把他剔除掉从list中
							j = 1;
						}
					}
					if (j == 0) {
						ids2.add(ids[i]);
					}
				}
			} else {
				for (int i = 0; i < ids.length; i++) {
					ids2.add(ids[i]);
				}
			}
			// 已经存在的数量 + 即将添加的数量
			// if((list.size()+ids2.size()) > 12){
			// return false;
			// }
		} else {// 如果是取消推荐
			for (int i = 0; i < ids.length; i++) {
				ids2.add(ids[i]);
			}
		}

		String sql = "select ifnull(min(essence_sort),0) from oe_course where  is_delete=0 and is_essence = 1";
		int i = dao.queryForInt(sql, null);// 最小的排序

		for (String id : ids2) {
			if (id == "" || id == null) {
				continue;
			}
			i = i - 1;
			String hqlPre = "from Course where  isDelete = 0 and id = ?";
			Course course = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (course != null) {
				course.setIsEssence(isEssence);
				course.setEssenceSort(i);
				dao.update(course);
			}
		}
		return true;
	}

	@Override
	public boolean updateTypeRec(String[] ids, int isTypeRecommend) {
		// TODO Auto-generated method stub
		List<String> ids2 = new ArrayList();
		if (isTypeRecommend == 1)// 如果是推荐
		{
			// 校验是否被引用
			String hqlPre = "from Course where isDelete=0 and isTypeRecommend = 1";
			List<Course> list = dao.findByHQL(hqlPre);
			if (list.size() > 0) {// 只有原来大于0才执行
				for (int i = 0; i < ids.length; i++) {
					int j = 0;
					Iterator<Course> iterator = list.iterator();
					while (iterator.hasNext()) {// 剔除本次推荐的与已经推荐的重复的

						Course course = iterator.next();
						if (course.getId() == Integer.parseInt(ids[i])) {// 如果存在就把他剔除掉从list中
							j = 1;
						}
					}
					if (j == 0) {
						ids2.add(ids[i]);
					}
				}
			} else {
				for (int i = 0; i < ids.length; i++) {
					ids2.add(ids[i]);
				}
			}
			// 已经存在的数量 + 即将添加的数量
			/*
			 * if((list.size()+ids2.size()) > 12){ return false; }
			 */
		} else {// 如果是取消推荐
			for (int i = 0; i < ids.length; i++) {
				ids2.add(ids[i]);
			}
		}

		String sql = "select ifnull(min(type_sort),0) from oe_course where  is_delete=0 and is_type_recommend = 1";
		int i = dao.queryForInt(sql, null);// 最小的排序

		for (String id : ids2) {
			if (id == "" || id == null) {
				continue;
			}
			i = i - 1;
			String hqlPre = "from Course where  isDelete = 0 and id = ?";
			Course course = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (course != null) {
				course.setIsTypeRecommend(isTypeRecommend);
				course.setTypeSort(i);
				dao.update(course);
			}
		}
		return true;
	}

	@Override
	public void updateJpSortUp(Integer id) {
		String hqlPre = "from Course where  isDelete=0 and id = ?";
		Course coursePre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer coursePreSort = coursePre.getEssenceSort();

		// String
		// hqlNext="from Course where sort > (select sort from Course where id= ? ) and type=2  and isDelete=0 order by sort asc";

		String hqlNext = "from Course where essenceSort > (select essenceSort from Course where id= ? ) and isEssence=1 and isDelete=0 and status=1  order by essenceSort asc";
		Course courseNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer courseNextSort = courseNext.getEssenceSort();

		coursePre.setEssenceSort(courseNextSort);
		courseNext.setEssenceSort(coursePreSort);

		dao.update(coursePre);
		dao.update(courseNext);

	}

	@Override
	public void updateJpSortDown(Integer id) {
		String hqlPre = "from Course where  isDelete=0 and id = ?";
		Course coursePre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer coursePreSort = coursePre.getEssenceSort();

		String hqlNext = "from Course where essenceSort < (select essenceSort from Course where id= ? )  and isEssence=1 and isDelete=0 and status=1  order by essenceSort desc";
		Course courseNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer courseNextSort = courseNext.getEssenceSort();

		coursePre.setEssenceSort(courseNextSort);
		courseNext.setEssenceSort(coursePreSort);

		dao.update(coursePre);
		dao.update(courseNext);
	}

	@Override
	public void updateFlSortUp(Integer id) {
		String hqlPre = "from Course where  isDelete=0 and id = ?";
		Course coursePre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer coursePreSort = coursePre.getEssenceSort();

		String hqlNext = "from Course where typeSort > (select typeSort from Course where id= ? ) and isTypeRecommend =1 and isDelete=0 and status=1  order by typeSort asc";
		Course courseNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer courseNextSort = courseNext.getEssenceSort();

		coursePre.setTypeSort(courseNextSort);
		courseNext.setTypeSort(coursePreSort);

		dao.update(coursePre);
		dao.update(courseNext);

	}

	@Override
	public void updateFlSortDown(Integer id) {

		String hqlPre = "from Course where  isDelete=0 and id = ?";
		Course coursePre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer coursePreSort = coursePre.getEssenceSort();

		String hqlNext = "from Course where typeSort < (select typeSort from Course where id= ? )  and isTypeRecommend =1 and isDelete=0 and status=1  order by typeSort desc";
		Course courseNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer courseNextSort = courseNext.getEssenceSort();

		coursePre.setTypeSort(courseNextSort);
		courseNext.setTypeSort(coursePreSort);

		dao.update(coursePre);
		dao.update(courseNext);
	}

}
