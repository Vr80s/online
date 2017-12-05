package com.xczh.consumer.market.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.dao.BasicSimpleDao;
import com.xczh.consumer.market.dao.OnlineCourseMapper;
import com.xczh.consumer.market.dao.OnlineLecturerMapper;
import com.xczh.consumer.market.dao.OnlineUserMapper;
import com.xczh.consumer.market.service.*;
import com.xczh.consumer.market.utils.JdbcUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.TimeUtil;
import com.xczh.consumer.market.vo.CourseLecturVo;
import com.xczh.consumer.market.wxpay.util.WeihouInterfacesListUtil;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;

import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class OnlineCourseServiceImpl extends BasicSimpleDao implements OnlineCourseService {

	@Autowired
	private OnlineCourseMapper courseMapper;
	
	@Autowired
	private OnlineUserMapper onlineUserMapper;

	@Autowired
	private OnlineLecturerMapper onlineLecturerMapper;
	
	@Autowired
	private OnlineWebService onlineWebService;
	
	@Autowired
	private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;

	
	@Autowired
	private OnlineUserService onlineUserService;
	
	@Autowired
	private FocusService focusService;
	
	
	@Override
	public List<CourseLecturVo> findLiveListInfo(Integer start_page,
                                                 Integer page_size, String queryParam) throws SQLException {
		/*
		 * 从teach_method表中获取直播方式 oe_course smallimg_path 缩略图 start_time end_time
		 * 只有公开课有直播开始时间和结束时间。其余的都是直接播放的啊。点播才有章节这个部分呢，直播和公开课是没有了啊。 gradeName 课程名称
		 * 讲师oe_lecturer 讲师课程中间表 course_r_lecturer 需要从这两个表中得到讲师名字和讲师头像 oe_video
		 * 获取视频id。从视频id中获取在线观看人数 多少人在看这个视频了 WeihouInterfacesListUtil
		 */
		List<CourseLecturVo> newList = new ArrayList<CourseLecturVo>();
		
		List<CourseLecturVo> list = courseMapper.findLiveListInfo(start_page,
				page_size, queryParam);
		//根据用户id和课程id
		//这里紧紧是判断密码是否为null  -- 没有判断用户是否已经输入了
		newList.addAll(list);
		return list;
	}

	@Override
	public CourseLecturVo liveDetailsByCourseId(int course_id, String userId)
			throws SQLException {
		CourseLecturVo courseLecturVo = courseMapper.liveDetailsByCourseId(course_id);
	    /**
	     * 当前直播状态:  0 直播已结束   1 直播还未开始   2 点播 
	     */
        //lineState: 0 直播已结束 1 直播还未开始 2 正在直播		 
		//直播状态1.直播中，2预告，3直播结束
		//0 直播已结束 1 直播还未开始 2 正在直播
		 if(courseLecturVo.getLineState() == 2){
			/**
			 * 获取当前预约人数
			 */
			Integer subCount = selectSubscribeInfoNumberCourse(course_id);
			courseLecturVo.setCountSubscribe(subCount);
			/**
			 * 判断自己是否预约了
			 */
			if(userId!=null){
				Integer isSubscribe = selectSubscribeInfoIs(course_id,userId);
				if(isSubscribe == 0){//未预约
					courseLecturVo.setIsSubscribe(isSubscribe);
				}else{
					courseLecturVo.setIsSubscribe(1);
				}
			}
		}
		return courseLecturVo;
	}

	@Override
	public ResponseObject courseIsBuy(OnlineUser user, int course_id) {
		String user_id = user.getId(); 
		if(user_id!=null && !"".equals(user_id) && (!"null".equals(user_id))){
			StringBuffer isbuy = new StringBuffer("");
			isbuy.append(" select oo.id from oe_order oo,oe_order_detail ood "
					+ "where oo.id=ood.order_id and oo.order_status=1 and oo.user_id=? and ood.course_id=?");
			Map<String, Object> map;
			try {
				map = super.query(JdbcUtil.getCurrentConnection(), isbuy.toString(), new MapHandler(),user_id,course_id);
				if(map!=null && map.size()>0){
					Object o_id = map.get("id");
					if(o_id!=null){
						return ResponseObject.newSuccessResponseObject("已经购买");
					}else{
						return ResponseObject.newErrorResponseObject("还未购买此课程");
					}
				}else{                                                                                        
					return ResponseObject.newErrorResponseObject("还未购买此课程");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			return ResponseObject.newErrorResponseObject("获取用户信息有误");
		}
		return ResponseObject.newErrorResponseObject("请求异常");
	}
   
	@Override
	public ResponseObject courseIsPwd(OnlineUser user, int course_id) {
		String user_id = user.getId(); 
		if(user_id!=null && !"".equals(user_id) && (!"null".equals(user_id))){
			StringBuffer isbuy = new StringBuffer("");
			isbuy.append(" select oo.id from oe_order oo,oe_order_detail ood "
					+ "where oo.id=ood.order_id and oo.order_status=1 and oo.user_id=? and ood.course_id=?");
			Map<String, Object> map;
			try {
				map = super.query(JdbcUtil.getCurrentConnection(), isbuy.toString(), new MapHandler(),user_id,course_id);
				if(map!=null && map.size()>0){
					Object o_id = map.get("id");
					if(o_id!=null){
						ResponseObject.newSuccessResponseObject("已经购买");
					}else{
						ResponseObject.newErrorResponseObject("还未购买此课程");
					}
				}else{                                                                                        
					ResponseObject.newErrorResponseObject("还未购买此课程");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			ResponseObject.newErrorResponseObject("获取用户信息有误");
		}
		return ResponseObject.newSuccessResponseObject("请求异常");
	}

	@Override
	public ResponseObject saveCoursePwdAndConfirm(OnlineUser user, int course_id,
                                                  String course_pwd) throws SQLException {
		//首先判断密码正确不
		StringBuffer isbuy = new StringBuffer("");
		isbuy.append("select course_pwd "
				+ " from oe_course where id = ?");
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), isbuy.toString(), new MapHandler(),course_id);
		if(map!=null && map.size()>0){
			Object coursePwd = map.get("course_pwd");
			if(coursePwd!=null && coursePwd.toString().equals(course_pwd)){
				Object type = map.get("type");
				/**
				 * 如果密码认证成功了。添加这个情况
				 */
				onlineWebService.saveEntryVideo(course_id,user);
				return	ResponseObject.newSuccessResponseObject("密码认证成功");
			}else{
				return ResponseObject.newErrorResponseObject("密码认证失败");
			}
		}else{
			return ResponseObject.newErrorResponseObject("获取课程信息有误");
		}
	}
	@Override
	public ResponseObject courseIsConfirmPwd(OnlineUser user, int course_id)
			throws SQLException {
		StringBuffer isPwd = new StringBuffer("");
		isPwd.append("select course_pwd  from course_pwd_user where course_id = ? and user_id = ? order by insert_date desc limit 1");
		Map<String, Object> mapPwd = super.query(JdbcUtil.getCurrentConnection(), isPwd.toString(), new MapHandler(),course_id,user.getId());
		if(mapPwd!=null && mapPwd.size()>0){
			Object course_pwd = mapPwd.get("course_pwd");
			String sql = "select c.course_pwd "
					+ " from oe_course as c where  c.id = ?";
			Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql, new MapHandler(),course_id);
			if(map!=null && map.size()>0){
				Object pwd = map.get("course_pwd");
				if(course_pwd!=null && pwd!=null && course_pwd.toString().equals(pwd.toString())){
					Object type = map.get("type");
					onlineWebService.saveEntryVideo(course_id,user);
					return ResponseObject.newSuccessResponseObject("认证通过");
				}else if(null ==course_pwd || pwd ==null){
					return ResponseObject.newErrorResponseObject("还需要进行密码认证");
				}
			}
		}
		return ResponseObject.newErrorResponseObject("需要进行密码认证");
	}
	
	
	public ResponseObject common1(OnlineUser user, int course_id)
			throws SQLException {
		StringBuffer isPwd = new StringBuffer("");
		isPwd.append("select course_pwd  from course_pwd_user where course_id = ? and user_id = ? order by insert_date desc limit 1");
		Map<String, Object> mapPwd = super.query(JdbcUtil.getCurrentConnection(), isPwd.toString(), new MapHandler(),course_id,user.getId());
		if(mapPwd!=null && mapPwd.size()>0){
			Object course_pwd = mapPwd.get("course_pwd");
			String sql = "select c.course_pwd "
					+ " from oe_course as c where  c.id = ?";
			Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql, new MapHandler(),course_id);
			if(map!=null && map.size()>0){
				Object pwd = map.get("course_pwd");
				if(course_pwd!=null && pwd!=null && course_pwd.toString().equals(pwd.toString())){
					Object type = map.get("type");
					onlineWebService.saveEntryVideo(course_id,user);
					return ResponseObject.newSuccessResponseObject("认证通过");
				}else if(null ==course_pwd || pwd ==null){
					return ResponseObject.newErrorResponseObject("还需要进行密码认证");
				}
			}
		}
		return ResponseObject.newErrorResponseObject("需要进行密码认证");
	}
	
	
	

	@Override
	public ResponseObject courseStatus(OnlineUser user, int course_id)
			throws SQLException {
		// TODO Auto-generated method stub
		
		CourseLecturVo courseLecturVo = courseMapper.liveDetailsByCourseId(course_id);
		/**
		 * 判断用户是否需要密码或者付费
		 */
		/*if(courseLecturVo.getWatchState()==2){ //是否已经认证了密码了
			ResponseObject resp = courseIsConfirmPwd(user,course_id);
			if(resp.isSuccess()){//认证通过
				courseLecturVo.setWatchState(0);
			}
		}else if(courseLecturVo.getWatchState()==1){//是否已经付过费了
			ResponseObject resp = courseIsBuy(user,course_id);
			if(resp.isSuccess()){//已经付过费了
				courseLecturVo.setWatchState(0);
			}
		}*/
		if(courseLecturVo.getWatchState()!=0){
			if(courseLecturVo.getUserId().equals(user.getId()) || onlineWebService.getLiveUserCourse(course_id,user.getId()).size()>0){
		       //System.out.println("同学,当前课程您已经报名了!");
		       courseLecturVo.setWatchState(0);    
		    };
		}
		/**
		 * 判断自己是否预约了
		 */
		Integer isSubscribe = selectSubscribeInfoIs(course_id,user.getId());
		if(isSubscribe == 0){//未预约
			courseLecturVo.setIsSubscribe(isSubscribe);
		}else{
			courseLecturVo.setIsSubscribe(1);
		}
		/**
		 * 获取当前预约人数
		 */
		Integer subCount = selectSubscribeInfoNumberCourse(course_id);
		courseLecturVo.setCountSubscribe(subCount);
		
		/**
		 * 是否已经关注了这个主播：0 未关注  1已关注
		 */
		Integer isFours  = focusService.myIsFourslecturer(user.getId(), courseLecturVo.getUserId());
		courseLecturVo.setIsfocus(isFours);
		return ResponseObject.newSuccessResponseObject(courseLecturVo);
	}

	@Override
	public CourseLecturVo get(int course_id) throws SQLException {
		return courseMapper.bunchDetailsByCourseId(course_id);
	}

	/**
	 * 点播课程和直播课程的合集
	 */
	@Override
	public List<CourseLecturVo> liveAndBunchAndAudio(String lecturerId, Integer pageNumber,
			Integer pageSize,Integer type) throws SQLException {
		
	    StringBuilder common = new StringBuilder();
	    if(type == 1){
	    	common.append("select c.id as id,c.grade_name as gradeName,c.smallimg_path as smallImgPath,");
	 	    common.append("c.start_time as startTime,c.end_time as endTime,");
	 	    common.append("c.learnd_count as learndCount,(c.course_length*3600) as courseLength,");
	 	    common.append("c.original_cost as originalCost,c.current_price as currentPrice,");
	 	    common.append(" if(c.course_pwd is not null,2,if(c.is_free =0,1,0)) as watchState, ");  // 观看状态  
	 	    common.append(" IF(c.type is not null,1,if(c.multimedia_type=1,2,3)) as type, "); //类型 
	 	    common.append(" ou.small_head_photo as headImg,ou.name as name, ");
	 	    common.append(" c.live_status as  lineState ");
	    }else{
	    	common.append(" select oc.id as id,oc.grade_name as gradeName,ocm.img_url as smallImgPath,");
	 	    common.append(" oc.start_time as startTime,oc.end_time as endTime, ");
	 	    common.append(" oc.learnd_count as learndCount,");
	 	    common.append(" (select sum(time_to_sec(CONCAT('00:',video_time))) from  oe_video where course_id = oc.id) as courseLength, ");
	 	    common.append(" oc.original_cost as originalCost,oc.current_price as currentPrice, ");
	 	    common.append(" if(oc.course_pwd is not null,2,if(oc.is_free =0,1,0)) as watchState, ");  // 观看状态  
	 	    common.append(" IF(oc.type is not null,1,if(oc.multimedia_type=1,2,3)) as type, "); //类型 
	 	    common.append(" ou.small_head_photo as headImg,ou.name as name, ");
	 	    common.append(" oc.live_status as  lineState ");
	    }
	    /**
		 * 得到直播
		 */
		StringBuilder sql = new StringBuilder("");  
		if(type == 1){ //直播啦
			sql.append(" from oe_course c,oe_user ou ");
			sql.append(" where  c.user_lecturer_id = ou.id  and c.user_lecturer_id = ? and c.is_delete=0 and c.status = 1 and c.type=1  ");
		}else if(type == 2){
			sql.append(" from oe_course oc, oe_course_mobile ocm,oe_user ou ");
			sql.append(" where oc.user_lecturer_id = ou.id and oc.user_lecturer_id = ? and "
					+ " oc.id=ocm.course_id and oc.is_delete=0 and oc.status=1 and oc.multimedia_type =1 ");//
		}else if(type == 3){
			sql.append(" from oe_course oc, oe_course_mobile ocm,oe_user ou ");
			sql.append(" where oc.user_lecturer_id = ou.id and oc.user_lecturer_id = ? and "
					+ " oc.id=ocm.course_id and oc.is_delete=0 and oc.status=1 and oc.multimedia_type =2 ");//
		}
		common.append(sql);
		System.out.println("sql : "+common.toString());
		Object [] params = {lecturerId};
		List<CourseLecturVo> list = super.queryPage(JdbcUtil.getCurrentConnection(), common.toString(),pageNumber,pageSize,CourseLecturVo.class,params);
		
		for (CourseLecturVo courseLecturVo : list) {
			if(courseLecturVo.getCourseLength()>0){
				courseLecturVo.setCourseTimeConver(TimeUtil.formatTime(courseLecturVo.getCourseLength()));
			}else{
				courseLecturVo.setCourseTimeConver("00:00:00");
			}
		}
		return list;
	}

	@Override
	public Integer liveAndBunchAndAudioCount(String lecturerId) throws SQLException {
		
		StringBuilder sql = new StringBuilder("");  
		/**
		 * 直播课程
		 */
		sql.append("select sum(count) as allCount from (");
		sql.append("select count(*) as count from oe_course c,oe_user ou ");
		sql.append(" where  c.user_lecturer_id = ou.id  and c.user_lecturer_id = ? and c.is_delete=0 and c.status = 1 and c.type=1  ");
		sql.append(" union all ");
		/**
		 *  点播课程
		 */
		sql.append("select count(*) as count from oe_course oc, oe_course_mobile ocm,oe_user ou ");
		sql.append(" where oc.user_lecturer_id = ou.id and oc.user_lecturer_id = ? and oc.id=ocm.course_id and oc.is_delete=0 and oc.status=1 and oc.type is null");//
		sql.append(") as all_course");
		
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new MapHandler(),lecturerId,lecturerId);
		Integer all_count = 0;
		if(map!=null && map.size()>0){
			Object allCount = map.get("allCount");
			all_count = Integer.parseInt(allCount.toString());
		}
		return all_count;
	}

	@Override
	public ResponseObject addSubscribeInfo(OnlineUser user, String mobile, Integer courseId) throws SQLException {

		StringBuilder sql = new StringBuilder();
		sql.append(" insert into oe_course_subscribe(course_id,user_id,phone,create_time )");     
		sql.append(" values (?,?,?,?) "); 
		super.update(JdbcUtil.getCurrentConnection(),sql.toString(),courseId,user.getId(),mobile,new Date());

		return ResponseObject.newSuccessResponseObject("预约成功");
	}
	
	@Override
	public Integer selectSubscribeInfoNumberCourse(Integer courseId) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(*) as allCount from oe_course_subscribe where course_id =?");     
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new MapHandler(),courseId);
		Integer all_count = 0;
		if(map!=null && map.size()>0){
			Object allCount = map.get("allCount");
			all_count = Integer.parseInt(allCount.toString());
		}
		return all_count;
	}

	@Override
	public Integer selectSubscribeInfoIs(Integer courseId, String userId)
			throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append(" select count(*) as allCount from oe_course_subscribe where course_id =? and user_id = ?");    
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(),
				new MapHandler(),courseId,userId);
		
		Integer all_count = 0;
		if(map!=null && map.size()>0){
			Object allCount = map.get("allCount");
			all_count = Integer.parseInt(allCount.toString());
		}
		return all_count;
	}

	@Override
	public Integer getIsCouseType(int courseId) throws SQLException {
		return 	courseMapper.getIsCouseType(courseId);
	}
	
	@Override
	public Map<String,Object> shareLink(int courseId,int type) throws SQLException {
		Map<String,Object> map  = new HashMap<String,Object>();
		if(type ==1 ){
			StringBuffer sql = new StringBuffer("");
			sql.append("select c.grade_name as gradeName,");
			sql.append("c.smallimg_path as smallImgPath,");
			sql.append("c.description as description,live_status as lineState");
			sql.append(" from oe_course c");
			sql.append(" where c.id = ? and c.is_delete=0 and c.status = 1  and  c.type=1  ");
			map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(),
					new MapHandler(),courseId);
		}else{
			StringBuffer sql = new StringBuffer("");
			sql.append("select c.grade_name as gradeName,");
			sql.append("ocm.img_url as smallImgPath,c.description as description");
			sql.append(" from oe_course c,oe_course_mobile as ocm ");
			sql.append(" where c.id = ocm.course_id and c.id = ? and c.is_delete=0 and c.status = 1 ");
			map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(),
					new MapHandler(),courseId);
			
			/*StringBuffer sql = new StringBuffer("");
			sql.append("select c.grade_name as gradeName,");
			sql.append("ocm.img_url as smallImgPath,c.description as description");
			sql.append(" from oe_course c ");
			sql.append(" where  c.id = ? and c.is_delete=0 and c.status = 1 ");
			map = super.query(JdbcUtil.getCurrentConnection(), sql.toString(),
					new MapHandler(),courseId);*/
			
		}
		
		return 	map;
	}

	@Override
	public OnlineUser saveWxInfoByCode(String access_token, String openid,
                                       UserCenterAPI userCenterAPI) throws Exception {
		return null;
	}
	
	 /**
     * 修改直播课程的浏览数
     * @param courseId 鲜花数
	 * @throws SQLException 
     */
	@Override
    public void updateBrowseSum(Integer courseId) throws SQLException {
        String sql = " update oe_course  set pv = (pv + 1)  where id = ? and is_delete = 0 and status=1 ";
        System.out.println(sql);
        super.update(JdbcUtil.getCurrentConnection(),sql, courseId);
    }
	
	/**
	 * Description：只判断状态,其他的不判断
	 * @param course_id
	 * @return
	 * @throws SQLException
	 * @return CourseLecturVo
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@Override //TODO
	public CourseLecturVo courseStatusList(int course_id, String userId) throws SQLException {
		StringBuffer sql = new StringBuffer("");
		sql.append("select c.start_time as startTime,c.end_time as endTime,IF(c.type is not null,1,if(c.multimedia_type=1,2,3)) as type,");
		sql.append("if(c.course_pwd is not null,2,if(c.is_free =0,1,0)) as watchState,c.user_lecturer_id as userId ");  //课程简介
		sql.append(" from oe_course c where  c.is_delete=0 and c.status = 1 ");
		Object[] params = {course_id};
		System.out.println(sql.toString());
		CourseLecturVo courseLecturVo = this.query(JdbcUtil.getCurrentConnection(), sql.toString(),
				new BeanHandler<>(CourseLecturVo.class),params);
		
		//直播状态1.直播中，2预告，3直播结束
		 if(courseLecturVo.getLineState() == 2){
			System.out.println("预告");
			/**
			 * 获取当前预约人数
			 */
			Integer subCount = selectSubscribeInfoNumberCourse(course_id);
			courseLecturVo.setCountSubscribe(subCount);
			/**
			 * 判断自己是否预约了
			 */
			Integer isSubscribe = selectSubscribeInfoIs(course_id,userId);
			if(isSubscribe == 0){//未预约
				courseLecturVo.setIsSubscribe(isSubscribe);
			}else{
				courseLecturVo.setIsSubscribe(1);
			}
		}
		return this.query(JdbcUtil.getCurrentConnection(), sql.toString(), new BeanHandler<>(CourseLecturVo.class),params);
	}

	@Override
	public List<CourseLecturVo> findLiveListByQueryKey(int start_page, int pageNumber,
                                                       String queryParam) throws SQLException {
		/*
		 *  先从礼物表里面查出来被送例如最多的课程 
		 */
		StringBuilder comSql = new StringBuilder(); 
		if(queryParam!=null && !"".equals(queryParam) && !"null".equals(queryParam)){
			comSql.append(" and ("); 
			/*comSql.append(" ou.room_number like '%"+ queryParam + "%'"); 
			comSql.append(" or "); */
			comSql.append(" ou.name like '%"+ queryParam + "%'"); 
			comSql.append(" or "); 
			comSql.append(" c.grade_name like '%"+ queryParam + "%')"); 
		}
		
		 StringBuilder sql = new StringBuilder();
		 sql.append(" select SUM(ogs.count) as a,ogs.live_id from oe_gift_statement as ogs ,oe_user as ou,oe_course as c ");
		 sql.append(" where ogs.receiver =ou.id and ogs.live_id = c.id ");
		//房间编号/主播/课程
		
		sql.append(comSql);
		sql.append(" group by live_id order by a desc limit  "+start_page+","+pageNumber); 
		
		System.out.println("sql:"+sql.toString());
		List<Map<String, Object>> mapList =super.query(JdbcUtil.getCurrentConnection(), sql.toString(), new MapListHandler());
		
		String ids = "";
		for (Map<String, Object> map : mapList) {
			ids +="'"+map.get("live_id") +"'"+",";
		}
		List<CourseLecturVo> list = new ArrayList<CourseLecturVo>();
		
		StringBuffer sql1 = new StringBuffer("");
		sql1.append("select c.id,c.direct_Id as directId,c.grade_name as gradeName,ou.small_head_photo as headImg,ou.name as name,ou.id as userId,");
		sql1.append("c.smallimg_path as smallImgPath,c.start_time as startTime,c.end_time as endTime, ");
		sql1.append("c.original_cost as originalCost,c.current_price as currentPrice,");
		sql1.append(" if(c.course_pwd is not null,2,if(c.is_free =0,1,0)) as watchState, ");  // 观看状态  
		sql1.append(" IF(c.type is not null,1,if(c.multimedia_type=1,2,3)) as type, "); //类型 
		sql1.append(" c.live_status as  lineState ");
		sql1.append(" from oe_course c,oe_user ou ");
		sql1.append(" where  c.user_lecturer_id = ou.id  and  c.is_delete=0 and c.status = 1 and ou.status =0 and c.type=1  ");
		if(ids!=""){
			ids =  ids.substring(0, ids.length()-1);
			sql1.append("and  c.id  in ("+ids+") ");
		}
		sql1.append(comSql);
		
		System.out.println("sql1:"+sql1.toString());
		
		StringBuffer sql2 = new StringBuffer("");
		sql2.append("select c.id,c.direct_Id as directId,c.grade_name as gradeName,ou.small_head_photo as headImg,ou.name as name,ou.id as userId,");
		sql2.append("c.smallimg_path as smallImgPath,c.start_time as startTime,c.end_time as endTime, ");
		sql2.append("c.original_cost as originalCost,c.current_price as currentPrice,");
		sql2.append(" if(c.course_pwd is not null,2,if(c.is_free =0,1,0)) as watchState, ");  // 观看状态  
		sql2.append(" IF(c.type is not null,1,if(c.multimedia_type=1,2,3)) as type, "); //类型 
		sql2.append(" c.live_status as  lineState  ");
		sql2.append(" from oe_course c,oe_user ou ");
		sql2.append(" where  c.user_lecturer_id = ou.id  and  c.is_delete=0 and c.status = 1 and ou.status =0 and c.type=1  ");
		sql2.append(comSql);
		sql2.append(" limit " +(pageNumber -mapList.size()));
		
		if(mapList.size()>0 && mapList.size()<3){
			sql1.append(" union ");
			String allSql = sql1.toString()+sql2.toString();
			System.out.println(allSql);
			list =  super.query(JdbcUtil.getCurrentConnection(), allSql,new BeanListHandler<>(CourseLecturVo.class));
		}else if(mapList.size() == 3){
			list =  super.query(JdbcUtil.getCurrentConnection(), sql1.toString(),new BeanListHandler<>(CourseLecturVo.class));
		}else{
			list = super.query(JdbcUtil.getCurrentConnection(), sql2.toString(),new BeanListHandler<>(CourseLecturVo.class));
		}
		return list;
	}

	@Override
	public CourseLecturVo h5ShareAfter(int courseId, Integer type) throws SQLException {
		// TODO Auto-generated method stub
		
		CourseLecturVo clv  = new CourseLecturVo();
		if(type ==1 ){
			StringBuffer sql = new StringBuffer("");
			sql.append("select c.grade_name as gradeName,c.direct_Id as directId,");
			sql.append("c.smallimg_path as smallImgPath,c.start_time as startTime,c.end_time as endTime,");
			sql.append("c.description as description,ou.small_head_photo as headImg,ou.name as name,ou.id as userId,");
			
			sql.append(" IF(c.type is not null,1,if(c.multimedia_type=1,2,3)) as type, "); //类型 
			
			sql.append(" (SELECT IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) "); //观看人数
			sql.append(" + IFNULL(c.default_student_count, 0) + IFNULL(c.pv, 0)) as  learndCount,c.live_status as  lineState  ");
			
			sql.append(" from oe_course c,oe_user ou ");
			sql.append(" where c.user_lecturer_id = ou.id and c.id = ? and c.is_delete=0 and c.status = 1  and  c.type=1  ");
			
			clv = super.query(JdbcUtil.getCurrentConnection(), sql.toString(),
					new BeanHandler<>(CourseLecturVo.class),courseId);
//			1	int	否	直播进行中, 参加者可以进入观看直播
//			2	int	否	预约中 , 活动预约中,尚未开始
//			3	int	否	活动已结束
//			4	int	否	活动当前为点播
//			5	int	否	结束且有自动回放
			 if (clv.getLineState() == 2) { // 开始时间大于当前时间，说明已经还没开始
				/**
				 * 获取当前预约人数
				 */
				Integer subCount = selectSubscribeInfoNumberCourse(courseId);
				clv.setCountSubscribe(subCount);
			}
		}else{
			StringBuffer sql = new StringBuffer("");
			sql.append("select c.grade_name as gradeName,ou.small_head_photo as headImg,ou.name as name,ou.id as userId,");
			sql.append("ocm.img_url as smallImgPath,ocm.description as description,");
			
			sql.append(" IF(c.type is not null,1,if(c.multimedia_type=1,2,3)) as type, "); //类型 
			
			sql.append(" (SELECT IFNULL((SELECT  COUNT(*) FROM apply_r_grade_course WHERE course_id = c.id),0) "); //观看人数
			sql.append(" + IFNULL(c.default_student_count, 0) + IFNULL(c.pv, 0)) as  learndCount,c.description as courseDescription ");
			sql.append(" from oe_course c,oe_course_mobile as ocm,oe_user ou ");
			sql.append(" where c.user_lecturer_id = ou.id and c.id = ? and c.id = ocm.course_id  and c.is_delete=0 and c.status = 1 ");
			System.out.println(sql.toString());
			clv = super.query(JdbcUtil.getCurrentConnection(), sql.toString(),
					new BeanHandler<>(CourseLecturVo.class),courseId);
		}
		return clv;
	}

	@Override
	public void addLiveExamineData() throws SQLException {
		// TODO Auto-generated method stub
		onlineUserMapper.addLiveExamineData();
	}
	@Override
	public Map<String, Object> shareJump(int courseId) throws SQLException {
		
		Map<String, Object> map = courseMapper.shareJump(courseId);
		if(map==null){
			return null;
		}
		return map;
	}
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		
		List<String> newlist = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			String string = list.get(i);
			if(string.equals("1") || string.equals("3") ||string.equals("5")){
				newlist.add(string);
				list.remove(i);
			}
		}
		newlist.addAll(list);
		for (String string : newlist) {
			System.out.println(string);
		}
	}

	@Override
	public Map<String, String> teacherIsLive(String lecturerId) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("select c.id as id,live_status as  lineState,ou.is_lecturer as isLecturer");
		sql.append(" from oe_course c,oe_user ou ");
		sql.append(" where  c.user_lecturer_id = ? and ou.id = ? and c.is_delete=0 and c.status = 1 and c.type=1  ");
	    Object params[] = { lecturerId,lecturerId };
	    Map<String,String> mapIsLive = new HashMap<String,String>();
	    List<Map<String,Object>> mapList = super.query(JdbcUtil.getCurrentConnection(), sql.toString(),new MapListHandler(), params);
	    for (Map<String, Object> map : mapList) {
	    	if(null !=map && Integer.parseInt(map.get("lineState").toString())==1){
	    		mapIsLive.put("status", "1");
	    		mapIsLive.put("id", map.get("id").toString());
	    	}
		}
		return mapIsLive;
	}

	@Override
	public String sumMoneyLive(String id) {

		try {
		return	courseMapper.query(JdbcUtil.getCurrentConnection(),"select SUM(ors.price) from oe_reward_statement ors where live_id=? ",new ResultSetHandler<String>(){

			@Override
			public String handle(ResultSet resultSet) throws SQLException {
				resultSet.next();
				return resultSet.getString(1);
			}
		},id);
		} catch (SQLException e) {
			e.printStackTrace();
			return "0";
		}
	}

	/**
	 * Description：得到此课程的讲师id 
	 * @param courseId
	 * @return
	 * @throws SQLException
	 * @return Map<String,Object>
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@Override
	public String getlecturerIdByCourseId(Integer courseId) throws SQLException{
		String sql = "select lecturer_id as userId from oe_course as c,oe_user as ou "
				+ " where c.user_lecturer_id = ou.id and c.id = ?";
		Map<String, Object> map = super.query(JdbcUtil.getCurrentConnection(), sql,
				new MapHandler(),courseId);
		String userId = null;
		if(map!=null && map.get("userId")!=null){
			 userId = map.get("userId").toString();
		}
		return userId;
	}	

}
