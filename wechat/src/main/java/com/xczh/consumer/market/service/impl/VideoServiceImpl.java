package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.dao.BasicSimpleDao;
import com.xczh.consumer.market.service.VideoService;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.*;

@Service
public class VideoServiceImpl implements VideoService {
	
	@Autowired
	private BasicSimpleDao basicSimpleDao;
	
	@Override
	public Map<String, Object> getVideoInfo(String video_id) throws SQLException {
		Map<String, Object> returnmap = new HashMap<String, Object>();

		return returnmap;
	}
	
	@Override
	public Map<String,String> videoInfo(String video_id, HttpServletRequest req) throws SQLException {
		OnlineUser ou = (OnlineUser)req.getSession().getAttribute("_user_");
		String email = ou.getLoginName()+"@xczh.com";
		String name = ou.getName();
		String k = "yrxk";//TODO k值验证  暂时写死
		
		Date d = new Date();
		String start_time = d.getTime() + "";
		start_time = start_time.substring(0, start_time.length() - 3);
		Map<String,String> kv = new TreeMap<String,String>();
		kv.put("app_key", "71a22e5b4a41483d41d96474511f58f3");
		kv.put("signedat", start_time);
		kv.put("email", email);
		kv.put("id", video_id);
		kv.put("account", email);
		kv.put("username", name);
		kv.put("sign", getSign(video_id, email, email, name, start_time));
		
		StringBuilder sb = new StringBuilder();
        Set<String> keySet = kv.keySet();
        Iterator<String> iter = keySet.iterator();
        while (iter.hasNext()) {
            String key = iter.next();
//            System.out.println(key + ":" + kv.get(key));
            sb.append(key +"="+ kv.get(key)+"&");
        }
//        System.out.println(sb.toString());
//		return sb.toString();
        return kv;
	}
	

	public String getSign(String roomid,String account,String email,String username,String start_time){
		Map<String,String> signkv = new TreeMap<String,String>();
		signkv.put("roomid", roomid);
		signkv.put("account", account);
		signkv.put("email", email);
		signkv.put("username", username);
		signkv.put("app_key", "71a22e5b4a41483d41d96474511f58f3");
		signkv.put("signedat", start_time);
		Set<String> keySet = signkv.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder sb = new StringBuilder();
        String AppSecretKey = "1898130bad871d1bf481823ba1f3ffb1";
        sb.append(AppSecretKey);
        while (iter.hasNext()) {
            String key = iter.next();
            System.out.println(key + ":" + signkv.get(key));
            sb.append(key + signkv.get(key));
        }
        sb.append(AppSecretKey);
        System.out.println(sb.toString());
        System.out.println(getMD5(sb.toString()));
        return getMD5(sb.toString());
	}
	

	/** 
     * 生成md5 
     *  
     * @param message 
     * @return 
     */  
	public static String getMD5(String message) {
		String md5str = "";
		try {
			// 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 2 将消息变成byte数组
			byte[] input = message.getBytes();

			// 3 计算后获得字节数组,这就是那128位了
			byte[] buff = md.digest(input);

			// 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
			md5str = bytesToHex(buff);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5str.toLowerCase();
	}  
  
    /** 
     * 二进制转十六进制 
     *  
     * @param bytes 
     * @return 
     */  
    public static String bytesToHex(byte[] bytes) {  
	    StringBuffer md5str = new StringBuffer();  
	    // 把数组每一字节换成16进制连成md5字符串  
	    int digital;  
	    for (int i = 0; i < bytes.length; i++) {  
	        digital = bytes[i];  
	  
	        if (digital < 0) {  
	        digital += 256;  
	        }  
	        if (digital < 16) {  
	        md5str.append("0");  
	        }  
	        md5str.append(Integer.toHexString(digital));  
	    }  
	    return md5str.toString().toUpperCase();  
    }  
    
    /**
     * 视频播放页、试学页面、答题闯关、视频列表 姜海成
     * @param courseId
     * @param userId
     * @param isTryLearn
     * @return
     * @throws SQLException 
     */
    @Override
    public List<Map<String, Object>> getvideos(Integer courseId, String userId, Boolean isTryLearn) throws SQLException {
    	
    	List<Map<String, Object>> returnmap = new ArrayList<Map<String, Object>>();
    	
    	//查询所有章节知识点
    	String sql = "select id as chapter_id,name,parent_id,level,barrier_id from oe_chapter "
    			+ "where is_delete=0 and course_id=? and level>1 order by sort";
    	//List<Map<String, Object>> chapters = videoDao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql, courseId);
    	
    	List<Map<String, Object>> chapters = basicSimpleDao.query(JdbcUtil.getCurrentConnection(), sql,new MapListHandler(),courseId);
    	
    	//查询本课程所有视频
    	List<Map<String, Object>> videos = null;
    	List<Map<String, Object>> barriers = null;//关卡
    	if (isTryLearn) {//试学
    		sql = "select t2.id as vid,t2.chapter_id as chapterId,t2.name as videoName,t2.video_id as videoId,t2.video_time as videoTime,"+
    				"t2.video_size as videoSize,t2.course_id as courseId,t2.is_try_learn isLearn "+
    				" from oe_video t2 "+
    				" where t2.course_id = ? and t2.is_delete = 0  and t2.status=1 order by t2.sort";
    		
    		videos = basicSimpleDao.query(JdbcUtil.getCurrentConnection(), sql,new MapListHandler(),courseId);
    		
		} else {//播放页
			sql = "select t2.id as vid ,t2.chapter_id as chapterId,t2.name as videoName,t2.video_id as videoId,t2.video_time as videoTime,"+
					"t2.video_size as videoSize,t2.course_id as courseId,t2.is_try_learn isLearn,t1.study_status "+
					" from user_r_video t1, oe_video t2"+
					" where t1.video_id = t2.id and t1.user_id = ? and t1.course_id = ? and t1.is_delete = 0  and t1.status=1 order by t2.sort";
			//videos = videoDao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,userId,courseId);
			videos = basicSimpleDao.query(JdbcUtil.getCurrentConnection(), sql,new MapListHandler(),userId,courseId);
			
			//查询本课程此用户的所有关卡
	    	sql = "select t1.*,t2.`name`,t2.kpoint_id from oe_barrier_user t1,oe_barrier t2 where t1.barrier_id=t2.id and t1.user_id=? "
	    			+ " and t1.course_id=? and t2.is_delete=0 and t2.status = 1 ";
	    	//barriers = videoDao.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql,userId, courseId);
	    	
	    	barriers = basicSimpleDao.query(JdbcUtil.getCurrentConnection(), sql,new MapListHandler(),userId,courseId);
		}
    	
    	//组装树形结构
    	for (Map<String, Object> zhangmap : chapters) {
    		//循环章
    		if (((Integer)zhangmap.get("level")) == 2) {
    			boolean zhangIsTry = false;
    			List<Map<String, Object>> zhangsons = new ArrayList<Map<String, Object>>();
    			//循环取节>>>>>
    			for (Map<String, Object> jiemap : chapters) {
    				if (jiemap.get("parent_id").equals(zhangmap.get("chapter_id"))) {
    					boolean jieIsTry = false;
    					List<Map<String, Object>> jiesons = new ArrayList<Map<String, Object>>();
    					//循环取知识点>>>>>
    	    			for (Map<String, Object> zhishidianmap : chapters) {
    	    				if (zhishidianmap.get("parent_id").equals(jiemap.get("chapter_id"))) {
    	    					boolean zhishidianIsTry = false;
    	    					
    	    					zhishidianmap.put("hasBarrier", "0");
    	    					Boolean videoHasLoker = false;//该关卡视频上有没有关卡
    	    					String barrierId = null;//barrierId = null表示这个知识点下的视频能看，否则不能看
    	    					
    	    					if (barriers != null && barriers.size() > 0) {
    	    						//取这个知识点上的关卡>>>>>
    	    						for (Map<String, Object> barriermap : barriers) {
    	    							Object bo = barriermap.get("kpoint_id");
    	    							if (bo != null && bo.equals(zhishidianmap.get("chapter_id").toString())) {
    	    								zhishidianmap.put("hasBarrier", "1");
	    									zhishidianmap.put("barrierName", barriermap.get("name"));
	    									zhishidianmap.put("barrierStatus", barriermap.get("barrier_status"));
	    									zhishidianmap.put("lockStatus", barriermap.get("lock_status"));
	    									break;
										}
    	    						}
    	    						//<<<<<取知这个识点上的关卡
    	    						
    	    						//判断这个知识点下的视频能不能看 >>>>>
    	    						Object pbo = zhishidianmap.get("barrier_id");
    	    						if (pbo != null && !"".equals(pbo.toString())) {
    	    							videoHasLoker = true;//有关卡
    	    							for (Map<String, Object> barriermap : barriers) {
    	    								if (barriermap.get("barrier_id").equals(pbo.toString())) {
    	    									if ("0".equals(barriermap.get("lock_status").toString())) {
    	    										barrierId = barriermap.get("chapter_id").toString();
    												break;
												}
    	    								}
    	    							}
									}
    	    						//<<<<<判断这个知识点下的视频能不能看
    	    					}
    	    					
    	    					//循环取视频>>>>>
    	    					List<Map<String, Object>> zhishidiansons = new ArrayList<Map<String, Object>>();
    	    	    			for (Map<String, Object> videomap : videos) {
    	    	    				if (videomap.get("chapterId").equals(zhishidianmap.get("chapter_id"))) {
    	    	    					if ((Boolean)videomap.get("isLearn") && !zhishidianIsTry && !jieIsTry && !zhangIsTry) {
    	    	    						zhishidianIsTry = true;
    	    	    						jieIsTry = true;
    	    	    						zhangIsTry = true;
										}
    	    	    					//该关卡视频上有没有关卡、是否可以看
    	    	    					if (videoHasLoker) {//有关卡
    	    	    						videomap.put("lockStatus", "1");//开锁
    	    	    						if(barrierId != null){
    	    	    							videomap.put("lockStatus", "0");//关锁
    	    	    							videomap.put("barrierId", barrierId);
    	    	    						}
										}
    	    	    					zhishidiansons.add(videomap);
    	    						}
    	    	    			}
    	    	    			//<<<<<循环取视频
    	    	    			zhishidianmap.put("videos", zhishidiansons);
    	    	    			zhishidianmap.put("isLearn", zhishidianIsTry);
    	    	    			jiesons.add(zhishidianmap);
    						}
    	    			}
    	    			jiemap.put("isLearn", jieIsTry);
    	    			jiemap.put("chapterSons", jiesons);
    	    			//<<<<<循环取知识点
    	    			zhangsons.add(jiemap);
					}
    			}
    			zhangmap.put("isLearn", zhangIsTry);
    			zhangmap.put("chapterSons", zhangsons);
    			//<<<<<循环取节
    			returnmap.add(zhangmap);
    		}
    	}
    	return returnmap;
    }
    
}
