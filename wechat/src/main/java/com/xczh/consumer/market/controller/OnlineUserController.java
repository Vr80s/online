package com.xczh.consumer.market.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.service.CacheService;
import com.xczh.consumer.market.service.OnlineUserService;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.user.center.bean.Token;
import com.xczhihui.user.center.web.utils.UCCookieUtil;

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
	private CacheService cacheService;
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(OnlineUserController.class);
	
	@RequestMapping("login")
	@ResponseBody
	public ResponseObject login(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		
		LOGGER.info("老版本方法----》》》》login");
   	    return ResponseObject.newErrorResponseObject("请使用最新版本");
		
	}
	/**
	 * 登录成功处理
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
	 * 退出登录
	 * @param req
	 * @param res
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("appleLogout")
	@ResponseBody
	public ResponseObject appleLogout(HttpServletRequest req, HttpServletResponse res, Map<String, String> params) throws Exception {
		UCCookieUtil.clearTokenCookie(res);
		req.getSession().setAttribute("_user_", null);
		String token = req.getParameter("token");
		if(token!=null){
			cacheService.delete(token);
		}
		String appUniqueId = req.getParameter("appUniqueId");
		return ResponseObject.newSuccessResponseObject("退出成功");
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
		
		LOGGER.info("老版本方法----》》》》sendCode");
   	    return ResponseObject.newErrorResponseObject("请使用最新版本");
		
//		//类型，1注册，2重置密码
//		String vtype = req.getParameter("vtype") == null ? "1" : req.getParameter("vtype");
//		//手机号
//		String username = req.getParameter("username");
//		try {
//			String str = onlineUserService.addMessage(username, Integer.parseInt(vtype));
//			if("发送成功".equals(str)){
//				return ResponseObject.newSuccessResponseObject(str);
//			}else{
//				return ResponseObject.newErrorResponseObject(str);
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			LOGGER.info("获取错误信息啦"+e.getMessage());
//			return ResponseObject.newErrorResponseObject("发送失败");
//		}
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

		LOGGER.info("老版本方法----》》》》phoneRegist");
   	    return ResponseObject.newErrorResponseObject("请使用最新版本");
//		String password = req.getParameter("password");
//		String mobile = req.getParameter("mobile");
//		String openId = req.getParameter("openId");
//		String code = req.getParameter("code");
//		//String vtype = req.getParameter()("vtype");
//		String vtype = "1";
//		
//		if(null == password || null == mobile || null == openId || null == code){
//			return ResponseObject.newErrorResponseObject("参数异常");
//		}
//		//短信验证码
//		ResponseObject checkCode = onlineUserService.checkCode(mobile, code,Integer.parseInt(vtype));
//		if (!checkCode.isSuccess()) {
//			return checkCode;
//		}
//		return onlineUserService.addPhoneRegist(req, password,mobile,openId);
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

		LOGGER.info("老版本方法----》》》》userCenterFormSub");
   	    return ResponseObject.newErrorResponseObject("请使用最新版本");
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
		
		
		LOGGER.info("老版本方法----》》》》userCenterFormSub1");
   	    return ResponseObject.newErrorResponseObject("请使用最新版本");
   	    
//        try{
//          OnlineUser user = new OnlineUser();
//          String token = req.getParameter("token");
//          if(token !=null ){
//        	  user = cacheService.get(token);
//          }else{
//        	  user = (OnlineUser) req.getSession().getAttribute("_user_");
//          }  
//          if(null == user){
//             return ResponseObject.newErrorResponseObject("获取用户信息有误");
//          }
//          Map<String,String> map = new HashMap<String,String>();
//          String sex= req.getParameter("sex");  
//          String nickname= req.getParameter("nickname");  
//          String email= req.getParameter("email");  
//          String info= req.getParameter("info"); 
//          String provinceCityName= req.getParameter("provinceCityName"); 
//          String provinceCityId= req.getParameter("provinceCityId"); 
//          
//          String occupation= req.getParameter("occupation"); 
//          String occupationOther= req.getParameter("occupationOther"); 
////  		/**
//// 		 * 职业
//// 		 */
//// 		private Integer occupation;
//// 		/**
//// 		 * 职业,其他
//// 		 */
//// 		private String occupationOther;
//          
//          map.put("sex", sex);
//          map.put("nickname", nickname);
//          map.put("email", email);
//          map.put("info", info);
//          map.put("occupation", occupation);
//          map.put("occupationOther", occupationOther);
//          
//          if(StringUtils.isNotBlank(provinceCityName)){
//        	  String [] str =  provinceCityName.split(" ");
//        	  String [] ids =  provinceCityId.split(" ");
//        	  //根据名字得到id，好惨        	  
//        	  if(str.length ==3 && ids.length == 3){
//        		  map.put("provinceName", str[0]);
//        		  map.put("cityName", str[1]);
//           		  map.put("countyName", str[2]);
//        		  
//        		  map.put("province", ids[0]);
//        		  map.put("city", ids[1]);
//        		  map.put("district", ids[2]);
//        	  }
//          }
//          userCenterAPI.update(user.getLoginName(),nickname,org.springframework.util.StringUtils.hasText(sex) ? Integer.parseInt(sex) : 3,email, null, 10, 10);
//          onlineUserService.updateUserCenterData(user,map);
//          String weiHouResp = WeihouInterfacesListUtil.
//        		  updateUser(user.getId(),null,nickname,null);
//          /**
//           * 更新session的值
//           */
//          OnlineUser newUser =   onlineUserService.findUserByLoginName(user.getLoginName());
//          req.getSession().setAttribute("_user_",newUser);
//          if(weiHouResp == null){
//        	  LOGGER.info("同步微吼昵称失败");
//          }
//          return ResponseObject.newSuccessResponseObject(map);
//        }catch (Exception e) {
//            e.printStackTrace();
//            return ResponseObject.newErrorResponseObject("后台处理流程异常");
//        }
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
		LOGGER.info("老版本方法----》》》》userCenterFormSub2");
   	    return ResponseObject.newErrorResponseObject("请使用最新版本");
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
		LOGGER.info("老版本方法----》》》》wxBindAndPhoneNumberIsReg");
   	    return ResponseObject.newErrorResponseObject("请使用最新版本");
		
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
		
		LOGGER.info("老版本方法----》》》》wxBindPhoneNumber");
   	    return ResponseObject.newErrorResponseObject("请使用最新版本");
		
	}
}
