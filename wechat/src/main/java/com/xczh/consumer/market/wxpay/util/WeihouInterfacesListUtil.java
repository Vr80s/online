package com.xczh.consumer.market.wxpay.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.utils.HttpUtil;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 得到微吼官方接口数据工具类
 * ClassName: WeihouInterListUtil.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2017年8月10日<br>
 */
public class WeihouInterfacesListUtil {

	//当前在线人数
	public static final String currentonlinenumber = "http://e.vhall.com/api/vhallapi/v2/webinar/current-online-number";
	
	//获取活动状态
	public static final String currentonlinestate ="http://e.vhall.com/api/vhallapi/v2/webinar/state"; 
	
	//得到微吼用户id
	public static final String currentUserID ="http://e.vhall.com/api/vhallapi/v2/user/get-user-id"; 
	
	//注册用户
	public static final String register = "http://e.vhall.com/api/vhallapi/v2/user/register";
	
	//获取用户信息
	public static final String getUserInfo = "http://e.vhall.com/api/vhallapi/v2/user/get-user-info";
	
	public static final String app_key = "71a22e5b4a41483d41d96474511f58f3";
	
	public static final String AppSecretKey = "1898130bad871d1bf481823ba1f3ffb1";
	
	
	public static final String moren = "123456";//第三方登录默认微吼密码
	
	public static Map<String,String> getBaseParams(){
		Map<String,String> parameters = new HashMap<String,String>();
		//公共参数
		parameters.put("auth_type", "1");
		parameters.put("password", "xinchengzhihui");
		parameters.put("webinar_id", "985221");
		
		return parameters;
	}
	
	
	/**
	 * Description：请求根据用户id,更新用户接口信息！微吼更新用户接口，
	 * @param userId
	 * @param pass
	 * @param name
	 * @param head
	 * @return
	 * @return String
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public static Integer currentUserID(String videoId) {
		String whUrl = currentUserID;
		Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
		parameters.put("auth_type", "1");
		parameters.put("account", "v19624388");
		parameters.put("password", "xinchengzhihui");
		
		
		parameters.put("third_user_id", videoId);
		String json = HttpUtil.sendPostRequest(whUrl, parameters);
		JSONObject js = JSONObject.parseObject(json);
		//System.out.println(js.toJSONString());
		if(js.get("code").equals("200")){
			//JSONObject jsData =JSONObject.parseObject(js.get("data").toString());
			System.out.println(js.toJSONString());
			if(js.get("data")!=null){
				return Integer.parseInt(js.get("data").toString());
			}
		}
		return 0;
	}
	
	
	//测试
	public static void main(String[] args) {
		//currentUserID("24e7d53a956f4a4eb7b22d5742626e8f");
		//third_user_id
		//getUserinfo("20383761", "name,head,third_user_id");
		
		//21047835   v21054339
		/*createUser("137827828781", "123456", "123456", "http://attachment-center.ixincheng.com:38080/data/"
				+ "picture/online/2017/09/25/15/e4981eeaec9746f7b965ee475ed90a2c.jpg");*/
		
		//账号：15936216273, 接口/bxg/bs/login返回的微吼id是"22785686", 微吼登录返回的id是"20383761".
		
		getUserinfo("22785686", "name,head");
		
/*		updateUser("6798c0bdeeea47f8ae6c016a97ee36ac", null, "yangxuanhao","http://attachment-center.ixincheng.com:38080/data/"
				+ "picture/online/2017/09/25/15/e4981eeaec9746f7b965ee475ed90a2c.jpg");*/
		
	}
	
	
	
	/**
	 * Description：请求根据用户id,更新用户接口信息！微吼更新用户接口，
	 * @param userId
	 * @param pass
	 * @param name
	 * @param head
	 * @return
	 * @return String
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public static Integer getOnlineState(String videoId) {
/*		if(!StringUtils.hasText(name) &&!StringUtils.hasText(head)){
			return null;
		}*/
		String whUrl = currentonlinestate;
		Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
		parameters.put("auth_type", "1");
		parameters.put("account", "v19624388");
		parameters.put("password", "xinchengzhihui");
		
		parameters.put("webinar_id", videoId);
		String json = HttpUtil.sendPostRequest(whUrl, parameters);
		JSONObject js = JSONObject.parseObject(json);
		//System.out.println(js.toJSONString());
		if(js.get("code").equals("200")){
			//JSONObject jsData =JSONObject.parseObject(js.get("data").toString());
			System.out.println(js.toJSONString());
			if(js.get("data")!=null){
				return Integer.parseInt(js.get("data").toString());
			}
		}
		return 0;
	}
	
	/**
	 * 
	 * Description：获取直播间当前在线人数
	 * @param webinar_id
	 * @return
	 * @return JSONObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public static JSONObject getCurrentOnlineNumber(String webinar_id){
		Map<String,String> parameters =  getBaseParams();
		parameters.put("webinar_id", webinar_id);
		String str  = HttpUtil.sendPostRequest(currentonlinenumber, parameters);
		JSONObject js = JSONObject.parseObject(str);
		return js;
	}
	/**
	 * 请求当前观看人数时，需要判断是不是在30秒内，如果在30秒内，就不用请求了
	 * Description：
	 * @param recordDate
	 * @return
	 * @return boolean
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public static boolean currentonLinenumberConditions(Date recordDate){
		boolean falg = false;
		Calendar c = new GregorianCalendar();
		//System.out.println("系统当前时间      ："+df.format(date));
		c.setTime(recordDate);//设置参数时间
		c.add(Calendar.SECOND,+30);//把日期往后增加SECOND 秒.整数往后推,负数往前移动
		recordDate = c.getTime();
		System.out.println(recordDate.getTime());
		if(recordDate.getTime()<new Date().getTime()){
			falg = true;
		}
		return falg;
	}
	
	
	/**
	 * Description：请求根据用户id,更新用户接口信息！微吼更新用户接口，
	 * @param userId
	 * @param pass
	 * @param name
	 * @param head
	 * @return
	 * @return String
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public static String updateUser(String userId,String password,String name,String head) {
/*		if(!StringUtils.hasText(name) &&!StringUtils.hasText(head)){
			return null;
		}*/
		String whUrl = "http://e.vhall.com/api/vhallapi/v2/user/update";
		Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
		parameters.put("auth_type", "1");
		parameters.put("account", "v19624388");
		parameters.put("password", "xinchengzhihui");
		/* 公共参数 */
		parameters.put("third_user_id", userId);
		//parameters.put("pass", userId);
		if(StringUtils.hasText(password)){
			parameters.put("pass", password);
		}
		if(StringUtils.hasText(name)){
			parameters.put("name", name);
		}
		if(StringUtils.hasText(head)){
			parameters.put("head", head);
		}
		String json = HttpUtil.sendPostRequest(whUrl, parameters);
		Map<String, String> m =json2Map(json);
		if(m.get("msg").equals("success")){
			Map<String, String> map =json2Map(m.get("data"));
			String vhallId = map.get("user_id");
			System.out.println(json+":"+userId);
			return vhallId;
		}
		/*JSONObject js = JSONObject.parseObject(json);
		System.out.println(js.toJSONString());
		if(js.get("msg").equals("success")){
			JSONObject jsData =JSONObject.parseObject(js.get("data").toString());
			System.out.println(jsData.toJSONString());
			
			return jsData.toJSONString();
		}
		*/
		return null;
	}
	//
	
	/**
	 * Description：请求根据用户id,更新用户接口信息！微吼更新用户接口，
	 * @param userId  
	 * @param pass fields 需要获取的字段，字段之间用英文逗号,分割
	 * @return
	 * @return String
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public static JSONObject getUserinfo(String userId,String fields) {
		if(!StringUtils.hasText(userId) &&!StringUtils.hasText(fields)){
			return null;
		}
		String whUrl = "http://e.vhall.com/api/vhallapi/v2/user/get-user-info";
		Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
		parameters.put("auth_type", "1");
		parameters.put("account", "v19624388");
		parameters.put("password", "xinchengzhihui");
		/* 公共参数 */
		parameters.put("user_id", userId);
		parameters.put("fields", fields);
		String json = HttpUtil.sendPostRequest(whUrl, parameters);
		//Map<String, String> m =json2Map(json);
		JSONObject js = JSONObject.parseObject(json);
		System.out.println(js.toJSONString());
		if(js.get("msg").equals("成功")){
			JSONObject jsData =JSONObject.parseObject(js.get("data").toString());
			System.out.println(jsData.toJSONString());
			return jsData;
		}
		return null;
	}
	
	
	/**
	 * Description：请求微吼创建用户接口，得到一个微吼用户id。
	 * @param userId  第三方用户id
	 * @param pass	登录密码
	 * @param name  姓名
	 * @param head	头像
	 * @return
	 * @return String
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public static String createUser(String userId,String pass,
			String name,String head) {

		Map<String, String> parameters =  WeihouInterfacesListUtil.getBaseParams();
		
		parameters.put("third_user_id", userId);
		parameters.put("pass", pass);
		parameters.put("name", name);
		parameters.put("head", head);
		
		String json = HttpUtil.sendPostRequest(register, parameters);
		
		JSONObject js = JSONObject.parseObject(json);
		if(js.get("msg").equals("success")){
			JSONObject jsData =JSONObject.parseObject(js.get("data").toString());
			System.out.println(jsData.toJSONString());
			String vhallId = jsData.get("user_id").toString();
			return vhallId;
		}else if(Integer.parseInt(js.get("code").toString()) == 10804){
			
			return updateUser(userId, pass, name, head);
		}
		return null;
	}
	
	//20264468
	public static Map<String, String> json2Map(String jsonStr) {
		JSONObject jsonObj = JSON.parseObject(jsonStr);
		Set<String>  jsonKeySet = jsonObj.keySet();   
        Map<String, String> resultMap = new HashMap<>();  
        Iterator<String> it = jsonKeySet.iterator();    
        while (it.hasNext()) {    
          String key = it.next();    
          resultMap.put(key, jsonObj.getString(key));  
        }    
        return resultMap; 
    }
	
//	//测试
//	public static void main(String[] args) {
//		Calendar c = new GregorianCalendar();
//		Date d= new Date();
//		System.out.println(d.getTime());
//		c.setTime(d);//设置参数时间
//		c.add(Calendar.SECOND,-40);//把日期往后增加SECOND 秒.整数往后推,负数往前移动
//		d=c.getTime();
//		System.out.println(d.getTime());
//		System.out.println(currentonLinenumberConditions(d));
//	}
	

}
