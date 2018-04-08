package com.xczhihui.course.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.course.vo.CourseVo;

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

	/**
	 * 
	 * Description：精品课程的上移
	 * @param id
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public void updateJpSortUp(Integer id);
	/**
	 * 
	 * Description：精品课程的下移
	 * @param id
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public void updateJpSortDown(Integer id);
	/**
	 * 
	 * Description：分类课程的上移
	 * @param id
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public void updateFlSortUp(Integer id);
	/**
	 * 
	 * Description：分类课程的下移
	 * @param id
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public void updateFlSortDown(Integer id);

	/**
	 * 
	 * Description：设置或者取消分类推荐
	 * @param _ids
	 * @param isRec
	 * @return
	 * @return boolean
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public boolean updateTypeRec(String[] _ids, int isRec);





}
