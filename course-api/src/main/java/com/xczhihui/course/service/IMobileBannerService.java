package com.xczhihui.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.course.model.MobileBanner;

public interface IMobileBannerService {

	/**
	 * Description：app端banner列表
	 * creed: Talk is cheap,show me the code
	 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	 * @Date: 2018/5/10 11:25
	 **/
	  public Page<MobileBanner> selectMobileBannerPage(Page<MobileBanner> page, Integer type);

	  /**
	   * Description：增加点击次数
	   * creed: Talk is cheap,show me the code
	   * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
	   * @Date: 2018/5/10 11:25
	   **/
	  void  addClickNum(String id);
}
