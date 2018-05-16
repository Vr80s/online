package com.xczhihui.course.service;

import java.util.List;

import com.xczhihui.course.model.MobileBanner;
import com.xczhihui.course.vo.CourseLecturVo;
import com.xczhihui.course.vo.MenuVo;

public interface IMobileBannerService {

	/**
	 * Description：app端banner列表
	 * creed: Talk is cheap,show me the code
	 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	 * @Date: 2018/5/10 11:25
	 **/
	  public List<MobileBanner> selectMobileBannerPage(Integer type);

	  /**
	   * Description：增加点击次数
	   * creed: Talk is cheap,show me the code
	   * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	   * @Date: 2018/5/10 11:25
	   **/
	  void  addClickNum(String id);

	  public List<CourseLecturVo> recommendCourseList(List<MenuVo> menuList,int pageSize);
}
