package com.xczhihui.course.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.course.dao.EssenceRecommendDao;
import com.xczhihui.course.service.EssenceRecommendService;
import com.xczhihui.course.vo.CourseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("essenceRecommendServiceImpl")
public class EssenceRecommendServiceImpl extends OnlineBaseServiceImpl
		implements EssenceRecommendService {
	@Autowired
	private EssenceRecommendDao essenceRecommendDao;

	@Value("${env.flag}")
	private String envFlag;
	@Value("${vhall.user.id}")
	private String liveVhallUserId;
	@Value("${vhall.callback.url}")
	String vhall_callback_url;
	@Value("${vhall.private.key}")
	String vhall_private_key;

	@Override
	public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber,
			int pageSize) {
		Page<CourseVo> page = essenceRecommendDao.findEssenceRecCoursePage(
				courseVo, pageNumber, pageSize);
		return page;
	}


}
