package com.xczhihui.bxg.online.web.base.utils;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xczhihui.bxg.common.util.HttpUtil;
import com.xczhihui.bxg.online.base.bean.Webinar;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.utils.MD5Util;
import com.xczhihui.user.center.utils.CodeUtil;

/**
 * @author Administrator
 *
 */
public class VhallUtil {
	
	private static String ACCOUNT = "v19624388";
	private static String PWD = "xinchengzhihui";
	private static String AUTO_TYPE = "1";
	
	private static String USER_REGISTER = "https://e.vhall.com/api/vhallapi/v2/user/register";//用户注册
	private static String USER_UPDATE = "https://e.vhall.com/api/vhallapi/v2/user/update";//用户更新

	private static String WEBINAR_CREATE = "https://e.vhall.com/api/vhallapi/v2/webinar/create";//直播间创建
	private static String WEBINAR_START = "https://e.vhall.com/api/vhallapi/v2/webinar/start";//获取直播间地址

	
	/** 
	 * Description：创建用户
	 * @param u
	 * @param password
	 * @return
	 * @return String
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public static String createUser(OnlineUser u,String password) {
		Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
		parameters.put("auth_type", AUTO_TYPE);
		parameters.put("account", ACCOUNT);
		parameters.put("password", PWD);
		/* 公共参数 */
		parameters.put("third_user_id", u.getId());
		parameters.put("pass", password);
		parameters.put("name", u.getName());
		parameters.put("head", u.getSmallHeadPhoto());
		
		String json = HttpUtil.sendPostRequest(USER_REGISTER, parameters);
		Map<String, String> m =json2Map(json);
		if(m.get("msg").equals("success")){
			Map<String, String> map =json2Map(m.get("data"));
			String vhallId = map.get("user_id");
			System.out.println(json+":"+u.getLoginName()+":"+password);
			return vhallId;
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
	
	
	/** 
	 * Description：更新用户
	 * @param u
	 * @param password
	 * @return
	 * @return String
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public static String updateUser(OnlineUser u,String password) {
		Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
		parameters.put("auth_type", AUTO_TYPE);
		parameters.put("account", ACCOUNT);
		parameters.put("password", PWD);
		/* 公共参数 */
		parameters.put("third_user_id", u.getId());
		if(password!=null)
			parameters.put("pass", password);
		if(u.getSmallHeadPhoto()!=null)
			parameters.put("head", u.getSmallHeadPhoto());
		if(u.getName()!=null)
			parameters.put("name", u.getName());
		
		String json = HttpUtil.sendPostRequest(USER_UPDATE, parameters);
		Map<String, String> m =json2Map(json);
		if(m.get("msg").equals("success")){
			Map<String, String> map =json2Map(m.get("data"));
			String vhallId = map.get("user_id");
			System.out.println(json+":"+u.getLoginName()+":"+password);
			return vhallId;
		}
		return null;
	}
	
	/** 
	 * Description：创建一个直播
	 * @return
	 * @return String
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public static String createWebinar(Webinar webinar) {
		Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
		parameters.put("auth_type", AUTO_TYPE);
		parameters.put("account", ACCOUNT);
		parameters.put("password", PWD);
		/* 公共参数 */
		
		parameters.put("subject", webinar.getSubject());
		parameters.put("start_time", webinar.getStart_time());
		if(webinar.getUser_id()!=null)
			parameters.put("user_id", webinar.getUser_id());
		if(webinar.getLayout()!=null)
			parameters.put("layout", webinar.getLayout());
		if(webinar.getType()!=null)
			parameters.put("type", webinar.getType());
		if(webinar.getAuto_record()!=null)
			parameters.put("auto_record", webinar.getAuto_record());
		if(webinar.getHost()!=null)
			parameters.put("host", webinar.getHost());
		
		
		String json = HttpUtil.sendPostRequest(WEBINAR_CREATE, parameters);
		System.out.println(json);
		Map<String, String> m =json2Map(json);
		if(m.get("code").equals("200")){
			return m.get("data");
		}
		return null;
	}
	
	/** 
	 * Description：获取主播的直播地址
	 * @return
	 * @return String
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public static String getWebinarUrl(String webinarId) {
		Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
		parameters.put("auth_type", AUTO_TYPE);
		parameters.put("account", ACCOUNT);
		parameters.put("password", PWD);
		/* 公共参数 */
		
		parameters.put("webinar_id", webinarId);
		
		String json = HttpUtil.sendPostRequest(WEBINAR_START, parameters);
		System.out.println(json);
		Map<String, String> m =json2Map(json);
		if(m.get("code").equals("200")){
			System.out.println(m.get("data"));
			return m.get("data");
		}
		return null;
	}

	/** 
	 * Description：设置封面
	 * @param webinar_id
	 * @param image
	 * @return
	 * @return String
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 **/
	public static String setActiveImage(String webinar_id,String image ){
		String resStr = null;
		try{
			String boundary = "Boundary-b1ed-4060-99b9-fca7ff59c113"; //Could be any string
			String Enter = "\r\n";
			
			File file = new File(image);
			FileInputStream fis = new FileInputStream(file);
			
			URL url = new URL("http://e.vhall.com/api/vhallapi/v2/webinar/activeimage");
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type","multipart/form-data;boundary=" + boundary); 
			
			conn.connect();
			
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
		  
		  //part 1
			String part1 =  "--" + boundary + Enter
					+ "Content-Type: application/octet-stream" + Enter
					+ "Content-Disposition: form-data; filename=\""+file.getName()+"\"; name=\"image\"" + Enter + Enter;
		  //part 2
			String part2 = Enter
					+ "--" + boundary + Enter
					+ "Content-Type: text/plain" + Enter
					+ "Content-Disposition: form-data; name=\"webinar_id\"" + Enter + Enter
					+ webinar_id + Enter
					+ "--" + boundary + "--";
			//part 3
			String part3 = Enter
					+ "--" + boundary + Enter
					+ "Content-Type: text/plain" + Enter
					+ "Content-Disposition: form-data; name=\"auth_type\"" + Enter + Enter
					+ VhallUtil.AUTO_TYPE + Enter
					+ "--" + boundary + "--";
			//part 4
			String part4 = Enter
					+ "--" + boundary + Enter
					+ "Content-Type: text/plain" + Enter
					+ "Content-Disposition: form-data; name=\"account\"" + Enter + Enter
					+ VhallUtil.ACCOUNT + Enter
					+ "--" + boundary + "--";
			//part 5
			String part5 = Enter
					+ "--" + boundary + Enter
					+ "Content-Type: text/plain" + Enter
					+ "Content-Disposition: form-data; name=\"password\"" + Enter + Enter
					+ VhallUtil.PWD + Enter
					+ "--" + boundary + "--";
			
			byte[] xmlBytes = new byte[fis.available()];
			fis.read(xmlBytes);
			
			dos.writeBytes(part1);
			dos.write(xmlBytes);
			dos.writeBytes(part2);
			dos.writeBytes(part3);
			dos.writeBytes(part4);
			dos.writeBytes(part5);
			
			dos.flush();
			dos.close();
			fis.close();
			
			System.out.println("status code: "+conn.getResponseCode());
            InputStream in = conn.getInputStream();
            int ch;
            StringBuilder sb2 = new StringBuilder();
            while ((ch = in.read()) != -1)
            {
                sb2.append((char) ch);
            }
            resStr = sb2.toString();
            System.out.println(resStr);
			conn.disconnect();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return resStr;
	}
	
	public static void main(String[] args) throws InterruptedException {
		/*String str = CodeUtil.getRandomUUID();
		String whUrl = "https://e.vhall.com/api/vhallapi/v2/user/register";
		
		Map<String, String> parameters = new TreeMap<String, String>();
		 公共参数 
		parameters.put("auth_type", AUTO_TYPE);
		parameters.put("account", ACCOUNT);
		parameters.put("password", PWD);
		 公共参数 
		parameters.put("third_user_id", "yuxin000");
		parameters.put("pass", "000000");
		parameters.put("head", "http://www.ixincheng.com/web/images/defaultHead/18.png");
		
		String json = HttpUtil.sendPostRequest(whUrl, parameters);
		Map<String, String> m =json2Map(json);
		if(m.get("msg").equals("success")){
			Map<String, String> map =json2Map(m.get("data"));
			String vhallId = map.get("user_id");
			System.out.println(vhallId);
		}*/
//		OnlineUser u = new OnlineUser();
//		u.setLoginName("15399973503");
//		u.setSmallHeadPhoto("http://attachment-center.ixincheng.com:38080/data/attachment/online/2017/08/07/20/0c15539fd1d4484b8fb3b022e27d6f7b.png");
//		u.setName("wwwww2wwwww");
//		String password = null;
//		updateUser(u, password);
//		System.out.println("\u7b2c\u4e09\u65b9\u7528\u6237\u5bf9\u8c61\u4e0d\u5b58\u5728");
		/*Webinar webinar = new Webinar();
		webinar.setSubject("一场api引起的show");
		Date d = new Date();
		String start_time = d.getTime() + "";
		start_time = start_time.substring(0, start_time.length() - 3);
		webinar.setStart_time(start_time);
		webinar.setHost("红星闪闪我的心");
		webinar.setLayout("3");
		String id = createWebinar(webinar);*/
//		getWebinarUrl(977685530+"");
		String ks = getKValue();
		System.out.println(cheackKValue(ks));
		Thread.sleep(1000);
		System.out.println(cheackKValue(ks));
		Thread.sleep(1000);
		System.out.println(cheackKValue(ks));
		Thread.sleep(1000);
		System.out.println(cheackKValue(ks));
		Thread.sleep(1000);
		System.out.println(cheackKValue(ks));
		Thread.sleep(1000);
		System.out.println(cheackKValue(ks));
		Thread.sleep(1000);
		System.out.println(cheackKValue(ks));
		Thread.sleep(1000);
	}

	public static String getKValue(){
		String timestr = new Date().getTime()/1000+"";
		System.out.println(CodeUtil.encodePassword(timestr,"adsf"));
		System.out.println("ts"+timestr);
		return CodeUtil.encodePassword(timestr,"adsf");
	}

	/**
	 * Description：校验k值
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 4:37 2017/9/28 0028
	 **/
	public static boolean cheackKValue(String k){
		Set ks = getKSignSet();
		if(ks.contains(k)){
			return true;
		}
		return false;
	}

	public static Set getKSignSet(){
		long time = new Date().getTime()/1000;
		Set ksSet = new HashSet();
		ksSet.add(CodeUtil.encodePassword(time+"","adsf"));
		time--;
		ksSet.add(CodeUtil.encodePassword(time+"","adsf"));
		time--;
		ksSet.add(CodeUtil.encodePassword(time+"","adsf"));
		time--;
		ksSet.add(CodeUtil.encodePassword(time+"","adsf"));
		time--;
		ksSet.add(CodeUtil.encodePassword(time+"","adsf"));
		return ksSet;
	}
}
