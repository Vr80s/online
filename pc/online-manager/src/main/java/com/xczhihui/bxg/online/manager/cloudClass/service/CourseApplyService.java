package com.xczhihui.bxg.online.manager.cloudClass.service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.*;

public interface CourseApplyService {


	/**
	 * 根据条件分页获取课程信息。
	 *
	 * @return
	 */
    public Page<CourseApplyInfo> findCoursePage(CourseApplyInfo courseApplyInfo, int pageNumber, int pageSize);

    CourseApplyInfo findCourseApplyById(Integer id);

    void savePass(Integer courseApplyId, String id);

	void saveNotPass(CourseApplyInfo courseApply, String id);

    Page<CourseApplyResource> findCourseApplyResourcePage(CourseApplyResource searchVo, int currentPage, int pageSize);

	void deleteOrRecoveryCourseApplyResource(Integer courseApplyId,Boolean delete);
	/**
	 * Description：获取主播的课程列表
	 * creed: Talk is cheap,show me the code
	 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	 * @Date: 2018/3/15 21:46
	 **/
	public Page<CourseApplyInfo> findCoursePageByUserId(CourseApplyInfo courseApplyInfo, int pageNumber, int pageSize);
	/**
	 * Description：
	 * creed: Talk is cheap,show me the code
	 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	 * @Date: 2018/3/17 15:06
	 **/
	public void updateRecommendSort(Integer id,Integer recommendSort);
}
