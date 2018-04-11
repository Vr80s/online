package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.support.cc.util.CCUtils;
import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.service.impl.RedisCacheService;
import com.xczhihui.bxg.common.util.CodeUtil;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.bxg.online.web.service.VedioService;
import com.xczhihui.bxg.online.web.vo.VedioAuthVo;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 视频播放验证
 * 
 * @author Haicheng Jiang
 *
 */
@Service
public class VedioServiceImpl extends OnlineBaseServiceImpl implements VedioService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SimpleHibernateDao dao;
	
	@Autowired
	private RedisCacheService redis;
	@Autowired
	private CCUtils ccUtils;

	@Override
	public String getCcVerificationCode(String userId, String vid,String id) {
		String key = CodeUtil.getRandomUUID();
		id = id == null ? "000" : id;
		redis.set(key, key + "!" + userId + "!" + id, 3600);
		return key;
	}

	@Override
	public VedioAuthVo checkAuth(String vid, String verificationcode) {
		
		VedioAuthVo vo = new VedioAuthVo();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String videosql = "select is_try_learn from oe_video where video_id=:video_id";
		paramMap.put("video_id", vid);
		List<Map<String, Object>> vs = dao.getNamedParameterJdbcTemplate().queryForList(videosql, paramMap);
		if (vs == null || vs.size() <= 0) {
			vo.setMessage("视频上传中，请耐心等待……");
			return  vo;
		}

		// 试学
		for (Map<String, Object> is_try_map : vs) {
			if (Boolean.valueOf(is_try_map.get("is_try_learn").toString())) {
				vo.setEnable(1);
				return vo;
			}
		}

		// 验证码验证
		String key = redis.get(verificationcode);
		if (key == null) {
			vo.setMessage("视频验证不成功，请刷新后重试……");
			return vo;
		}
		
		// 用户验证
		String sql = "select count(t2.id) from user_r_video t1,oe_video t2 "
				+ " where t1.video_id=t2.id and t1.user_id=:user_id and t2.video_id=:video_id";
		paramMap.put("user_id", key.split("!")[1]);
		
		Integer sum = dao.getNamedParameterJdbcTemplate().queryForObject(sql, paramMap, Integer.class);
		if (sum > 0) {
			vo.setEnable(1);
		} else {
			vo.setMessage("请购买此课程");
		}
		
		//跑马灯
		if (vo.getEnable() == 1) {
			vo.setMarquee(this.getMarquee(key.split("!")[1]));
		}

		return vo;
	}
	
	private Map<String, Object> getMarquee(String userId){
		
		Map<String, Object> top = new HashMap<String, Object>();
		top.put("loop", -1);//循环次数，-1为无数次
		top.put("type", "text");//类型，text/image
		
		//跑马灯内容
		Map<String, Object> text = new HashMap<String, Object>();
		String usql = "select login_name from oe_user where id=?";
		String login_name = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForObject(usql, String.class, userId);
		text.put("content", "熊猫中医 "+login_name+" 正在观看视频");//文字内容
		text.put("font_size", 13);//文字大小
		text.put("color", "gray");//文字颜色
		top.put("text", text);
		
		//动作
		List<Map<String, Object>> action = new ArrayList<Map<String, Object>>();
		
		//y轴随机位置
		double[] ia = {0.2,0.5,0.8};
		java.util.Random random=new java.util.Random();
		for(int i=0; i<3; i++){
			
			double ypos = ia[random.nextInt(3)];
			
			Map<String, Object> actionmp = new HashMap<String, Object>();
			actionmp.put("index", i);
			actionmp.put("duration", 15000);
			
			Map<String, Object> start = new HashMap<String, Object>();
			start.put("xpos", 1);
			start.put("ypos", ypos);
			start.put("alpha", 0.5);
			actionmp.put("start",start);
			
			Map<String, Object> end = new HashMap<String, Object>();
			end.put("xpos", 0);
			end.put("ypos", ypos);
			end.put("alpha", 0.5);
			actionmp.put("end",end);
			
			action.add(actionmp);
		}
		
		top.put("action", action);
		
		return top;
	}
	
	@Override
	public Map<String,Object> getVideoInfo(Map<String, String> paramsMap, HttpServletRequest req) {
		
		BxgUser loginUser = UserLoginUtil.getLoginUser(req);
//		String email = loginUser.getEmail();
		String email = loginUser.getLoginName()+"@xczh.com";
		String name = loginUser.getName();
		String k = "yrxk";//TODO k值验证  暂时写死
		String roomId = paramsMap.get("videoid");
		
		Map<String,Object> returnmap = new HashMap<String,Object>();
		
		String url = "http://e.vhall.com/webinar/inituser/"+roomId+"?email="+email+"&name="+name+"&k="+k + "&embed=video";
		String playCode = "<iframe id=\"vhall-video\" border=\"0\" src=\""+url+"\" width=\"100%\" height=\"100%\" style=\"border-width:0;\"></iframe>";
		
		returnmap.put("playCode", playCode);
		
		String title = "暂无";
		List<Map<String, Object>> vs = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
				.queryForList("select `name` from oe_video where video_id='"+paramsMap.get("videoid")+"' ");
		if (vs != null && vs.size() > 0) {
			title = String.valueOf(vs.get(0).get("name"));
		}
		
		returnmap.put("title", title);
		
		return returnmap;
	}

	@Override
	public Map<String, Object> getCCVideoInfo(Map<String, String> paramsMap) {
		DetachedCriteria dc = DetachedCriteria.forClass(Course.class);
		dc.add(Restrictions.eq("id", Integer.valueOf(paramsMap.get("courseId"))));
		Course course = dao.findEntity(dc);

		Map<String,Object> returnmap = new HashMap<String,Object>();
		String audioStr="";
		if(course.getMultimediaType()==2){
			audioStr = "_2";
		}

		String playCode = ccUtils.getPlayCode(course.getDirectId(),audioStr);

		returnmap.put("playCode", playCode);
		returnmap.put("title", course.getGradeName());

		return returnmap;
	}

	@Override
	@Resource(name="simpleHibernateDao")
	public void setDao(SimpleHibernateDao dao) {
		this.dao = dao;
	}
}
