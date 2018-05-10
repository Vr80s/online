package com.xczhihui.bxg.online.web.controller;

import java.security.MessageDigest;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.common.support.domain.BxgUser;
import com.xczhihui.common.util.WeihouInterfacesListUtil;
import com.xczhihui.common.util.bean.ResponseObject;
import com.xczhihui.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.web.service.UserService;

/**
 * @Author yuruixin
 * @Date 2017/7/17
 */
@RestController
@RequestMapping(value = "/weihou")
public class WeihouController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());


	@Autowired
	private UserService service;
	
	/**
	 * 微吼k值验证回调接口
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/pass")
	public void vPass(HttpServletRequest request, HttpServletResponse response,
			String email, String k) throws Exception {
		ServletInputStream ris = request.getInputStream();
		StringBuilder content = new StringBuilder();
		byte[] b = new byte[1024];
		int lens = -1;
		while ((lens = ris.read(b)) > 0) {
			content.append(new String(b, 0, lens));
		}
		String strcont = content.toString();// 内容
		response.setContentType("text/html;charset=UTF-8");
		if ("yrxk".equals(k)) {
			response.getWriter().write("pass");
		} else {
			response.getWriter().write("fail");
		}
	}
	
	/**
	 * 获取微吼签名啦
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getWeihouSign")
	public ResponseObject getWeihouSign(HttpServletRequest req,
			HttpServletResponse response) throws Exception {
		
		String roomNumber = req.getParameter("videoId");  //视频id
		BxgUser sessionLoginUser = UserLoginUtil.getLoginUser(req);
		if(sessionLoginUser!=null) {
			OnlineUser user = service.findUserByLoginName(sessionLoginUser.getLoginName());
			String email = user.getLoginName();
			if(email!=null && email.indexOf("@")==-1){
				email+="@163.com";
			}
			Date d = new Date();
			String start_time = d.getTime() + "";
			start_time = start_time.substring(0, start_time.length() - 3);
			Map<String,String> map = new TreeMap<String,String>();
			map.put("app_key", WeihouInterfacesListUtil.APP_KEY);  //微吼key
			map.put("signedat", start_time); //时间戳，精确到秒  
			map.put("email", email);         //email 自己写的
			map.put("roomid", roomNumber);   //视频id
			map.put("account",user.getId());       //用户帐号
			map.put("username",user.getName());      //用户名
			System.out.println(map.toString());
			String sing = getSign(map);
			System.out.println(sing);
			map.put("sign",sing);
			return ResponseObject.newSuccessResponseObject(map);
		}
		
		
		return null;
	}
	
	public static void main(String[] args) {

		/**
		 * md5加密一定要统一字符集
		 */
		Map<String,String> map = new TreeMap<String,String>();
		map.put("app_key", "71a22e5b4a41483d41d96474511f58f3");  //微吼key
		Date d = new Date();
		String start_time = d.getTime() + "";
		start_time = start_time.substring(0, start_time.length() - 3);
		System.out.println(start_time);
		
		//map.put("signedat", "1523932395"); //时间戳，精确到秒  
		map.put("email", "15936216273@163.com");         //email 自己写的
		map.put("roomid", "929267329");   //视频id
		map.put("account","ef894375d67146478869ed0b3d7ccd66");       //用户帐号
		map.put("username","杨宣");      //用户名
		
		String sing = getSign(map);
		System.out.println("sing:"+sing);
		
		
	}
	
	public static String getSign(Map<String,String> signkv){
		Set<String> keySet = signkv.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append(WeihouInterfacesListUtil.APP_SECRET_KEY);
        while (iter.hasNext()) {
            String key = iter.next();
            sb.append(key + signkv.get(key));
        }
        sb.append(WeihouInterfacesListUtil.APP_SECRET_KEY);
        
        System.out.println("sb.toString():"+sb.toString());
        return getMD5(sb.toString());
	}
	
	
	
	/** 
     * 生成md5 
     * @param message 
     * @return 
     */  
	public static String getMD5(String message) {
		String md5str = "";
		try {
			// 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
			MessageDigest md = MessageDigest.getInstance("MD5");
			//md.update(message.getBytes("UTF-8")); 
			// 2 将消息变成byte数组
			byte[] input = message.getBytes("UTF-8");
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
	

}
