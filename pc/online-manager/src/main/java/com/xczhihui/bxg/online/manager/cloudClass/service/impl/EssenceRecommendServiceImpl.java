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
		// TODO Auto-generated method stub
		List<String> ids2 = new ArrayList();
		if(isEssence == 1)//如果是推荐
		{
			//校验是否被引用
			String hqlPre="from Course where isDelete=0 and essenceSort = 1";
			List<Course> list= dao.findByHQL(hqlPre);
			if(list.size() > 0){//只有原来大于0才执行
				for(int i = 0;i<ids.length;i++)
				{
					int j = 0;
					Iterator<Course> iterator = list.iterator();
					while(iterator.hasNext()){//剔除本次推荐的与已经推荐的重复的
						
						Course course = iterator.next();
						if(course.getId() == Integer.parseInt(ids[i])){//如果存在就把他剔除掉从list中
							j =1;
						}
					}
					if(j == 0){
						ids2.add(ids[i]);
					}
				}
			}else{
				for(int i=0;i<ids.length;i++)
				{
					ids2.add(ids[i]);
				}
			}
			//已经存在的数量 +  即将添加的数量
            if((list.size()+ids2.size()) > 12){
            	return false;
            }
		}else{//如果是取消推荐
			for(int i=0;i<ids.length;i++)
			{
				ids2.add(ids[i]);
			}
		}
		
		String sql="select ifnull(min(essence_sort),0) from oe_course where  is_delete=0 and essence_sort = 1";
		int i = dao.queryForInt(sql,null);//最小的排序
		
		for(String id:ids2){
			if(id == "" || id == null)
			{
				continue;
			}
			i = i -1;
			String hqlPre="from Course where  isDelete = 0 and id = ?";
	        Course course= dao.findByHQLOne(hqlPre,new Object[] {Integer.valueOf(id)});
            if(course !=null){
            	 course.setEssenceSort(isEssence);
            	 course.setSort(i);
                 dao.update(course);
            }
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
