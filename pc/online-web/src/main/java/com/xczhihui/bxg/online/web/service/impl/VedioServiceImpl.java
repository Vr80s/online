package com.xczhihui.bxg.online.web.service.impl;/**
												* Created by admin on 2016/8/31.
												*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.support.service.impl.RedisCacheService;
import com.xczhihui.bxg.common.util.CodeUtil;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.utils.OnlineConfig;
import com.xczhihui.bxg.online.web.service.VedioService;
import com.xczhihui.bxg.online.web.vo.VedioAuthVo;

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
		
		//答题闯关
//		String barrierSql = "select count(t1.id) from oe_video t1,oe_chapter t2,oe_barrier_user t3 "
//				+ " where t1.chapter_id=t2.id and t2.barrier_id=t3.barrier_id and t1.id = '"+key.split("!")[2]+"' and t3.lock_status=0";
//		Integer barrierVideoSum = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
//				.queryForObject(barrierSql,Integer.class);
//		if (barrierVideoSum > 0) {
//			vo.setEnable(0);
//			vo.setMessage("0");
//			return vo;
//		}

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
/*	
	@Override
	public Map<String,Object> getVideoInfo(Map<String, String> paramsMap) {
		
		Map<String,Object> returnmap = new HashMap<String,Object>();
		
		String src = "https://p.bokecc.com/flash/single/"+OnlineConfig.CC_USER_ID+"_"+paramsMap.get("videoid")+"_false_"+OnlineConfig.CC_PLAYER_ID+"_1/player.swf";
		String id = UUID.randomUUID().toString().replace("-", "");
		String playCode = "";
		playCode+="<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" ";
		playCode+="		codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\" ";
		playCode+="		width=\"100%\" ";
		playCode+="		height=\"100%\" ";
		playCode+="		id=\""+id+"\">";
		playCode+="		<param name=\"movie\" value=\""+src+"\" />";
		playCode+="		<param name=\"allowFullScreen\" value=\"true\" />";
		playCode+="		<param name=\"allowScriptAccess\" value=\"always\" />";
		playCode+="		<param value=\"transparent\" name=\"wmode\" />";
		playCode+="		<embed src=\""+src+"\" ";
		playCode+="			width=\"100%\" height=\"100%\" name=\""+id+"\" allowFullScreen=\"true\" ";
		playCode+="			wmode=\"transparent\" allowScriptAccess=\"always\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" ";
		playCode+="			type=\"application/x-shockwave-flash\"/> ";
		playCode+="	</object>";
		
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
*/
//	@Override
//	public String getUploadUrl(String title, String description, String tag, String categoryid) {
//		Map<String,String> paramsMap = new HashMap<String,String>();
//		paramsMap.put("userid", OnlineConfig.CC_USER_ID);
//		paramsMap.put("title", title);
//		paramsMap.put("description", description);
//		paramsMap.put("tag", tag);
//		paramsMap.put("categoryid", categoryid);
//		long time = System.currentTimeMillis();
//		return  Config.api_updateVideo + "?" + APIServiceFunction.createHashedQueryString(paramsMap, time, OnlineConfig.CC_API_KEY);
//	}

//	@Override
//	public void uploadSuccessCallback(String duration,String image,String status,String videoid,String time,String hash) {
//		if ("OK".equals(status)) {
//			
//			String url = String.format("duration=%s&image=%s&status=%s&videoid=%s&time=%s&salt=%s",
//					duration,image,status,videoid,time,OnlineConfig.CC_API_KEY);
//			
//			String md5 = Md5Encrypt.md5(url);
//			if (!md5.equals(hash)) {
//				logger.error(videoid+"|uploadSuccessCallback接口验证失败！");
//			}
//			if (System.currentTimeMillis() / 1000 - Long.valueOf(time) > 5) {
//				logger.error(videoid+"|uploadSuccessCallback接口恶意调用！");
//			}
//			
//			String ms = "00:00";
//			int duration_s = StringUtils.hasText(duration) ? Integer.valueOf(duration) : 0;
//			if (duration_s > 0) {
//				String m = String.valueOf((duration_s / 60));
//				String s = String.valueOf((duration_s % 60));
//				m = m.length()==1 ? "0"+m : m;
//				s = s.length()==1 ? "0"+s : s;
//				ms = m+":"+s;
//			}
//			dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(
//					"update oe_video set video_time=? where video_id=? ",ms,videoid);
//			
//			logger.info(videoid+"|片长更新成功|"+ms);
//		} else {
//			logger.error(videoid+"|视频处理失败！");
//		}
//	}

	@Resource(name="simpleHibernateDao")
	public void setDao(SimpleHibernateDao dao) {
		this.dao = dao;
	}
}
