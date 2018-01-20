package com.xczhihui.bxg.online.manager.cloudClass.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Lecturer;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.manager.cloudClass.vo.ChangeCallbackVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface EssenceRecommendService {
	/**
	 * 精品课程推荐分页
	 * @param courseVo
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber, int pageSize);

	/**
	 * 更新是否为精品推荐
	 * @return
	 */
	public boolean updateEssenceRec(String[] ids,int isEssence);

	public void updateSortUp(Integer id);

	public void updateSortDown(Integer id);





}
