package com.xczh.consumer.market.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.bean.WxcpClientUserWxMapping;
import com.xczh.consumer.market.service.AppBrowserService;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OLAttachmentCenterService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.service.WxcpClientUserService;
import com.xczh.consumer.market.service.WxcpClientUserWxMappingService;
import com.xczh.consumer.market.utils.ConfigUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczh.consumer.market.utils.Token;
import com.xczh.consumer.market.utils.UCCookieUtil;
import com.xczh.consumer.market.vo.ItcastUser;
import com.xczh.consumer.market.wxpay.util.CommonUtil;
import com.xczh.consumer.market.wxpay.util.WeihouInterfacesListUtil;
import com.xczhihui.bxg.online.api.service.CityService;
import com.xczhihui.bxg.online.api.service.UserCoinService;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.bean.TokenExpires;
import com.xczhihui.user.center.bean.UserOrigin;
import com.xczhihui.user.center.bean.UserSex;
import com.xczhihui.user.center.bean.UserStatus;
import com.xczhihui.user.center.bean.UserType;

/**
 * 用户controller
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/bxg/user")
public class OnlineUserController {
	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private WxcpClientUserWxMappingService wxcpClientUserWxMappingService;
	@Autowired
	private UserCenterAPI userCenterAPI;
	@Autowired
	private WxcpClientUserService wxcpClientUserService;
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private UserCoinService userCoinService;
	@Autowired
	private OLAttachmentCenterService service;
	@Autowired
	private AppBrowserService appBrowserService;
	
	@Autowired
	private CityService cityService;
	
	@RequestMapping("login")
	@ResponseBody
	public ResponseObject login(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String openId = req.getParameter("openId");
		
		if(null == username || null == password || null == openId){	
			return ResponseObject.newErrorResponseObject("参数异常");
		}
		Token t = null;
		try {
			t = userCenterAPI.loginMobile(username, password, TokenExpires.TenDay);
		} catch (RuntimeException e) {
			return ResponseObject.newErrorResponseObject("用户名密码错误");
		}
		if (t != null) {
			OnlineUser o = onlineUserService.findUserByLoginName(username);
			WxcpClientUserWxMapping wxcpClientUserWxMapping =wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByOpenId(openId);
			if (o != null) {
				if (o.isDelete() || o.getStatus() == -1){
					return ResponseObject.newErrorResponseObject("用户已禁用");
				}
				if(wxcpClientUserWxMapping!=null && wxcpClientUserWxMapping.getClient_id() == null){
					wxcpClientUserWxMapping.setClient_id(o.getId());
					wxcpClientUserWxMappingService.update(wxcpClientUserWxMapping);
				}
				o.setTicket(t.getTicket());
				this.onlogin(req, res, t, o);
				return ResponseObject.newSuccessResponseObject(o);
			} else {
				boolean ise = Pattern.matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$",username);
				if (ise) {
					ItcastUser user = userCenterAPI.getUser(username);
					OnlineUser newUser = onlineUserService.addUser(username, user.getNike_name(),null,password);
					WxcpClientUserWxMapping m =
							wxcpClientUserWxMappingService.getWxcpClientUserWxMappingByOpenId(openId);
					if(null != m){
						m.setClient_id(newUser.getId());
					}
					wxcpClientUserWxMappingService.update(m);
					this.onlogin(req, res, t, newUser);
					return ResponseObject.newSuccessResponseObject(newUser);
				}
			}
			return ResponseObject.newErrorResponseObject("在线系统不存在此用户");
		} else {
			return ResponseObject.newErrorResponseObject("用户名密码错误");
		}
	}
	/**
	 * 登陆成功处理
	 * @param req
	 * @param res
	 * @param token
	 * @param user
	 */
	public void onlogin(HttpServletRequest req, HttpServletResponse res, Token token, OnlineUser user){
		UCCookieUtil.writeTokenCookie(res, token);
		HttpSession session = req.getSession();
		session.setAttribute("_user_", user);
		session.setMaxInactiveInterval(86400);
	}
	/**
	 * 退出登录
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("logout")
	@ResponseBody
	public ResponseObject logout(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		UCCookieUtil.clearTokenCookie(res);
		req.getSession().setAttribute("_user_", null);
		String token = req.getParameter("token");
		if(token!=null){
			cacheService.delete(token);
		}
		return ResponseObject.newSuccessResponseObject("退出成功");
	}
	
	/**
	 * 微信退出登录
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("wechatLogout")
	public void logoutWechat(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		
	    System.out.println("wx return code:" + req.getParameter("code"));
		ConfigUtil cfg = new ConfigUtil(req.getSession());
		String returnOpenidUri = cfg.getConfig("returnOpenidUri");
		try {
			String code = req.getParameter("code");
			String code_buffer = CommonUtil.getOpenId(code);
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(code_buffer);//Map<String, Object> access_info =GsonUtils.fromJson(code_buffer, Map.class);
			String openid = (String)jsonObject.get("openid");
			
			UCCookieUtil.clearTokenCookie(res);
			req.getSession().setAttribute("_user_", null);
			String token = req.getParameter("token");
			if(token!=null){
				cacheService.delete(token);
			}
			
			res.sendRedirect(returnOpenidUri + "/bxg/page/login/1?openid="+openid);
		} catch (Exception e) {
			e.printStackTrace();
			res.sendRedirect(returnOpenidUri + "/bxg/page/login/1?error=error");
		}
	}
	
	
	/**
	 * 图形验证码
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="vcode")
	public void vcode(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception{
		int width = 105, height = 46;
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, width - 1, height - 1);
		g.setColor(Color.GRAY);
		Random random = new Random();            
		for (int i = 0; i < 40; i++) {
		    int x1 = random.nextInt(width);
		    int y1 = random.nextInt(height);
		    int x2 = random.nextInt(10);
		    int y2 = random.nextInt(10);            
		    g.drawLine(x1, y1, x1 + x2, y1 + y2);
		}
		Font font = new Font("Times New Roman", Font.PLAIN, 30);
		g.setFont(font);

	    String[] ops={"+","-","*","/","="};//定义运算符
	    int num1=random.nextInt(10);//生成第一个操作数
	    String strRand1 = String.valueOf(num1);
	    int red1 = random.nextInt(255);
	    int green1 = random.nextInt(255);
	    int blue1 = random.nextInt(255);
	    g.setColor(new Color(red1, green1, blue1)); //画出第一个操作数        
	    g.drawString(strRand1, 13 * 0 + 6, 30);
	   
	    //int op_num=random.nextInt(4);//随机生成一个运算符数组中的下标，从而得到随机的一个运算符。这里是0~3之间一个随机值。因为4是等号
	    int op_num=random.nextInt(2);//只有加减
	    String strRand2 =(String)ops[op_num];
	    int red2 = random.nextInt(255);
	    int green2 = random.nextInt(255);
	    int blue2 = random.nextInt(255);
	    g.setColor(new Color(red2, green2, blue2));       //画出操作运算符     
	    g.drawString(strRand2, 13 * 1 + 16, 30);
	   
	    int num2=(random.nextInt(9)+1);    //随机生成0~8之间的一个数+1，作为第二个操作数。因为有可能出现除法，所以第二个操作数不能为0。所以+1，使数在1~9之间。
	    String strRand3 = String.valueOf(num2);
	    int red3 = random.nextInt(255);
	    int green3 = random.nextInt(255);
	    int blue3 = random.nextInt(255);
	    g.setColor(new Color(red3, green3, blue3));       //画出第二个操作数     
	    g.drawString(strRand3, 13 * 2 + 26, 30);
	   
	    String strRand4 =(String)ops[4] ;
	    int red4 = random.nextInt(255);
	    int green4 = random.nextInt(255);
	    int blue4 = random.nextInt(255);
	    g.setColor(new Color(red4, green4, blue4));            //画出等号
	    g.drawString(strRand4, 13 * 3 + 30, 30);
	    
	    Integer randomCode=0;         //由运算符的不同执行不同的运算，得到验证码结果值
	    switch(op_num){
		    case 0: randomCode = num1+num2;
		    	break;
		    case 1: randomCode = num1-num2;
		    	break;
//			    case 2: randomCode = num1*num2;
//			    	break;  
//			    case 3: randomCode = num1/num2;
//			    	break;    
	    }
		String appUniqueId = req.getParameter("appUniqueId");
		if(appUniqueId!=null && !"".equals(appUniqueId)){//来自app端的请求
			 String imgCode = appUniqueId +"code";
			 cacheService.set(imgCode,randomCode.toString(), 1*1800);
		}else{                                           //来自网页请求
			 HttpSession session = req.getSession();
			 session.setAttribute("randomCode", randomCode.toString());//把运算符结果值set到session中，用于其他文件进行验证码校对    
		}
	    buffImg.flush();
	    g.dispose();
	    res.setContentType("image/jpeg");
	    res.setHeader("Pragma", "no-cache");
	    res.setHeader("Cache-Control", "no-cache"); 	
	    res.setDateHeader("Expires", 0);
	    OutputStream outputStream = res.getOutputStream();
	    ImageIO.write(buffImg, "jpeg", outputStream);
	    outputStream.flush();
	    outputStream.close();
	}
	/**
	 * 发送短信验证码
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value="sendCode")
	@ResponseBody
	public ResponseObject sendCode(HttpServletRequest req, HttpServletResponse res, Map<String, String> params){
		ResponseObject response = new ResponseObject();
		
//		String appUniqueId = req.getParameter()("appUniqueId");
//		if(appUniqueId!=null && !"".equals(appUniqueId)){//来自app端的请求
//			 String imgCode = appUniqueId +"code";
//			 String vcode = cacheService.get(imgCode);
//			 if (req.getParameter()("vcode") == null
//						|| !req.getParameter()("vcode").equals(vcode)) {
//					return ResponseObject.newErrorResponseObject("图形验证码错误");
//				}
//		}else{                                           //来自网页请求
//			HttpSession session = req.getSession();
//			if (req.getParameter()("vcode") == null
//					|| !req.getParameter()("vcode").equals(session.getAttribute("randomCode"))) {
//				return ResponseObject.newErrorResponseObject("图形验证码错误");
//			}
//		}
		
		//类型，1注册，2重置密码
		String vtype = req.getParameter("vtype") == null ? "1" : req.getParameter("vtype");
		//手机号
		String username = req.getParameter("username");
		
		try {
			String str = onlineUserService.addMessage(username, vtype);
			if("发送成功！".equals(str)){
				return ResponseObject.newSuccessResponseObject(str);
			}else{
				return ResponseObject.newErrorResponseObject(str);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("获取错误信息啦"+e.getMessage());
			return ResponseObject.newErrorResponseObject("发送失败");
			
		}
	}
	/**
	 * 手机提交注册
	 * @param username
	 * @param password
	 * @param code
	 * @param req
	 * @return
	 */
	@RequestMapping(value="phoneRegist")
	@ResponseBody
	@Transactional
	public ResponseObject phoneRegist(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		String password = req.getParameter("password");
		String mobile = req.getParameter("mobile");
		String openId = req.getParameter("openId");
		String code = req.getParameter("code");
		//String vtype = req.getParameter()("vtype");
		String vtype = "1";
		
		if(null == password || null == mobile || null == openId || null == code){
			return ResponseObject.newErrorResponseObject("参数异常");
		}
		//短信验证码
		ResponseObject checkCode = onlineUserService.checkCode(mobile, code,vtype);
		if (!checkCode.isSuccess()) {
			return checkCode;
		}
		return onlineUserService.addPhoneRegist(req, password,mobile,openId);
	}
	/**
	 * 获取shareCode
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getShareCode")
	@ResponseBody
	public ResponseObject getShareCode(HttpServletRequest req, HttpServletResponse res, Map<String, String> params)throws Exception{
		String id = req.getParameter("id");
		if(StringUtils.isBlank(id) || id.equals("undefined")){
			return ResponseObject.newSuccessResponseObject("");
		}
		OnlineUser user = onlineUserService.findUserById(id);
		if(null == user){
			ResponseObject.newErrorResponseObject("查询不到用户信息");
		}
		return ResponseObject.newSuccessResponseObject(user.getShareCode() == null ? "null" : user.getShareCode());
	}
	/**
	 * 判断用户是否登录着
	 * @param req
	 * @param res
	 * @param params
	 * @return 如果登录着返回当前用户，否则返回错误
	 * @throws Exception
	 */
	@RequestMapping("isLogined")
	@ResponseBody
	public ResponseObject isLogined(HttpServletRequest req, HttpServletResponse res, Map<String, String> params)throws Exception{
		Object ou = req.getSession().getAttribute("_user_");
		OnlineUser user = null;
		Token t = UCCookieUtil.readTokenCookie(req);
		if (ou != null && t != null) { //正常登录着
			String userId = ((OnlineUser)ou).getId();
			user = onlineUserService.findUserById(userId);
		} else if (ou == null) { //session过期了，续期
			user = onlineUserService.findUserByLoginName(t.getLoginName());
			req.getSession().setAttribute("_user_", user);
		} else if (t == null){ //cookie过期了，直接退出
			req.getSession().setAttribute("_user_", null);
		}
		if (user == null) {
			return ResponseObject.newErrorResponseObject("请登录");
		}
		return ResponseObject.newSuccessResponseObject(user);
	}
	
	@RequestMapping("isSessionTimeout")
	@ResponseBody
	public ResponseObject sessionVelidate(HttpServletRequest req, HttpServletResponse res, Map<String, String> params)throws Exception{
		Object ou = req.getSession().getAttribute("_user_");
		if(ou==null){
			return ResponseObject.newSuccessResponseObject(false);
		}
		return ResponseObject.newSuccessResponseObject(ou);
	}
	
	/**
	 * Description：用户中心保存接口
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("userCenterFormSub")
	@ResponseBody
	@Transactional
	public ResponseObject userCenterFormSub(HttpServletRequest request, HttpServletResponse response)throws Exception{
		//TODO
        try{
        	 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
             MultipartFile fileMul = multipartRequest.getFile("file");  
             Map<String,String> map = new HashMap<String,String>();
             if(fileMul!=null && !fileMul.isEmpty()){  
                 // 获得文件名：   
                 String filename = fileMul.getOriginalFilename();   
                 // 获得输入流：   
                 //InputStream input = fileMul.getInputStream();   
                 
                 if(filename!=null && !filename.trim().equals("")){
                     filename = filename.toLowerCase();
         			if (!filename.endsWith("image")&& !filename.endsWith("gif")&& !filename.endsWith("jpg")
         					&& !filename.endsWith("png")&& !filename.endsWith("bmp")) {
         				return ResponseObject.newErrorResponseObject("文件类型有误");
         			}
         			String contentType =  fileMul.getContentType();//文件类型
         			byte [] bs = fileMul.getBytes();
         			String projectName="other";
         			String fileType="1"; //图片类型了
         			String headImgPath = service.upload(null, //用户中心的用户ID
     				projectName, filename,contentType, bs,fileType,null);
         			JSONObject json = JSONObject.parseObject(headImgPath);
         			System.out.println("文件路径——path:"+headImgPath);
         			map.put("smallHeadPhoto", json.get("url").toString());
                 }
             }  	
        	
          String sex= request.getParameter("sex");  
          String nickname= request.getParameter("nickname");  
          String email= request.getParameter("email");  
          String cityName= request.getParameter("cityName");
          String provinceName= request.getParameter("provinceName");
          String cityId= request.getParameter("cityId");
          String provinceId= request.getParameter("provinceId");
          map.put("sex", sex);
          map.put("nickname", nickname);
          map.put("email", email);
          map.put("cityName", cityName);
          map.put("provinceName", provinceName);
          map.put("cityId", cityId);
          map.put("provinceId", provinceId);
          
          OnlineUser user = new OnlineUser();
          String token = request.getParameter("token");
          if(token !=null ){
        	  user = cacheService.get(token);
          }else{
        	  user = (OnlineUser) request.getSession().getAttribute("_user_");
          }  
          if(null == user){
             return ResponseObject.newErrorResponseObject("获取用户信息有误");
          }
          if(sex!=null){
        	  Integer.parseInt(sex);
          }
          userCenterAPI.update(user.getLoginName(),nickname,sex!=null ? Integer.parseInt(sex) : 3,email, null, 10, 10);
          onlineUserService.updateUserCenterData(user,map);
          /**
           * 如果用户信息发生改变。那么就改变token的信息，也就是redsei里面的信息
           */
          OnlineUser newUser =   onlineUserService.findUserByLoginName(user.getLoginName());
          cacheService.delete(token);
          cacheService.set(token, newUser, TokenExpires.TenDay.getExpires());
          String weiHouResp = WeihouInterfacesListUtil.updateUser(user.getId(),null,map.get("nickname"),map.get("smallHeadPhoto"));
          if(weiHouResp == null){
        	  System.out.println("同步微吼昵称，头像失败");
          }
          //先这样处理，到时他们在线的时候在给他们说下吧
       /*   if(map.get("sex").equals("2")){
        	  map.remove("sex");
          }*/
          return ResponseObject.newSuccessResponseObject(map);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("后台处理流程异常");
        }
	}
	
	
	/**
	 * Description：用户中心保存接口
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping("userCenterFormSub1")
	@ResponseBody
	@Transactional
	public ResponseObject userCenterFormSub1(HttpServletRequest req, HttpServletResponse response)throws Exception{
		//TODO
        try{
          OnlineUser user = new OnlineUser();
          String token = req.getParameter("token");
          if(token !=null ){
        	  user = cacheService.get(token);
          }else{
        	  user = (OnlineUser) req.getSession().getAttribute("_user_");
          }  
          if(null == user){
             return ResponseObject.newErrorResponseObject("获取用户信息有误");
          }
          Map<String,String> map = new HashMap<String,String>();
          String sex= req.getParameter("sex");  
          String nickname= req.getParameter("nickname");  
          String email= req.getParameter("email");  
          String info= req.getParameter("info"); 
          String provinceCityName= req.getParameter("provinceCityName"); 
          
          String occupation= req.getParameter("occupation"); 
          String occupationOther= req.getParameter("occupationOther"); 
//  		/**
// 		 * 职业
// 		 */
// 		private Integer occupation;
// 		/**
// 		 * 职业,其他
// 		 */
// 		private String occupationOther;
          
          map.put("sex", sex);
          map.put("nickname", nickname);
          map.put("email", email);
          map.put("info", info);
          map.put("occupation", occupation);
          map.put("occupationOther", occupationOther);
          
          
          if(StringUtils.isNotBlank(provinceCityName)){
        	  String [] str =  provinceCityName.split(" ");
        	  //根据名字得到id，好惨
        	  if(str.length ==2){
        		  String proince =  str[0].trim();
        		  String city =  str[1].trim();
        		  if(proince.indexOf("省")!=-1 || proince.indexOf("市")!=-1){
        			  proince = proince.substring(0,proince.length()-1);
        		  }else if(proince.endsWith("区")){
        			  proince = proince.substring(0,2);
        		  }
        		  if(city.indexOf("省")!=-1 || city.indexOf("市")!=-1){
        			  city = city.substring(0,city.length()-1);
        		  }else if(proince.endsWith("区") || proince.endsWith("州") || proince.endsWith("盟")){
        			  city = city.substring(0,2);
        		  }
        		  Integer [] code = cityService.getCodeByName(proince,city);
        		  map.put("provinceName", proince);
        		  map.put("cityName", city);
        		  map.put("provinceId", code[0]+"");
        		  map.put("cityId", code[1]+"");
        	  }
          }
          userCenterAPI.update(user.getLoginName(),nickname,org.springframework.util.StringUtils.hasText(sex) ? Integer.parseInt(sex) : 3,email, null, 10, 10);
          onlineUserService.updateUserCenterData(user,map);
          String weiHouResp = WeihouInterfacesListUtil.
        		  updateUser(user.getId(),null,nickname,null);
          /**
           * 更新session的值
           */
          OnlineUser newUser =   onlineUserService.findUserByLoginName(user.getLoginName());
          req.getSession().setAttribute("_user_",newUser);
          if(weiHouResp == null){
        	  System.out.println("同步微吼昵称失败");
          }
          return ResponseObject.newSuccessResponseObject(map);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("后台处理流程异常");
        }
	}
	/**
	 * Description：用户中心保存接口
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("userCenterFormSub2")
	@ResponseBody
	@Transactional
	public ResponseObject userCenterFormSub2(HttpServletRequest request, HttpServletResponse response, Map<String, String> params)throws Exception{
		//TODO
        try{
        	
        	 String base64Data = request.getParameter("base64Data");
        	 String imageName = request.getParameter("imageName");
        	 //logger.debug("上传文件的数据："+base64Data);
             String dataPrix = "";
             String data = "";

             //logger.debug("对数据进行判断");
             if(base64Data == null || "".equals(base64Data)){
                 throw new Exception("上传失败，上传图片数据为空");
             }else{
                 String [] d = base64Data.split("base64,");
                 if(d != null && d.length == 2){
                     dataPrix = d[0];
                     data = d[1];
                 }else{
                     throw new Exception("上传失败，数据不合法");
                 }
             }
             String suffix = "";
             if("data:image/jpeg;".equalsIgnoreCase(dataPrix)){//data:image/jpeg;base64,base64编码的jpeg图片数据
                 suffix = ".jpg";
             } else if("data:image/x-icon;".equalsIgnoreCase(dataPrix)){//data:image/x-icon;base64,base64编码的icon图片数据
                 suffix = ".ico";
             } else if("data:image/gif;".equalsIgnoreCase(dataPrix)){//data:image/gif;base64,base64编码的gif图片数据
                 suffix = ".gif";
             } else if("data:image/png;".equalsIgnoreCase(dataPrix)){//data:image/png;base64,base64编码的png图片数据
                 suffix = ".png";
             }else{
                 throw new Exception("上传图片格式不合法");
             }
            /* String tempFileName = getRandomFileName() + suffix;
             logger.debug("生成文件名为："+tempFileName);*/

             //因为BASE64Decoder的jar问题，此处使用spring框架提供的工具包
            byte[] bs123 = Base64Utils.decodeFromString(data);
        	
            String projectName="other";
 			String fileType="1"; //图片类型了
 			
 			Map<String,String> map = new HashMap<String,String>();
 			String headImgPath = service.upload(null, //用户中心的用户ID
				projectName, imageName, suffix, bs123,fileType,null);
 			JSONObject json = JSONObject.parseObject(headImgPath);
 			System.out.println("文件路径——path:"+headImgPath);
 			map.put("smallHeadPhoto", json.get("url").toString());
        	  
/*    	    MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
            MultipartFile fileMul = multipartRequest.getFile("file");  
            Map<String,String> map = new HashMap<String,String>();
            if(!fileMul.isEmpty()){  
                // 获得文件名：   
                String filename = fileMul.getOriginalFilename();   
                // 获得输入流：   
                //InputStream input = fileMul.getInputStream();   
                if(filename!=null && !filename.trim().equals("")){
                    filename = filename.toLowerCase();
        			if (!filename.endsWith("image")&& !filename.endsWith("gif")&& !filename.endsWith("jpg")
        					&& !filename.endsWith("png")&& !filename.endsWith("bmp")) {
        				return ResponseObject.newErrorResponseObject("文件类型有误");
        			}
        			String contentType =  fileMul.getContentType();//文件类型
        			byte [] bs = fileMul.getBytes();
        			String projectName="other";
        			String fileType="1"; //图片类型了
        			String headImgPath = service.upload(null, //用户中心的用户ID
    				projectName, filename,contentType, bs,fileType,null);
        			JSONObject json = JSONObject.parseObject(headImgPath);
        			System.out.println("文件路径——path:"+headImgPath);
        			map.put("smallHeadPhoto", json.get("url").toString());
                }
            }*/
            
          OnlineUser user = new OnlineUser();
          String token = request.getParameter("token");
          if(token !=null ){
        	  user = cacheService.get(token);
          }else{
        	  user = (OnlineUser) request.getSession().getAttribute("_user_");
          }  
          onlineUserService.updateUserCenterData(user,map);
          String weiHouResp = WeihouInterfacesListUtil.
        		  updateUser(user.getId(),null,null,map.get("smallHeadPhoto"));
          
          /**
           * 如果用户信息发生改变。那么就改变token的信息，也就是redsei里面的信息
           */
          OnlineUser newUser =   onlineUserService.findUserByLoginName(user.getLoginName());
          request.getSession().setAttribute("_user_",newUser);
          
          if(weiHouResp == null){
        	  System.out.println("同步微吼头像失败");
          }
          return ResponseObject.newSuccessResponseObject(map);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.newErrorResponseObject("后台处理流程异常");
        }
	}
	
	/**
	 * 微信绑定已经注册的手机号
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@SuppressWarnings("unused")
	@RequestMapping("wxBindAndPhoneNumberIsReg")
	@ResponseBody
	@Transactional
	public ResponseObject wxBindAndPhoneNumberIsReg(HttpServletRequest req,
                    HttpServletResponse res, Map<String, String> params)
			throws Exception {
		
		String username = req.getParameter("username"); //手机号
		String openId = req.getParameter("openId");     //openId
		String code = req.getParameter("code");         //验证码
		if(null == username || null == code || null == openId){
			return ResponseObject.newErrorResponseObject("网络不给力,刷新页面试试");
		}
		String vtype = "2";
		//短信验证码
		ResponseObject checkCode = onlineUserService.checkCode(username, code,vtype);
		if (!checkCode.isSuccess()) { //如果动态验证码不正确
			return checkCode;
		}
		/*
		 * 如果正确
		 *  从wx 记录表中得到用户的信息。然后
		 */
		WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.
				getWxcpClientUserWxMappingByOpenId(openId);
		if(null == m){
			return  ResponseObject.newErrorResponseObject("网络不给力,刷新页面试试");
		}
		/**
		 * 向用户中心添加数据
		 *  用户名、 密码、昵称、性别、邮箱、手机号
		 *  第三方登录的用户名和密码是opendi
		 */
	    ItcastUser iu = userCenterAPI.getUser(username);
		if(iu == null){
			return  ResponseObject.newErrorResponseObject("网络不给力,刷新页面试试");
		}
		/**
		 * 判断这个用户是否在外面注册过
		 */
		OnlineUser ou1 = onlineUserService.findUserByLoginName(username);
		
		m.setClient_id(ou1.getId());
		wxcpClientUserWxMappingService.update(m);
		
		if(ou1 !=null ){ //此用户已经注册过了
			
			ou1.setUnionId(m.getUnionid());
			onlineUserService.updateUserUnionidByid(ou1);
			
			
			Token t = userCenterAPI.loginThirdPart(username,iu.getPassword(), TokenExpires.TenDay);
			ou1.setTicket(t.getTicket());
			//把用户中心的数据给他  --这些数据是IM的
			ou1.setUserCenterId(iu.getId());
			ou1.setPassword(iu.getPassword());
			
			this.onlogin(req,res,t,ou1,t.getTicket());
			return  ResponseObject.newSuccessResponseObject(ou1);
		}else{
			return  ResponseObject.newErrorResponseObject("网络不给力,刷新页面试试");
		}
	}
	/**
	 * 微信绑定手机号此用户还没注册
	 * Description：
	 * @param req
	 * @param res
	 * @param params
	 * @throws Exception
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@RequestMapping("wxBindPhoneNumber")
	@ResponseBody
	@Transactional
	public ResponseObject wxBindPhoneNumber(HttpServletRequest req, HttpServletResponse res, Map<String, String> params)
			throws Exception {
        //TODO
		String username = req.getParameter("username"); //手机号
		String password = req.getParameter("password"); //密码
		String openId = req.getParameter("openId");     //openId
		String code = req.getParameter("code");         //验证码
		if(null == username || null == code || null == password){
			return ResponseObject.newErrorResponseObject("网络不给力,刷新页面试试");
		}
		String vtype = "1";
		//短信验证码
		ResponseObject checkCode = onlineUserService.checkCode(username, code,vtype);
		if (!checkCode.isSuccess()) { //如果动态验证码不正确
			return checkCode;
		}
		/*
		 * 如果正确
		 *  从wx 记录表中得到用户的信息。然后
		 */
		WxcpClientUserWxMapping m = wxcpClientUserWxMappingService.
				getWxcpClientUserWxMappingByOpenId(openId);
		if(null == m){
			return  ResponseObject.newErrorResponseObject("网络不给力,刷新页面试试");
		}

		/**
		 * 向用户中心添加数据
		 *  用户名、 密码、昵称、性别、邮箱、手机号
		 *  第三方登录的用户名和密码是opendi
		 */
	    ItcastUser iu = userCenterAPI.getUser(username);
		if(iu == null){
			userCenterAPI.regist(username, password,m.getNickname(), UserSex.parse(Integer.parseInt(m.getSex())), null,
					null, UserType.COMMON, UserOrigin.ONLINE, UserStatus.NORMAL);
			iu =  userCenterAPI.getUser(username);
		}
		/**
		 * 判断这个用户是否在外面注册过
		 */
		OnlineUser ou1 = onlineUserService.findUserByLoginName(username);
		
		if(ou1 !=null ){ //此用户已经注册过了
			
			ou1.setUnionId(m.getUnionid());
			//onlineUserMapper.updateUserUnionidByid(ou1);
			onlineUserService.updateUserUnionidByid(ou1);
			
			Token t = userCenterAPI.loginMobile(username,iu.getPassword(), TokenExpires.TenDay);
			ou1.setTicket(t.getTicket());
			//把用户中心的数据给他  --这些数据是IM的
			ou1.setUserCenterId(iu.getId());
			ou1.setPassword(iu.getPassword());
			
			onlogin(req,res,t,ou1,t.getTicket());
			return  ResponseObject.newSuccessResponseObject(ou1);
		}else{
			/**
			 * 保存用户信息到user表中。保存微信的unionid_如果这个用户表中存在这个id，那么说明已经登录过了！查找用户就ok。
			 */
			OnlineUser ou =  onlineUserService.findOnlineUserByUnionid(m.getUnionid());
			if(ou == null){
				OnlineUser u = new OnlineUser();
				u.setId(UUID.randomUUID().toString().replace("-", ""));
				u.setSex(Integer.parseInt(m.getSex()));
				u.setUnionId(m.getUnionid());
				u.setStatus(0);
				u.setCreateTime(new Date());
				u.setDelete(false);
				u.setName(m.getNickname());   //微信名字
				u.setSmallHeadPhoto(m.getHeadimgurl());//微信头像
				u.setVisitSum(0);
				u.setStayTime(0);
				u.setUserType(0);
				u.setOrigin("weixin");
				u.setMenuId(-1);
				u.setCreateTime(new Date());
				u.setType(1);
				
				String weihouUserId = WeihouInterfacesListUtil.createUser(u.getId(),WeihouInterfacesListUtil.moren, u.getName(), u.getSmallHeadPhoto());
				u.setVhallId(weihouUserId);  //微吼id
				u.setVhallPass(WeihouInterfacesListUtil.moren);        //微吼密码 
				u.setVhallName(u.getName());
				u.setPassword(iu.getPassword()); 
				u.setUserCenterId(iu.getId());
				u.setLoginName(username);
				 /**
				 * 将从微信获取的省市区信息变为对应的id和name
				 */
				System.out.println("country_:"+m.getCountry()+",province_:"+m.getProvince()+",city_:"+m.getCity());
				Map<String,Object> map = cityService.getSingProvinceByCode(m.getCountry());
				if(map!=null){
					Object objId = map.get("cid");
					int countryId = Integer.parseInt(objId.toString());
					u.setDistrict(countryId+"");
					map = cityService.getSingCityByCodeAndPid(m.getProvince(), countryId);
					if(map!=null){
						objId = map.get("cid");
						Object objName = map.get("name");	
						int provinceId = Integer.parseInt(objId.toString());
						u.setProvince(provinceId+"");
						u.setProvinceName(objName.toString());
						map = cityService.getSingDistrictByCodeAndPid(m.getCity(), provinceId);
						if(map!=null){
							objId = map.get("cid");
							objName = map.get("name");
							int cityId = Integer.parseInt(objId.toString());
							u.setCity(cityId+"");
							u.setCityName(objName.toString());
						}
					}
				}
				onlineUserService.addOnlineUser(u);
				ou = u;
			}
			
			/**
			 * 初始化一条代币记录
			 */
			userCoinService.saveUserCoin(ou.getId());
			m.setClient_id(ou.getId());
			wxcpClientUserWxMappingService.update(m);
			Token t = userCenterAPI.loginThirdPart(username,iu.getPassword(), TokenExpires.TenDay);
			ou.setTicket(t.getTicket());
			//把用户中心的数据给他  --这些数据是IM的
			ou.setUserCenterId(iu.getId());
			ou.setPassword(iu.getPassword());
			
			onlogin(req,res,t,ou,t.getTicket());
			return  ResponseObject.newSuccessResponseObject(ou);
		}
	}	
	/**
	 * 登陆成功处理
	 * @param req
	 * @param res
	 * @param token
	 * @param user
	 */
	@SuppressWarnings("unchecked")
	public void onlogin(HttpServletRequest req, HttpServletResponse res,
                        Token token, OnlineUser user, String ticket){
		
		String appUniqueId = req.getParameter("appUniqueId");
		if(StringUtils.isNotBlank(appUniqueId)){   //表示是app登录
			cacheService.set(ticket, user,TokenExpires.TenDay.getExpires());
			cacheService.set(user.getId(),ticket,TokenExpires.TenDay.getExpires());
			//Map<String,String> mapClientInfo =  com.xczh.consumer.market.utils.HttpUtil.getClientInformation(req);
			String model = req.getParameter("model");
			if(StringUtils.isNotBlank(model)){
				cacheService.set(user.getLoginName(),model,TokenExpires.TenDay.getExpires());
			}else{
				cacheService.set(user.getLoginName(),"其他设备",TokenExpires.TenDay.getExpires());
			}
		}else{
			// 用户登录成功
			// 第一个BUG的解决:第二个用户登录后将之前的session销毁!
			req.getSession().invalidate();
			// 第二个BUG的解决:判断用户是否已经在Map集合中,存在：已经在列表中.销毁其session.
			// 获得到ServletCOntext中存的Map集合.
			Map<OnlineUser, HttpSession> userMap = (Map<OnlineUser, HttpSession>) req.getServletContext()
					.getAttribute("userMap");
			// 判断用户是否已经在map集合中'
			HttpSession session = userMap.get(user);
			if(session!=null && userMap.containsKey(user)){
				/**
				 *  * 如果存在那么就注销原来的。或者把原来的session搞成一个表示，不是取用户信息的。
				 * 得到客户端信息
				 */
				Map<String,String> mapClientInfo =  com.xczh.consumer.market.utils.HttpUtil.getClientInformation(req);
				//session.invalidate();
				session.setAttribute("topOff", mapClientInfo);
				session.setAttribute("_user_",null);
			}else if(session!=null){
				session.setAttribute("topOff",null);
			}
			// 使用监听器:HttpSessionBandingListener作用在JavaBean上的监听器.
			req.getSession().setMaxInactiveInterval(86400);//设置session失效时间
			req.getSession().setAttribute("_user_", user);
			/**
			 * 这是cookie 
			 */
			UCCookieUtil.writeTokenCookie(res, token);
		}
	}
	
	
}
