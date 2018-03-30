package com.xczhihui.bxg.online.manager.vhall;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xczhihui.bxg.common.util.HttpUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.manager.vhall.bean.Webinar;

/**
 * @author Administrator
 */

/**
 * ClassName: VhallUtil.java <br>
 * Description: <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 2017年9月7日<br>
 */
public class VhallUtil {

    private static String ACCOUNT = "v19624388";
    private static String PWD = "xinchengzhihui";
    private static String AUTO_TYPE = "1";

    private static String USER_REGISTER = "https://e.vhall.com/api/vhallapi/v2/user/register";//用户注册
    private static String USER_UPDATE = "https://e.vhall.com/api/vhallapi/v2/user/update";//用户更新

    private static String WEBINAR_CREATE = "https://e.vhall.com/api/vhallapi/v2/webinar/create";//直播间创建
    private static String WEBINAR_UPDATE = "http://e.vhall.com/api/vhallapi/v2/webinar/update";//直播间创建
    private static String WEBINAR_START = "https://e.vhall.com/api/vhallapi/v2/webinar/start";//获取直播间地址
    private static String CALLBACK_URL = "http://e.vhall.com/api/vhallapi/v2/webinar/change-callback";//设置回调地址
    private static String CHANGE_USER_POWER = "http://e.vhall.com/api/vhallapi/v2/user/change-user-power";//修改用户权限
    
  
    private static String record_list ="http://e.vhall.com/api/vhallapi/v2/record/list";	//获取直播回放列表


    /**
     * Description：创建用户
     * @param u
     * @param password
     * @return
     * @return String
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    public static String createUser(OnlineUser u, String password) {
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
        Map<String, String> m = json2Map(json);
        if ("success".equals(m.get("msg"))) {
            Map<String, String> map = json2Map(m.get("data"));
            String vhallId = map.get("user_id");
            System.out.println(json + ":" + u.getLoginName() + ":" + password);
            return vhallId;
        }
        return null;
    }

    //20264468
    public static Map<String, String> json2Map(String jsonStr) {
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        Set<String> jsonKeySet = jsonObj.keySet();
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
    public static String updateUser(OnlineUser u, String password) {
        Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
        parameters.put("auth_type", AUTO_TYPE);
        parameters.put("account", ACCOUNT);
        parameters.put("password", PWD);
		/* 公共参数 */
        parameters.put("third_user_id", u.getId());
        if (password != null) {
            parameters.put("pass", password);
        }
        if (u.getSmallHeadPhoto() != null) {
            parameters.put("head", u.getSmallHeadPhoto());
        }
        if (u.getName() != null) {
            parameters.put("name", u.getName());
        }

        String json = HttpUtil.sendPostRequest(USER_UPDATE, parameters);
        Map<String, String> m = json2Map(json);
        if ("success".equals(m.get("msg"))) {
            Map<String, String> map = json2Map(m.get("data"));
            String vhallId = map.get("user_id");
            System.out.println(json + ":" + u.getLoginName() + ":" + password);
            return vhallId;
        }
        return null;
    }

    /**
     * Description：创建一个直播
     * @param u
     * @param password
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
        if (webinar.getUser_id() != null) {
            parameters.put("user_id", webinar.getUser_id());
        }
        if (webinar.getLayout() != null) {
            parameters.put("layout", webinar.getLayout());
        }
        if (webinar.getType() != null) {
            parameters.put("type", webinar.getType());
        }
        if (webinar.getAuto_record() != null) {
            parameters.put("auto_record", webinar.getAuto_record());
        }
        if (webinar.getHost() != null) {
            parameters.put("host", webinar.getHost());
        }
        if (webinar.getIntroduction() != null) {
            parameters.put("introduction", webinar.getIntroduction());
        }

        String json = HttpUtil.sendPostRequest(WEBINAR_CREATE, parameters);
        System.out.println(json);
        Map<String, String> m = json2Map(json);

        JSONObject js = JSONObject.parseObject(json);
        if ("200".equals(js.get("code"))) {
            System.out.println(js.toJSONString());
            return js.get("data").toString();
        }
        return null;
    }

    /**
     * Description：创建一个直播
     * @param u
     * @param password
     * @return
     * @return String
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    public static String updateWebinar(Webinar webinar) {
        Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
        parameters.put("auth_type", AUTO_TYPE);
        parameters.put("account", ACCOUNT);
        parameters.put("password", PWD);
		/* 公共参数 */

        parameters.put("webinar_id", webinar.getId());
        parameters.put("subject", webinar.getSubject());
        parameters.put("start_time", webinar.getStart_time());
        if (webinar.getUser_id() != null) {
            parameters.put("user_id", webinar.getUser_id());
        }
        if (webinar.getLayout() != null) {
            parameters.put("layout", webinar.getLayout());
        }
        if (webinar.getType() != null) {
            parameters.put("type", webinar.getType());
        }
        if (webinar.getAuto_record() != null) {
            parameters.put("auto_record", webinar.getAuto_record());
        }
        if (webinar.getHost() != null) {
            parameters.put("host", webinar.getHost());
        }
        if (webinar.getIntroduction() != null) {
            parameters.put("introduction", webinar.getIntroduction());
        }


        String json = HttpUtil.sendPostRequest(WEBINAR_UPDATE, parameters);
        System.out.println(json);
        Map<String, String> m = json2Map(json);
        if ("200".equals(m.get("code"))) {
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
    public static String getWebinarUrl(String webinarId, String is_sec_auth) {
        Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
        parameters.put("auth_type", AUTO_TYPE);
        parameters.put("account", ACCOUNT);
        parameters.put("password", PWD);
		/* 公共参数 */

        parameters.put("webinar_id", webinarId);
        if (is_sec_auth != null) {
            parameters.put("is_sec_auth", is_sec_auth);
        }

        String json = HttpUtil.sendPostRequest(WEBINAR_START, parameters);
        System.out.println(json);
        Map<String, String> m = json2Map(json);
        if ("200".equals(m.get("code"))) {
            System.out.println(m.get("data"));
            return m.get("data");
        }
        return null;
    }

    /**
     * Description：设置封面
     * @param auth_type
     * @param account
     * @param password
     * @param webinar_id
     * @param image
     * @return
     * @return String
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    public static String setActiveImage(String webinar_id, String image) {
        String resStr = null;
        try {
            String boundary = "Boundary-b1ed-4060-99b9-fca7ff59c113"; //Could be any string
            String Enter = "\r\n";

            File file = new File(image);
            FileInputStream fis = new FileInputStream(file);

            URL url = new URL("http://e.vhall.com/api/vhallapi/v2/webinar/activeimage");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);

            conn.connect();

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

            //part 1
            String part1 = "--" + boundary + Enter
                    + "Content-Type: application/octet-stream" + Enter
                    + "Content-Disposition: form-data; filename=\"" + file.getName() + "\"; name=\"image\"" + Enter + Enter;
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

            System.out.println("status code: " + conn.getResponseCode());
            InputStream in = conn.getInputStream();
            int ch;
            StringBuilder sb2 = new StringBuilder();
            while ((ch = in.read()) != -1) {
                sb2.append((char) ch);
            }
            resStr = sb2.toString();
            System.out.println(resStr);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resStr;
    }

    public static String downUrlImage(String fileUrl, String downPath) {
        File savePath = new File(downPath);
        if (!savePath.exists()) {
            savePath.mkdir();
        }
        String[] urlname = fileUrl.split("/");
        int len = urlname.length - 1;
        String uname = urlname[len];//获取文件名
        try {
            File file = new File(savePath + "/" + uname);//创建新文件
            if (file != null && !file.exists()) {
                file.createNewFile();
            }
            OutputStream oputstream = new FileOutputStream(file);
            URL url = new URL(fileUrl);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
            uc.connect();
            InputStream iputstream = uc.getInputStream();
            System.out.println("file size is:" + uc.getContentLength());//打印文件长度
            byte[] buffer = new byte[4 * 1024];
            int byteRead = -1;
            while ((byteRead = (iputstream.read(buffer))) != -1) {
                oputstream.write(buffer, 0, byteRead);
            }
            oputstream.flush();
            iputstream.close();
            oputstream.close();
        } catch (Exception e) {
            System.out.println("读取失败！");
            e.printStackTrace();
        }
        System.out.println("生成文件路径：" + savePath + "/" + uname);
        return savePath + "/" + uname;
    }

    /**
     * Description：设置回调地址
     * @param webinarId
     * @param callback_url
     * @param private_key
     * @return
     * @return String
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    public static String setCallbackUrl(String webinarId, String callback_url, String private_key) {
        Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
        parameters.put("auth_type", AUTO_TYPE);
        parameters.put("account", ACCOUNT);
        parameters.put("password", PWD);
		/* 公共参数 */

        parameters.put("webinar_id", webinarId);
        parameters.put("callback_url", callback_url);
        parameters.put("private_key", private_key);

        String json = HttpUtil.sendPostRequest(CALLBACK_URL, parameters);
        System.out.println(json);
        Map<String, String> m = json2Map(json);
        if ("200".equals(m.get("code"))) {
            System.out.println(m.get("data"));
            return m.get("data");
        }
        return null;
    }


    /**
     * Description：修改用户权限
     * @param webinarId
     * @param callback_url
     * @param private_key
     * @return
     * @return String
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     **/
    public static void changeUserPower(String userId, String isChild, String assign) {
        Map<String, String> parameters = new TreeMap<String, String>();
		/* 公共参数 */
        parameters.put("auth_type", AUTO_TYPE);
        parameters.put("account", ACCOUNT);
        parameters.put("password", PWD);
		/* 公共参数 */

        parameters.put("user_id", userId);
        parameters.put("is_child", isChild);//是否是子帐号 1是 0不是
        parameters.put("assign", assign);

        String json = HttpUtil.sendPostRequest(CHANGE_USER_POWER, parameters);
        System.out.println(json);
        Map<String, String> m = json2Map(json);
        if ("200".equals(m.get("code"))) {
            System.out.println("修改用户权限" + m.get("msg"));
        }
    }
    
    
    
	/**
	 * Description：查看回放信息  --》来判断这个回放是否有效，来判断这个直播是否应该下架
	 * @param userId
	 * @return
	 * @return String
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public static String recordList(String videoId) {

	    Map<String, String> parameters = new TreeMap<String, String>();
		
		/* 公共参数 */
        parameters.put("auth_type", AUTO_TYPE);
        parameters.put("account", ACCOUNT);
        parameters.put("password", PWD);
		/* 公共参数 */

		parameters.put("webinar_id", videoId);
		String json = HttpUtil.sendPostRequest(record_list, parameters);
		JSONObject js = JSONObject.parseObject(json);
		
		System.out.println(js.toJSONString());
		
		if(  ("success".equals(js.get("msg")) || "成功".equals(js.get("msg")) ) && 
				Integer.parseInt(js.get("code").toString()) == 200){
			
			
			/*
			 * 判断这个回放的所有时长相加等于多少
			 */
			js = (JSONObject)js.get("data");
			
			JSONArray jsonArray =  (JSONArray) js.get("lists");
			Integer countDuration = 0;
			for (Object jobj : jsonArray) {
				JSONObject jsonObject = (JSONObject)jobj;
				System.out.println(jsonObject.get("duration").toString());
				countDuration+=Integer.parseInt(jsonObject.get("duration").toString());
			}
			
			
			System.out.println("countDuration:"+countDuration);
			if(countDuration>300){//总长度大于5分钟才可以上架
				return "ok";
			}else{
				return "error";
			}
		}else{
			System.out.println(js.toJSONString());
		}
		return "error";
	}

}

