package com.xczh.consumer.market.controller.user;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.common.util.WeihouInterfacesListUtil;
import com.xczhihui.common.util.enums.RegisterForm;
import com.xczhihui.common.util.enums.SMSCode;
import com.xczhihui.common.util.enums.TokenExpires;
import com.xczhihui.course.util.XzStringUtils;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.utils.UCCookieUtil;
import com.xczhihui.user.center.vo.OeUserVO;
import com.xczhihui.user.center.vo.Token;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * 用户controller
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Controller
@RequestMapping(value = "/xczh/user")
public class XzUserController {
	@Autowired
	private OnlineUserService onlineUserService;
	@Autowired
	private UserCenterService userCenterService;
	@Autowired
	private CacheService cacheService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(XzUserController.class);
	
	/**
	 * 
	 * Description：发送短信验证码
	 * @param req
	 * @param res
	 * @param vtype
	 * @param username
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@RequestMapping(value="sendCode")
	@ResponseBody
	public ResponseObject sendCode(HttpServletRequest req,
			@RequestParam("vtype")Integer vtype,
			@RequestParam("username")String username){
		
		//类型，1注册，  2重置密码   3 完善信息
		vtype = vtype == null ? SMSCode.RETISTERED.getCode() : vtype;
		try {
			if(!XzStringUtils.checkPhone(username)){
				return ResponseObject.newErrorResponseObject("请输入正确的手机号");
			}
			LOGGER.info("vtype"+vtype);
			String str = onlineUserService.addMessage(username, vtype);
			if("发送成功".equals(str)){
				return ResponseObject.newSuccessResponseObject(str);
			}else{
				return ResponseObject.newErrorResponseObject(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("获取错误信息啦"+e.getMessage());
			return ResponseObject.newErrorResponseObject("发送失败");
		}
	}
	
	/**
	 * h5普通浏览器、APP提交注册。微信公众号是没有注册的，直接就登录了
	 * @param req
	 * @return
	 */
	@RequestMapping(value="phoneRegist")
	@ResponseBody
	@Transactional
	public ResponseObject phoneRegist(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("password")String password,
			@RequestParam("username")String username,
			@RequestParam("code")String code) throws Exception {
		
		if(!XzStringUtils.checkPhone(username)){
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		}
		if(!XzStringUtils.checkPassword(password)){
			return ResponseObject.newErrorResponseObject("密码为6-18位英文大小写字母或者阿拉伯数字");
		}
		/*
		 * 验证短信验证码
		 */
		Integer vtype = SMSCode.RETISTERED.getCode();		//短信验证码类型
		ResponseObject checkCode = onlineUserService.checkCode(username, code,vtype);
		if (!checkCode.isSuccess()) {
			return checkCode;
		}
		
		/**
		 * 注册后默认登录啦
		 */
		OnlineUser ou =  onlineUserService.addPhoneRegistByAppH5(req, password,username,vtype);
		Token t =  userCenterService.loginMobile(username, password, TokenExpires.TenDay);
		ou.setTicket(t.getTicket());
		ou.setUserCenterId(t.getUserId());
		ou.setPassword(t.getPassWord());
		this.onlogin(req, res, t, ou,t.getTicket());
		
		/**
		 * 清除这个cookie
		 */
		UCCookieUtil.clearThirdPartyCookie(res);
		
		return ResponseObject.newSuccessResponseObject(ou);
	}
	
	/**
	 * h5、APP提交注册。微信公众号是没有注册的，直接就登录了
	 * @param req
	 * @return
	 */
	@RequestMapping(value="publicNumberPhoneRegist")
	@ResponseBody
	@Transactional
	public ResponseObject publicNumberPhoneRegist(HttpServletRequest req,
			HttpServletResponse res,
			@RequestParam("password")String password,
			@RequestParam("username")String username,
			@RequestParam("code")String code,
			@RequestParam("openId")String openId) throws Exception {
		
		if(!XzStringUtils.checkPhone(username)){
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		}
		/*
		 * 验证短信验证码
		 */
		Integer vtype = SMSCode.RETISTERED.getCode();		//短信验证码类型
		ResponseObject checkCode = onlineUserService.checkCode(username, code,vtype);
		if (!checkCode.isSuccess()) {
			return checkCode;
		}
		
		/**
		 * 注册后默认登录啦
		 */
		OnlineUser ou =  onlineUserService.addPhoneRegistByAppH5(req, password,username,vtype);
		Token t =  userCenterService.loginMobile(username, password, TokenExpires.TenDay);
		ou.setTicket(t.getTicket());
		this.onlogin(req, res, t, ou,t.getTicket());
		
		
		
		return ResponseObject.newSuccessResponseObject(ou);
	}
	
	
	
	/**
	 * Description：浏览器端登录
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	@SuppressWarnings("unused")
	@RequestMapping("login")
	@ResponseBody
	@Transactional
	public ResponseObject login(HttpServletRequest req, HttpServletResponse res,
			@RequestParam("username") String username,
			@RequestParam("password") String password) throws Exception {
		
		if(!XzStringUtils.checkPhone(username)){
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		}
		//存储在redis中了，有效期为10天。
		Token t =  userCenterService.loginMobile(username, password, TokenExpires.TenDay);
		
		if (t != null) {
			OnlineUser o = onlineUserService.findUserByLoginName(username);
			if (o != null) {
				if (o.isDelete() || o.getStatus() == -1){
					return ResponseObject.newErrorResponseObject("用户已禁用");
				}
				/*
				 * 判断是否存在微吼信息,不存在创建，因为app端需要根据创建的信息进行播放
				 */
				if(o.getVhallId()==null || "".equals(o.getVhallId()) ){
					
					String weihouId = WeihouInterfacesListUtil.createUser(
							o.getId(),
							WeihouInterfacesListUtil.MOREN, 
							o.getName(),o.getSmallHeadPhoto());
					
					onlineUserService.updateVhallIdOnlineUser(weihouId,WeihouInterfacesListUtil.MOREN,o.getName(),o.getId());
					o.setVhallId(weihouId);
					o.setVhallPass(WeihouInterfacesListUtil.MOREN);
					o.setVhallName(o.getName());
				}
				
				//把用户中心的数据给他   这里im都要用到
				OeUserVO user = userCenterService.getUserVO(username);
				o.setUserCenterId(user.getId());
				o.setPassword(user.getPassword());
				//把这个票给前端
				o.setTicket(t.getTicket());
				
				this.onlogin(req, res, t, o,t.getTicket());
				
				/**
				 * 清除这个cookie
				 */
				UCCookieUtil.clearThirdPartyCookie(res);
				
				return ResponseObject.newSuccessResponseObject(o);
			} else {  
				/*
				 * 如果用户中心存在信息的话,但是用户表里面没有添加的话，就默认在帮他创建一条
				 */
				boolean ise = Pattern.matches("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$",username);
				if (ise) {
					OeUserVO user = userCenterService.getUserVO(username);
					//这个地方会返回这个用户的微吼id和名字
					OnlineUser newUser = onlineUserService.addUser(username,  user.getName(),getRegiserFormByReq(req),password);
					newUser.setPassword(user.getPassword());
					//把这个票给前端
					newUser.setTicket(t.getTicket());
					this.onlogin(req, res, t, newUser,t.getTicket());
					/**
					 * 清除这个cookie
					 */
					UCCookieUtil.clearThirdPartyCookie(res);
					
					return ResponseObject.newSuccessResponseObject(newUser);
				}
			}
			return ResponseObject.newErrorResponseObject("在线系统不存在此用户");
		} else {
			return ResponseObject.newErrorResponseObject("用户名密码错误");
		}
	}
	
	/**
	 * 忘记密码
	 * @param req
	 * @return
	 */
	@RequestMapping(value="forgotPassword")
	@ResponseBody
	public ResponseObject forgotPassword(HttpServletRequest req,
			@RequestParam("username") String username,
			@RequestParam("password") String password,
		    @RequestParam("code") String code) throws Exception {
		
		/**
		 * 验证手机号
		 */
		if(!XzStringUtils.checkPhone(username)){
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		}
		
		if (!XzStringUtils.checkPassword(password)) {
			return ResponseObject.newErrorResponseObject("密码为6-18位英文大小写字母或者阿拉伯数字");
		}
		
		Integer vtype = SMSCode.FORGOT_PASSWORD.getCode();
		//短信验证码
		ResponseObject checkCode = onlineUserService.checkCode(username, code,vtype);
		if (!checkCode.isSuccess()) {
			return checkCode;
		}
		//更新用户密码
		try {
			userCenterService.resetPassword(username,password);
			return ResponseObject.newSuccessResponseObject("修改密码成功");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseObject.newErrorResponseObject("修改密码失败");
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
			default:
				break;
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
	 * 登录成功处理
	 * @param req
	 * @param res
	 * @param token
	 * @param user
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public void onlogin(HttpServletRequest req, HttpServletResponse res,
                        Token token, OnlineUser user, String ticket) throws SQLException{
		
		LOGGER.info("用户普通登录----》ticket"+ticket);
		/**
		 * 存在两个票，两个票都可以得到用户信息。
		 * 然后根据用户信息得到新的票和这个旧的票进行比较就ok了
		 */
		String appUniqueId = req.getParameter("appUniqueId");
		/*
		 * 这个地方是可以了。目前都支持吧
		 */
		if(StringUtils.isNotBlank(appUniqueId)){   //表示是app登录
			
			//设置登录标识
			//onlineUserService.updateAppleTourisrecord(appUniqueId,1);
			
			cacheService.set(ticket, user,TokenExpires.TenDay.getExpires());
			cacheService.set(user.getId(),ticket,TokenExpires.TenDay.getExpires());
			String model = req.getParameter("model");
			if(StringUtils.isNotBlank(model) && user.getLoginName()!=null){
				cacheService.set(user.getLoginName(),model,TokenExpires.TenDay.getExpires());
			}else if(user.getLoginName()!=null){
				cacheService.set(user.getLoginName(),"其他设备",TokenExpires.TenDay.getExpires());
			}
		}else{
			// 用户登录成功
			// 第一个BUG的解决:第二个用户登录后将之前的session销毁!
			req.getSession().invalidate();
			// 第二个BUG的解决:判断用户是否已经在Map集合中,存在：已经在列表中.销毁其session.
			// 获得到ServletCOntext中存的Map集合.
			Map<OnlineUser, HttpSession> userMap = (Map<OnlineUser, HttpSession>) req.getServletContext().getAttribute("userMap");
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
	/**
	 * 判断注册来源
	 */
    public String getRegiserFormByReq(HttpServletRequest req){
    	String appUniqueId = req.getParameter("appUniqueId");
    	if(appUniqueId == null){//h5注册
    		return RegisterForm.H5.getText();
    	}
    	UserAgent userAgent = UserAgent.parseUserAgentString(req.getHeader("User-Agent"));  
        OperatingSystem os = userAgent.getOperatingSystem();
        if(!os.toString().toLowerCase().contains("android")){
        	return RegisterForm.ANDROID.getText();
        }else{
        	return RegisterForm.IOS.getText();
        }
    }
    
    
	/**
	 * 
	 * Description：发送短信验证码
	 * @param req
	 * @param username
	 * @return
	 * @return ResponseObject
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 * @throws SQLException 
	 *
	 */
	@RequestMapping(value="verifyPhone")
	@ResponseBody
	public ResponseObject sendCode(@RequestParam("username")String username) throws SQLException{
		
		//类型，1注册，  2重置密码   3 完善信息
			
		 if(!XzStringUtils.checkPhone(username)){
			return ResponseObject.newErrorResponseObject("请输入正确的手机号");
		 }
		 onlineUserService.verifyPhone(username);
		 return ResponseObject.newSuccessResponseObject("验证成功");
	}
    
}
