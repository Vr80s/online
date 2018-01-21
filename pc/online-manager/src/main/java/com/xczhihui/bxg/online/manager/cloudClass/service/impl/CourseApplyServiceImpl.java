package com.xczhihui.bxg.online.manager.cloudClass.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.web.auth.UserHolder;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.bxg.online.common.enums.Dismissal;
import com.xczhihui.bxg.online.common.utils.OnlineConfig;
import com.xczhihui.bxg.online.manager.cloudClass.dao.CourseApplyDao;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseApplyService;
import com.xczhihui.bxg.online.manager.cloudClass.service.CourseService;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 *   CourseServiceImpl:课程业务层接口实现类
 *   @author Rongcai Kang
 */
@Service
public class CourseApplyServiceImpl extends OnlineBaseServiceImpl implements CourseApplyService {

    @Autowired
    private CourseApplyDao courseApplyDao;
    @Autowired
    private CourseService courseService;

    
    @Override
	public Page<CourseApplyInfo> findCoursePage(CourseApplyInfo courseApplyInfo,  int pageNumber, int pageSize) {
    	Page<CourseApplyInfo> page = courseApplyDao.findCloudClassCoursePage(courseApplyInfo, pageNumber, pageSize);
    	return page;
	}

	@Override
	public CourseApplyInfo findCourseApplyById(Integer id) {
		CourseApplyInfo courseApply = courseApplyDao.findCourseApplyAndMenuById(id);
		if(!courseApply.getCollection()){
			String audioStr="";
			if(courseApply.getMultimediaType()==2){
				audioStr = "_2";
			}
			String src = "https://p.bokecc.com/flash/single/"+ OnlineConfig.CC_USER_ID+"_"+courseApply.getCourseResource()+"_false_"+OnlineConfig.CC_PLAYER_ID+"_1"+audioStr+"/player.swf";
			String uuid = UUID.randomUUID().toString().replace("-", "");
			String playCode = "";
			playCode+="<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" ";
			playCode+="		codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\" ";
			playCode+="		width=\"600\" ";
			playCode+="		height=\"490\" ";
			playCode+="		id=\""+uuid+"\">";
			playCode+="		<param name=\"movie\" value=\""+src+"\" />";
			playCode+="		<param name=\"allowFullScreen\" value=\"true\" />";
			playCode+="		<param name=\"allowScriptAccess\" value=\"always\" />";
			playCode+="		<param value=\"transparent\" name=\"wmode\" />";
			playCode+="		<embed src=\""+src+"\" ";
			playCode+="			width=\"600\" height=\"490\" name=\""+uuid+"\" allowFullScreen=\"true\" ";
			playCode+="			wmode=\"transparent\" allowScriptAccess=\"always\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" ";
			playCode+="			type=\"application/x-shockwave-flash\"/> ";
			playCode+="	</object>";
			courseApply.setPlayCode(playCode);
		}

		if(courseApply.getCollection()){
			List<CourseApplyInfo> courseApplyInfos = courseApplyDao.getCourseByCollectionId(courseApply.getId());
			courseApply.setCourseApplyInfoList(courseApplyInfos);
		}
		if(courseApply.getStatus()==2){
			courseApply.setDismissalText(Dismissal.getDismissal(courseApply.getDismissal()));
		}
		return courseApply;
	}

	@Override
	public void savePass(Integer courseApplyId, String userId) {
		CourseApplyInfo courseApply = courseApplyDao.findCourseApplyById(courseApplyId);
		Course collection = passCourse(courseApply,userId);
		//对于专辑，通过时，所有课程都通过
		if(courseApply.getCollection()){
			List<CourseApplyInfo> courseApplyInfos = courseApplyDao.getCourseDeatilsByCollectionId(courseApply.getId());
			for (int i = 0; i < courseApplyInfos.size(); i++) {
				if (courseApplyInfos.get(i).getStatus()!=1){
					Course course = passCourse(courseApplyInfos.get(i),userId);
					//保存专辑-课程关系
					saveCollectionCourse(collection,course);
				}else{
					Course course = getCourseByApplyId(courseApplyInfos.get(i));
					//保存专辑-课程关系
					saveCollectionCourse(collection,course);
				}
			}
		}
	}

	private Course getCourseByApplyId(CourseApplyInfo courseApplyInfo) {
		DetachedCriteria dc = DetachedCriteria.forClass(Course.class);
		dc.add(Restrictions.eq("applyId", courseApplyInfo.getId()));
		Course course = dao.findEntity(dc);
		return course;
	}

	private void saveCollectionCourse(Course collection, Course course) {
		String sql = "insert into collection_course(collection_id,course_id,create_time) "
				+ " values (:cId,:courseId,now())";
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("cId",collection.getId());
		paramMap.put("courseId",course.getId());
		dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	@Override
	public void saveNotPass(CourseApplyInfo courseApplyInfo, String userId) {
		CourseApplyInfo courseApply = courseApplyDao.findCourseApplyById(courseApplyInfo.getId());
		courseApply.setDismissal(courseApplyInfo.getDismissal());
		courseApply.setDismissalRemark(courseApplyInfo.getDismissalRemark());
		notPassCourse(courseApply,userId);
		//对于专辑，拒绝时，仅拒绝专辑，不拒绝课程
//		if(courseApply.getCollection()){
//			List<CourseApplyInfo> courseApplyInfos = courseApplyDao.getCourseByCollectionId(courseApply.getId());
//			for (int i = 0; i < courseApplyInfos.size(); i++) {
//				//若该申请未被审核通过
//				if (courseApplyInfos.get(i).getStatus()!=1){
//					notPassCourse(courseApplyInfos.get(i),userId);
//				}
//			}
//		}
	}

	private void notPassCourse(CourseApplyInfo courseApply, String userId) {
		courseApply.setStatus(2);
		courseApply.setReviewPerson(userId);
		courseApply.setReviewTime(new Date());
		dao.update(courseApply);
	}

	public Course passCourse(CourseApplyInfo courseApply, String userId){
		courseApply.setStatus(1);
		courseApply.setReviewPerson(userId);
		courseApply.setReviewTime(new Date());
		dao.update(courseApply);

		return courseApply2course(courseApply);

	}

	private Course courseApply2course(CourseApplyInfo courseApply) {
		courseService.checkName(null,courseApply.getTitle());
		// TODO Auto-generated method stub
		Map<String,Object> params=new HashMap<String,Object>();
		String sql="SELECT IFNULL(MAX(sort),0) as sort FROM oe_course ";
		List<Course> temp = dao.findEntitiesByJdbc(Course.class, sql, params);
		int sort;
		if(temp.size()>0){
			sort=temp.get(0).getSort().intValue()+1;
		}else{
			sort=1;
		}
		//当课程存在密码时，设置的当前价格失效，改为0.0
		if(courseApply.getPassword()!=null && !"".equals(courseApply.getPassword().trim())){
			courseApply.setPrice(0.0);
		}
		Course course = new Course();
		//课程名称
		course.setGradeName(courseApply.getTitle());
		//课程副标题
		course.setSubtitle(courseApply.getSubtitle());
		//学科id
		course.setMenuId(Integer.valueOf(courseApply.getCourseMenu()));
		//课程时长
		course.setCourseLength(courseApply.getCourseLength());
		//原价格
		course.setOriginalCost(courseApply.getPrice());
		//现价格
		course.setCurrentPrice(courseApply.getPrice());

		if(0==course.getCurrentPrice()){
			//免费
			course.setIsFree(true);
		}else{
			//收费
			course.setIsFree(false);
		}
		//排序
		course.setSort(sort);
		//请填写一个基数，统计的时候加上这个基数
		course.setLearndCount(0);
		//当前登录人
		course.setCreatePerson(UserHolder.getCurrentUser().getLoginName());
		//当前时间
		course.setCreateTime(new Date());
		//状态
		course.setStatus('0'+"");
		//课程描述
//		course.setDescription(courseApply.getCourseDescription());
		course.setCourseDetail(courseApply.getCourseDetail());
		//yuruixin-2017-08-16
		course.setMultimediaType(courseApply.getMultimediaType());

		//增加密码和老师
		course.setCoursePwd(courseApply.getPassword());
		course.setUserLecturerId(courseApply.getUserId()+"");
		if(courseApply.getCourseForm()==3){
			//线下课程
			course.setOnlineCourse(1);
			course.setAddress(courseApply.getAddress());
			course.setStartTime(courseApply.getStartTime());
			course.setEndTime(courseApply.getEndTime());
		}else{
			course.setOnlineCourse(0);
		}

		// zhuwenbao-2018-01-09 设置课程的展示图
		// findCourseById 是直接拿小图 getCourseDetail是从大图里拿 同时更新两个 防止两者数据不一样
		course.setSmallImgPath(courseApply.getImgPath());
		course.setBigImgPath(courseApply.getImgPath());
		//课程资源
		course.setDirectId(courseApply.getCourseResource());
		course.setCourseOutline(courseApply.getCourseOutline());
		course.setLecturer(courseApply.getLecturer());
		course.setLecturerDescription(courseApply.getLecturerDescription());
		if(courseApply.getSale()){
			course.setStatus("1");
		}else{
			course.setStatus("0");
		}
		course.setIsRecommend(0);
		course.setClassRatedNum(0);
		course.setServiceType(0);
		course.setLiveSource(2);
		course.setDescriptionShow(0);
		course.setApplyId(courseApply.getId());
		course.setCollectionCourseSort(courseApply.getCollectionCourseSort());
		course.setCollection(courseApply.getCollection());
		course.setCourseNumber(courseApply.getCourseNumber());
		dao.save(course);
		return course;
	}


}
