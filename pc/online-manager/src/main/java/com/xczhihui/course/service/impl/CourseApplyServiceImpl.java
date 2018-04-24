package com.xczhihui.course.service.impl;

import com.xczhihui.anchor.service.AnchorService;
import com.xczhihui.bxg.common.support.cc.util.CCUtils;
import com.xczhihui.bxg.common.support.config.OnlineConfig;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.common.util.enums.ApplyStatus;
import com.xczhihui.bxg.common.util.enums.CourseDismissal;
import com.xczhihui.bxg.common.util.enums.CourseForm;
import com.xczhihui.bxg.common.util.enums.Multimedia;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.course.dao.CourseApplyDao;
import com.xczhihui.course.dao.CourseDao;
import com.xczhihui.course.service.CourseApplyService;
import com.xczhihui.course.service.CourseService;
import com.xczhihui.message.dao.MessageDao;
import com.xczhihui.order.vo.MessageShortVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.vhall.VhallUtil;
import com.xczhihui.vhall.bean.Webinar;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * CourseServiceImpl:课程业务层接口实现类
 *
 * @author Rongcai Kang
 */
@Service
public class CourseApplyServiceImpl extends OnlineBaseServiceImpl implements
		CourseApplyService {

	@Autowired
	private CourseApplyDao courseApplyDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private CourseService courseService;
	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private AnchorService anchorService;
	@Autowired
	private CourseDao courseDao;
	@Autowired
	private CCUtils ccUtils;
	@Value("${vhall.user.id}")
	private String liveVhallUserId;
	@Value("${vhall.callback.url}")
	String vhall_callback_url;
	@Value("${vhall.private.key}")
	String vhall_private_key;

	@Override
	public Page<CourseApplyInfo> findCoursePage(
			CourseApplyInfo courseApplyInfo, int pageNumber, int pageSize) {
		Page<CourseApplyInfo> page = courseApplyDao.findCloudClassCoursePage(
				courseApplyInfo, pageNumber, pageSize);
		return page;
	}

	@Override
	public CourseApplyInfo findCourseApplyById(Integer id) {
		CourseApplyInfo courseApply = courseApplyDao
				.findCourseApplyAndMenuById(id);
		courseApply.setPrice(courseApply.getPrice() * 10);
		if (!courseApply.getCollection()
				&& courseApply.getCourseForm() == CourseForm.VOD.getCode()) {
			String audioStr = "";
			if (courseApply.getMultimediaType() == Multimedia.AUDIO.getCode()) {
				audioStr = "_2";
			}

			String playCode = ccUtils.getPlayCode(courseApply.getCourseResource(), audioStr, "600", "490");

			courseApply.setPlayCode(playCode);
		}
		if (courseApply.getCollection()) {
			List<CourseApplyInfo> courseApplyInfos = courseApplyDao
					.getCourseByCollectionId(courseApply.getId());
			courseApply.setCourseApplyInfoList(courseApplyInfos);
		}
		if ((courseApply.getStatus() == ApplyStatus.NOT_PASS.getCode())
				&& courseApply.getDismissal() != null) {
			courseApply.setDismissalText(CourseDismissal
					.getDismissal(courseApply.getDismissal()));
		}
		return courseApply;
	}

	@Override
	public void savePass(Integer courseApplyId, String userId) {
		CourseApplyInfo courseApply = courseApplyDao
				.findCourseApplyById(courseApplyId);
		if (courseApply.getStatus() != ApplyStatus.UNTREATED.getCode()) {
			throw new RuntimeException("课程已被他人审核");
		}
		if (courseApply.getIsDelete()) {
			throw new RuntimeException("该课程申请被主播重新发起");
		}
		Course collection = savePassCourse(courseApply, userId);
		// 对于专辑，通过时，所有课程都通过
		if (courseApply.getCollection()) {
			deleteCollectionCourseByCollectionId(collection.getId());
			List<CourseApplyInfo> courseApplyInfos = courseApplyDao
					.getCourseDeatilsByCollectionId(courseApply.getId());
			for (int i = 0; i < courseApplyInfos.size(); i++) {
				if (courseApplyInfos.get(i).getStatus() != 1) {
					throw new RuntimeException("专辑中包含未通过课程，无法通过审核");
				}
				Course course = getCourseByApplyId(courseApplyInfos.get(i));
				course.setCollectionCourseSort(courseApplyInfos.get(i)
						.getCollectionCourseSort());
				// 保存专辑-课程关系
				saveCollectionCourse(collection, course);
			}
		}

		String msgId = UUID.randomUUID().toString().replaceAll("-", "");
		MessageShortVo messageShortVo = new MessageShortVo();
		messageShortVo.setUser_id(courseApply.getUserId());
		messageShortVo.setId(msgId);
		messageShortVo.setCreate_person(userId);
		messageShortVo.setType(1);
		String n;
		// 若为专辑
		if (courseApply.getCollection()) {
			n = "专辑";
		} else {
			n = "课程";
		}
		String content = n + "《" + courseApply.getTitle() + "》通过系统审核，可以上架啦！";
		messageShortVo.setContext(content);
		messageDao.saveMessage(messageShortVo);
	}

	private void deleteCollectionCourseByCollectionId(Integer collectionId) {
		String sql = "DELETE FROM `collection_course` WHERE collection_id=:collectionId";
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("collectionId", collectionId);
		dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	private Course getCourseByApplyId(CourseApplyInfo courseApplyInfo) {
		DetachedCriteria dc = DetachedCriteria.forClass(Course.class);
		dc.add(Restrictions.eq("applyId", courseApplyInfo.getId()));
		Course course = dao.findEntity(dc);
		return course;
	}

	private void saveCollectionCourse(Course collection, Course course) {
		String sql = "INSERT INTO collection_course(collection_id,course_id,create_time,collection_course_sort) "
				+ " VALUES (:cId,:courseId,now(),:collectionCourseSort)";
		Map<String, Integer> paramMap = new HashMap<String, Integer>();
		paramMap.put("cId", collection.getId());
		paramMap.put("courseId", course.getId());
		paramMap.put("collectionCourseSort", course.getCollectionCourseSort());

		dao.getNamedParameterJdbcTemplate().update(sql, paramMap);
	}

	@Override
	public void saveNotPass(CourseApplyInfo courseApplyInfo, String userId) {
		CourseApplyInfo courseApply = courseApplyDao
				.findCourseApplyById(courseApplyInfo.getId());
		if (courseApply.getStatus() != ApplyStatus.UNTREATED.getCode()) {
			throw new RuntimeException("课程已被他人审核");
		}
		if (courseApply.getIsDelete()) {
			throw new RuntimeException("该课程申请被主播重新发起");
		}
		courseApply.setDismissal(courseApplyInfo.getDismissal());
		courseApply.setDismissalRemark(courseApplyInfo.getDismissalRemark());
		saveNotPassCourse(courseApply, userId);
		// 发送消息通知
		String msgId = UUID.randomUUID().toString().replaceAll("-", "");
		MessageShortVo messageShortVo = new MessageShortVo();
		messageShortVo.setUser_id(courseApply.getUserId());
		messageShortVo.setId(msgId);
		messageShortVo.setCreate_person(userId);
		messageShortVo.setType(1);
		String n;
		// 若为打款
		if (courseApply.getCollection()) {
			n = "专辑";
		} else {
			n = "课程";
		}
		String content = n + "《" + courseApply.getTitle()
				+ "》未通过系统审核，请重新编辑后提交！原因："
				+ CourseDismissal.getDismissal(courseApply.getDismissal())
				+ " " + courseApply.getDismissalRemark();
		messageShortVo.setContext(content);
		messageDao.saveMessage(messageShortVo);
	}

	@Override
	public Page<CourseApplyResource> findCourseApplyResourcePage(
			CourseApplyResource courseApplyResource, int currentPage,
			int pageSize) {
		Page<CourseApplyResource> page = courseApplyDao
				.findCourseApplyResourcePage(courseApplyResource, currentPage,
						pageSize);
		for (CourseApplyResource car : page.getItems()) {
			String audioStr = "";
			if (car.getMultimediaType() == 2) {
				audioStr = "_2";
			}

			String playCode = ccUtils.getPlayCode(car.getResource(), audioStr, "600", "490");

			car.setPlayCode(playCode);
		}
		return page;
	}

	@Override
	public void deleteOrRecoveryCourseApplyResource(
			Integer courseApplyResourceId, Boolean delete) {
		DetachedCriteria dc = DetachedCriteria
				.forClass(CourseApplyResource.class);
		dc.add(Restrictions.eq("id", courseApplyResourceId));
		CourseApplyResource courseApplyResource = dao.findEntity(dc);
		courseApplyResource.setDeleted(delete);
		dao.update(courseApplyResource);
	}

	@Override
	public Page<CourseApplyInfo> findCoursePageByUserId(
			CourseApplyInfo courseApplyInfo, int pageNumber, int pageSize) {
		Page<CourseApplyInfo> page = courseApplyDao.findCoursePageByUserId(
				courseApplyInfo, pageNumber, pageSize);
		return page;
	}

	@Override
	public void updateRecommendSort(Integer id, Integer recommendSort) {
		String hqlPre = "from CourseAnchor where id = ?";
		CourseAnchor courseAnchor = dao.findByHQLOne(hqlPre,
				new Object[] { id });
		if (courseAnchor != null) {
			courseAnchor.setRecommendSort(recommendSort);
			dao.update(courseAnchor);
		}
	}

	private void saveNotPassCourse(CourseApplyInfo courseApply, String userId) {
		courseApply.setStatus(ApplyStatus.NOT_PASS.getCode());
		courseApply.setReviewPerson(userId);
		courseApply.setReviewTime(new Date());
		dao.update(courseApply);
	}

	public Course savePassCourse(CourseApplyInfo courseApply, String userId) {
		courseApply.setStatus(ApplyStatus.PASS.getCode());
		courseApply.setReviewPerson(userId);
		courseApply.setReviewTime(new Date());
		dao.update(courseApply);

		return saveCourseApply2course(courseApply);

	}

	private Course saveCourseApply2course(CourseApplyInfo courseApply) {
		courseService.checkName(null, courseApply.getTitle(),
				courseApply.getOldApplyInfoId());
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT IFNULL(MAX(sort),0) as sort FROM oe_course ";
		List<Course> temp = dao.findEntitiesByJdbc(Course.class, sql, params);
		int sort;
		if (temp.size() > 0) {
			sort = temp.get(0).getSort().intValue() + 1;
		} else {
			sort = 1;
		}
		// 当课程存在密码时，设置的当前价格失效，改为0.0
		if (courseApply.getPassword() != null
				&& !"".equals(courseApply.getPassword().trim())) {
			courseApply.setPrice(0.0);
		}
		Course course = null;
		if (courseApply.getOldApplyInfoId() != null) {
			course = courseDao
					.findOneEntitiyByProperty(Course.class, "applyId",
							Integer.valueOf(courseApply.getOldApplyInfoId()));
		}
		if (course == null) {
			course = new Course();
		}
		// 课程名称
		course.setGradeName(courseApply.getTitle());
		// 课程副标题
		course.setSubtitle(courseApply.getSubtitle());
		// 学科id
		course.setMenuId(Integer.valueOf(courseApply.getCourseMenu()));
		// 课程时长
		course.setCourseLength(courseApply.getCourseLength());
		// 原价格
		course.setOriginalCost(courseApply.getPrice());
		// 现价格
		course.setCurrentPrice(courseApply.getPrice());
		// 推荐值
		course.setRecommendSort(0);

		if (0 == course.getCurrentPrice()) {
			// 免费
			course.setIsFree(true);
		} else {
			// 付费
			course.setIsFree(false);
		}
		// 排序
		course.setSort(sort);
		// 请填写一个基数，统计的时候加上这个基数
		course.setLearndCount(0);
		// 当前登录人
		course.setCreatePerson(ManagerUserUtil.getUsername());

		// 状态
		course.setStatus('0' + "");
		// 课程介绍
		course.setCourseDetail(courseApply.getCourseDetail());

		// 增加密码和老师
		course.setCoursePwd(courseApply.getPassword());
		course.setUserLecturerId(courseApply.getUserId() + "");
		course.setType(courseApply.getCourseForm());

		// zhuwenbao-2018-01-09 设置课程的展示图
		// findCourseById 是直接拿小图 getCourseDetail是从大图里拿 同时更新两个 防止两者数据不一样
		course.setSmallImgPath(courseApply.getImgPath());
		course.setBigImgPath(courseApply.getImgPath());

		course.setCourseOutline(courseApply.getCourseOutline());
		course.setLecturer(courseApply.getLecturer());
		course.setLecturerDescription(courseApply.getLecturerDescription());

		course.setStatus("0");
		if (course.getType() == CourseForm.OFFLINE.getCode()) {
			// 线下课程
			course.setAddress(courseApply.getAddress());
			course.setStartTime(courseApply.getStartTime());
			course.setEndTime(courseApply.getEndTime());
			course.setCity(courseApply.getCity());
			// 添加城市管理
			courseService.addCourseCity(course.getCity());
		} else if (course.getType() == CourseForm.LIVE.getCode()) {
			course.setStartTime(courseApply.getStartTime());
			course.setDirectSeeding(3);
			if(StringUtils.isBlank(course.getDirectId())){
				String webinarId = createWebinar(course);
				course.setDirectId(webinarId);
			}else{
				updateWebinar(course);
			}
			// 将直播课设置为预告
			course.setLiveStatus(2);
		} else if (course.getType() == CourseForm.VOD.getCode()) {
			// yuruixin-2017-08-16
			// 课程资源
			course.setMultimediaType(courseApply.getMultimediaType());
			course.setDirectId(courseApply.getCourseResource());
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
		if (course.getId() != null) {
			// 若course有id，说明该申请来自一个已经审核通过的课程，则更新
			dao.update(course);
		} else {
			// 当前时间
			course.setCreateTime(new Date());
			dao.save(course);
		}
		return course;
	}

	/**
	 * Description：创建一个直播活动 creed: Talk is cheap,show me the code
	 *
	 * @author name：yuxin <br>
	 *         email: yuruixin@ixincheng.com
	 * @Date: 下午 8:52 2018/1/22 0022
	 **/
	public String createWebinar(Course entity) {
		Webinar webinar = new Webinar();
		webinar.setSubject(entity.getGradeName());
		webinar.setIntroduction(entity.getDescription());
		Date start = entity.getStartTime();
		String start_time = start.getTime() + "";
		start_time = start_time.substring(0, start_time.length() - 3);
		webinar.setStart_time(start_time);
		CourseAnchor courseAnchor = anchorService
				.findCourseAnchorByUserId(entity.getUserLecturerId());
		webinar.setHost(courseAnchor.getName());
		Integer directSeeding = entity.getDirectSeeding();
		if (directSeeding == null) {
			directSeeding = 3;
		}
		webinar.setLayout(directSeeding.toString());
		OnlineUser u = onlineUserService.getOnlineUserByUserId(entity
				.getUserLecturerId());
		webinar.setUser_id(u.getVhallId());
		String webinarId = VhallUtil.createWebinar(webinar);

		VhallUtil.setActiveImage(webinarId,
				VhallUtil.downUrlImage(entity.getSmallImgPath(), "image"));
		VhallUtil.setCallbackUrl(webinarId, vhall_callback_url,
				vhall_private_key);
		return webinarId;
	}

	public String updateWebinar(Course entity) {
		//更新封面
		VhallUtil.setActiveImage(entity.getDirectId(), VhallUtil.downUrlImage(entity.getSmallImgPath(), "image"));
		Webinar webinar = new Webinar();
		webinar.setId(entity.getDirectId()+"");
		webinar.setSubject(entity.getGradeName());
		webinar.setIntroduction(entity.getDescription());
		Date start = entity.getStartTime();
		String start_time = start.getTime() + "";
		start_time = start_time.substring(0, start_time.length() - 3);
		webinar.setStart_time(start_time);
		webinar.setHost(entity.getLecturer());
		webinar.setLayout(entity.getDirectSeeding()+"");
		return VhallUtil.updateWebinar(webinar);
	}
}
