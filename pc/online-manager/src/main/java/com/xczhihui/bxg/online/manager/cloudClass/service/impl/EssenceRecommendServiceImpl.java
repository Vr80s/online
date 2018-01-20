package com.xczhihui.bxg.online.manager.cloudClass.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.common.domain.Lecturer;
import com.xczhihui.bxg.online.common.domain.Menu;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseDao;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseSubscribeDao;
import com.xczhihui.bxg.online.manager.cloudClass.dao.EssenceRecommendDao;
import com.xczhihui.bxg.online.manager.cloudClass.dao.PublicCourseDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import com.xczhihui.bxg.online.manager.cloudClass.service.EssenceRecommendService;
import com.xczhihui.bxg.online.manager.cloudClass.service.LecturerService;
import com.xczhihui.bxg.online.manager.cloudClass.service.PublicCourseService;
import com.xczhihui.bxg.online.manager.cloudClass.vo.ChangeCallbackVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.CourseVo;
import com.xczhihui.bxg.online.manager.cloudClass.vo.MenuVo;
import com.xczhihui.bxg.online.manager.user.service.OnlineUserService;
import com.xczhihui.bxg.online.manager.utils.subscribe.Subscribe;
import com.xczhihui.bxg.online.manager.vhall.VhallUtil;
import com.xczhihui.bxg.online.manager.vhall.bean.Webinar;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("essenceRecommendServiceImpl")
public class EssenceRecommendServiceImpl extends OnlineBaseServiceImpl implements EssenceRecommendService {
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
	public Page<CourseVo> findCoursePage(CourseVo courseVo, int pageNumber,int pageSize) {
		Page<CourseVo> page = essenceRecommendDao.findEssenceRecCoursePage(courseVo, pageNumber, pageSize);
    	return page;
	}

	@Override
	public boolean updateEssenceRec(String[] ids, int isEssence) {
		for(String id:ids){
			if(id == "" || id == null)
			{
				continue;
			}
			/*String hqlPre="from Course where  isDelete = 0 and id = ?";
			Course course= dao.findByHQLOne(hqlPre,new Object[] {Integer.valueOf(id)});
			if(course !=null){
				course.setIsRecommend(isEssence);
				dao.update(course);
			}*/
		}
		return true;
	}

	@Override
	public void updateSortUp(Integer id) {
		 String hqlPre="from Course where  isDelete=0 and id = ?";
         Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer coursePreSort=coursePre.getEssenceSort();
         
         String hqlNext="from Course where essenceSort > (select essenceSort from Course where id= ? )  and isDelete=0 and status=1  order by essenceSort asc";
         Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer courseNextSort=courseNext.getEssenceSort();
         
         coursePre.setEssenceSort(courseNextSort);
         courseNext.setEssenceSort(coursePreSort);
         
         dao.update(coursePre);
         dao.update(courseNext);
		
	}
	@Override
	public void updateSortDown(Integer id) {
		 String hqlPre="from Course where  isDelete=0 and id = ?";
         Course coursePre= dao.findByHQLOne(hqlPre,new Object[] {id});
         Integer coursePreSort=coursePre.getEssenceSort();
         String hqlNext="from Course where essenceSort < (select essenceSort from Course where id= ? )  and isDelete=0 and status=1  order by essenceSort desc";
         Course courseNext= dao.findByHQLOne(hqlNext,new Object[] {id});
         Integer courseNextSort=courseNext.getEssenceSort();
         
         coursePre.setEssenceSort(courseNextSort);
         courseNext.setEssenceSort(coursePreSort);
         
         dao.update(coursePre);
         dao.update(courseNext);
	}

}
