package com.xczh.consumer.market.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.utils.HttpUtil;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.user.center.bean.ThridFalg;
import com.xczhihui.user.center.web.utils.UCCookieUtil;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;

/**
 * 
 * 此过滤器主要为了进行请求接口前的一些拦截操作，判断用户的token是否有效，判断是否同一个用户是否两次登录
 * ClassName: TokenFilter.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年2月22日<br>
 */
public class TokenFilter implements Filter {
	
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(TokenFilter.class);
	
	private CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();

	/*
	 * 老业务方法
	 */
	private static String specific_business_one = 
			"/bxg/binner/list,/bxg/bunch/offLineClass,"
			+ "/bxg/live/list,/bxg/bunch/list,"
			+ "/bxg/menu/list,/bxg/bunch/offLineClassList,"
			+ "/bxg/live/listKeywordQuery";
	
	//原来老版本的
	private String[] appExcludedPageArray;
		
	/***************************************************
	 * 
	 * 
	 * 	上面的是以前的拦截和不应该拦截的方法
	 * 
	 * **************************************************
	 */
	//登录前不拦截
	private static String new_controller_login_before ="/xczh/user";
	
	//分享的接口不拦截
	private static String new_controller_share_before ="/xczh/share";
	
	//各种第三方前的登录不需要拦截
	private static String new_controller_login_three_parties ="/xczh/qq,/xczh/wxlogin,/xczh/weibo,/xczh/third,/xczh/wxpublic";
	
	//微信，支付宝支付回调、评论列表不需要拦截 -- 这个是直接等于的  
	private static String new_controller_pay_the_callback ="/xczh/alipay/alipayNotifyUrl,/bxg/wxpay/wxNotify,"
			+ "/xczh/alipay/pay,/xczh/alipay/rechargePay";
	
	private static String new_controller_pay_the_callback_one =
			"/xczh/criticize/getCriticizeList,/xczh/ccvideo/palyCode,/xczh/wechatJssdk/certificationSign,"
			+ "/xczh/medical/applyStatus,/xczh/manager/home,/xczh/common/getProblems,"
			+ "/xczh/common/rechargeList,/xczh/common/verifyLoginStatus,"
			+ "/xczh/common/getProblemAnswer,/xczh/common/checkUpdate,"
			+ "/xczh/common/addOpinion,/xczh/gift/rankingList,/xczh/common/richTextDetails,"
			+ "/xczh/gift/list,/xczh/message,/xczh/common/checkToken,/xczh/pay/pay_notify,/xczh/alipay/alipayNotifyUrl";
	
	/*
	 * 下面是一下具体业务方法不需要拦截
	 *  推荐、分类、线下班、直播、听课、
	 */
	private static String new_controller_specific_business_one 
	  = "/xczh/recommend,/xczh/classify,/xczh/bunch,/xczh/live,/xczh/bunch,/xczh/message";//message
	/*
	 *  主播页面 、课程详情、
	 */
	private static String new_controller_specific_business_two
	  = "/xczh/host,/xczh/course";
	
	
	/**
	 * 这个可以写特殊强调的要拦截的啊
	 *   强调有问题需要拦截的
	 */
	public static String new_to_intercept = "/xczh/course/liveDetails";
	
	//现在新版本的  接口不过滤
	private String[] newInterfaceFilter;
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String excludedPageStr = specific_business_one;
		if (StringUtils.isNotEmpty(excludedPageStr) ) {   
		    appExcludedPageArray  = excludedPageStr.split(",");
		}     
		//新的
		String newExcludedControllerStr = new_controller_login_before+","+new_controller_share_before+","+
				new_controller_login_three_parties+","+new_controller_specific_business_one+","+new_controller_specific_business_two;
		
		if (StringUtils.isNotEmpty(newExcludedControllerStr)) {   
			newInterfaceFilter  = newExcludedControllerStr.split(",");
		}  
	}
	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		//设置将字符以"UTF-8"编码输出到客户端浏览器
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		boolean isExcludedPage = false;   
		
		String currentURL = request.getRequestURI(); // 取得根目录所对应的绝对路径:
		/**
		 * 测试和生产用这个
		 */
		if((useLoopEqual(appExcludedPageArray,currentURL) || 
				useLoopContains(newInterfaceFilter, currentURL))
				&& new_to_intercept.indexOf(currentURL)==-1){
			     isExcludedPage = true;     
		}
		LOGGER.info("欢迎欢迎，"+currentURL);
		LOGGER.info("isExcludedPage，"+isExcludedPage);
		/**
		 * 拦截ajax请求
		 */
		boolean isAjax = false;
		if (request.getHeader("x-requested-with") != null 
                 && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {   
			isAjax = true;
		}
		if(isExcludedPage){ // 直接放行
			chain.doFilter(request, response);
		}else{
		    /*
			 * 如果是app的话也需要拦截，因为拦截需要验证下时候是否在其他客户端登录
			 */
			String appUniqueId = (String) request.getAttribute("appUniqueId");
			String strToken = (String) request.getAttribute("token");
			//获取请求的content-type
			String contentType = request.getContentType();
			//文件上传请求 *特殊请求
			if(StringUtils.isNotBlank(contentType) && contentType.contains("multipart/form-data")){
	              MultipartHttpServletRequest multiReq = multipartResolver.resolveMultipart(request);
	              appUniqueId= multiReq.getParameter("appUniqueId");//获取参数中的token
	              strToken= multiReq.getParameter("token");//获取参数中的token
	              request = multiReq;//将转化后的reuqest赋值到过滤链中的参数 *重要
	        }else{
	        	  appUniqueId = request.getParameter("appUniqueId");
	        	  strToken = request.getParameter("token");
	        }
			LOGGER.info("appUniqueId，"+appUniqueId+",token:"+strToken);
			/*
			 * 说明这个请求的来自浏览器，判断session是否失效了
			 */
			if(null == appUniqueId){ //来自浏览器请求
				//浏览器的状态
				int statusFalg = 200;
				//需要重定向的页面
				String redirectUrl =  request.getContextPath()+"/xcview/html/enter.html";
				//转发的url
				String forwardUrl = "/xczh/common/verifyLoginStatus";
		    	HttpSession session = request.getSession(false);
		    	if(session!=null && null != session.getAttribute("_user_")) {
		    		/*
		    		 * 是不是需要传递一个token过来啊。然后判断这个token是否
		    		 */
		    		chain.doFilter(request, response);
		    		return;
		    	}else if(session !=null){
		    		Map<String,String> mapClientInfo = (Map<String, String>) session.getAttribute("topOff");//如果存在顶掉的信息，
		    		if(mapClientInfo!=null){ 
		    			statusFalg = 1003;		    			
		    			redirectUrl = request.getContextPath() + "/xcview/html/enter.html?errorMessage=1";
		    		}else{
		    			statusFalg = 1002;
		    			redirectUrl = request.getContextPath() + "/xcview/html/enter.html";
		    		}
		    	}else{
		    		statusFalg = 1002;
		    		redirectUrl = request.getContextPath() + "/xcview/html/enter.html";
		    	}  
		    	ThridFalg tf = UCCookieUtil.readThirdPartyCookie(request);
		    	LOGGER.info("currentURL:"+currentURL+"================================tf.toString():"+tf);
		    	String tfParams = "";
		    	//如果是没有登录的状态 并且cookie不登录空的话，就去完善信息页面
		    	if(statusFalg ==1002 && tf!=null && tf.getOpenId()!=null && tf.getUnionId()!=null){
		    		statusFalg = 1005;
		    		redirectUrl = request.getContextPath() + "/xcview/html/evpi.html?openId="+tf.getOpenId()+"&unionId="+tf.getUnionId()+"&jump_type=1";
		    		tfParams ="&openId="+tf.getOpenId()+"&unionId="+tf.getUnionId();
		    	}
		    	if(isAjax){
		    		LOGGER.info("forwardUrl"+forwardUrl+"?statusFalg="+statusFalg+tfParams);
    				req.getRequestDispatcher(forwardUrl+"?statusFalg="+statusFalg+tfParams).forward(request,response);
    			}else{
    				response.sendRedirect(redirectUrl);
    			}
		    }else{ 
		    	response.setContentType("application/json; charset=utf-8");  
		    	
		    	if(currentURL.indexOf("/bxg")!=-1) { //以前接口
		    		
		    		ResponseObject obj = new ResponseObject();
		    		obj.setSuccess(false);
		    		obj.setErrorMessage("请使用最新版本app!");
		    		
		    		PrintWriter out = response.getWriter();//获取PrintWriter输出流
		    		out.println(obj);
		    		out.flush();
		            out.close();
		    	}else {
		    		Map<String,Object> mapApp = validateLoginFormApp(strToken);
			    	int code = mapApp.get("code")==null?-1:Integer.parseInt(String.valueOf(mapApp.get("code")));
			    	if(code == 1000){
			    	    chain.doFilter(request, response);
			    	}else{
			    		PrintWriter out = response.getWriter();//获取PrintWriter输出流
			    		out.println(mapApp);
			    		out.flush();
			            out.close();
			    	}
		    	}
		    }
		}
	}
	/**
	 * 
	 * send request validate login
	 * 2017-06-19
	 */
	private static Map<String,Object> validateLoginFormApp(String token){
		
		JSONObject obj = new JSONObject();
		try{
			String httpUrl = WxPayConst.returnOpenidUri+"/xczh/common/checkToken?token="+token;
			String str = HttpUtil.sendPostRequest(httpUrl,null);
			if(null!=str) {
                obj = JSONObject.parseObject(str);
            }
		}catch(Exception e){
			e.printStackTrace();
		}
		return (Map<String,Object>) obj;
	}
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
		
	}
	/**
	 * 
	 * Description：使用循环会更加的高效  --》这个用来判断数组中的元素是否相等于这个字符串
	 * @param arr
	 * @param targetValue
	 * @return
	 * @return boolean
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 */
	public static boolean useLoopContains(String[] arr,String targetValue){
		if(new_controller_pay_the_callback.indexOf(targetValue)!=-1
				|| new_controller_pay_the_callback_one.indexOf(targetValue)!=-1){
			return true;
		}
		int  laseIndex = targetValue.lastIndexOf("/");
		String tmptargetValue = targetValue.substring(0, laseIndex);
	    for(String s:arr){
	       if(s.equals(tmptargetValue))
	         return true;
	    }  
	    return false;
	}
	/**
	 * 
	 * Description：使用循环会更加的高效  --》这个用来判断数组中的元素是否包含这个字符串
	 * @param arr
	 * @param targetValue
	 * @return
	 * @return boolean
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	public static boolean useLoopEqual(String[] arr,String targetValue){
	    for(String s:arr){
	       if(s.equals(targetValue))
	         return true;
	    }  
	    return false;
	}

}
