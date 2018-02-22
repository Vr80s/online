package com.xczh.consumer.market.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
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

import com.alibaba.fastjson.JSONObject;
import com.xczh.consumer.market.utils.HttpUtil;
import com.xczh.consumer.market.wxpay.consts.WxPayConst;

public class TokenFilter implements Filter {
	
    /**   
     * 需要排除的页面     登录前操作接口是不拦截token的
     */    
	/*
	 * 登录前不拦截的控制器：
	 */
	private static String str ="/bxg/user/sendCode,/bxg/bs/phoneRegist,/bxg/page/reg,/bxg/bs/login,/bxg/page/login/1"
			+ ",/bxg/user/logout,/bxg/user/phoneRegist,/bxg/bs/forgotPassword,/bxg/page/login/1,/bxg/bs/checkToken,/bxg/user/forgotPassword,"
			+ "/bxg/user/login,/bxg/bs/isReg,/bxg/wxjs/h5JSSDK,/bxg/page/verifyLoginStatus";
	/*
	 * 登录前不拦截的页面，因为可能来自分享。
	 */
	private static String str1 = "/bxg/common/shareLink,/bxg/common/pcShareLink,/bxg/wxpay/h5GetOpenidForPersonal";
	/*
	 * 或者说是来自请求微信的信息
	 * 因为存在第三方登录，第三方登录不应该拦截的地址
	 */
	private static String str2 = "/bxg/bs/addGetUserInfoByCode,/bxg/wxpay/h5GetOpenid,"
			+ "/bxg/wxjs/setWxMenu,/bxg/wxpay/h5BsGetCodeUrlAndBase,/bxg/wxjs/h5BsGetCodeUrl,"
			+ "/bxg/wxjs/h5BsGetCodeUrlAndBase,/bxg/user/wxBindPhoneNumber,"
			+ "/bxg/user/wxBindAndPhoneNumberIsReg,/bxg/wxjs/h5BsGetCodeUrlReqParams,"
			+ "/bxg/user/h5GetOpenidForPersonal,/bxg/bs/appThirdPartyLogin,/bxg/wxpay/h5GetCodeAndUserMobile";
	/*
	 * 支付的回调不应该拦截的地方 
	 */
	private static String str3 = "/bxg/alipay/alipayNotifyUrl,/bxg/wxpay/wxNotify,/bxg/alipay/pay,/bxg/alipay/rewardPay,/bxg/alipay/payXianXia,"
			+ "/bxg/alipay/rechargePay,/bxg/common/h5ShareAfter,/bxg/wxjs/h5ShareGetWxOpenId,/bxg/wxpay/h5ShareGetWxUserInfo";

	
	private static String str5 = "/bxg/binner/list,/bxg/bunch/offLineClass,/bxg/live/list,/bxg/bunch/list,/bxg/page/index/null/null,"
			+ "/bxg/menu/list,/bxg/bunch/offLineClassList,/bxg/live/listKeywordQuery,/bxg/bs/appLogin,/bxg/focus/myHome,"
			+ "/bxg/user/appleLogout";
	
	private static String str6 = "/bxg/bunch/queryAllCourse,/bxg/bunch/schoolClass,/bxg/bunch/recommendBunch,/bxg/bunch/recommendTop,"
			+ "/bxg/bunch/offLine,/bxg/live/onlineLive,/bxg/bunch/listenCourse,/bxg/criticize/saveCriticize,/bxg/criticize/updatePraise,"
			+ "/bxg/criticize/saveReply,/bxg/criticize/getCriticizeList,/bxg/host/hostPageCourse,/bxg/host/hostPageInfo,/bxg/bunch/hotSearch";
	

	
	public static void main(String[] args) {
		System.out.println((str + "," +str1+","+str2+","+str3).split(",").length);
		//System.out.println((str6+","+str5+","+","+str7+","+str8+","+str9+","+str10+","+str11).split(",").length);
	}
	
	private String[] excludedPageArray; 
	
	private String[] appExcludedPageArray;
	
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
		String excludedPageStr = str+","+str1+","+str2+","+str3+","+str6;
	    
	    String appExcludedPageStr = str5+","+str6;
		
		if (StringUtils.isNotEmpty(excludedPageStr) ) {   
		    excludedPageArray = excludedPageStr.split(",");
		    appExcludedPageArray  = appExcludedPageStr.split(",");
		}     
	}
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		request.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");//设置将字符以"UTF-8"编码输出到客户端浏览器

		boolean isExcludedPage = false;   
		String currentURL = request.getRequestURI(); // 取得根目录所对应的绝对路径:
		
		if(Arrays.asList(excludedPageArray).contains(currentURL) || currentURL.indexOf("xczh")!=-1){
			     isExcludedPage = true;     
		}
		boolean isAjax = false;
		if (request.getHeader("x-requested-with") != null 
                 && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {   
			isAjax = true;
		}
		if(isExcludedPage){ // 直接放行
			chain.doFilter(request, response);
		}else{
			int statusFalg = 200;
		    /*
			 * 如果是app的话也需要拦截，因为拦截需要验证下时候是否在其他客户端登录
			 */
			String appUniqueId = (String) request.getAttribute("appUniqueId");
			appUniqueId = request.getParameter("appUniqueId");
			
			
			/*
			 * 说明这个请求的来自浏览器，判断session是否失效了   --现在先待修改，后面需要判断session
			 */
			if(null == appUniqueId){ 
		    	HttpSession session = request.getSession(false);
		    	if(session!=null && null != session.getAttribute("_user_")) {
		    		/*
		    		 * 是不是需要传递一个token过来啊。然后判断这个token是否
		    		 */
		    		chain.doFilter(request, response);
		    		
		    	}else if(session !=null){
					/**
					 * 有两种情况一种是：失效了，一种是被顶掉了。
					 * 这里不能直接给用户返回到登录页面，需要一个弹框提示：
					 *   提示登录ip,登录机型等信息，登录的时间等信息。
					 */
		    		Map<String,String> mapClientInfo = (Map<String, String>) session.getAttribute("topOff");//如果存在顶掉的信息，
		    		if(mapClientInfo!=null){ 
		    			statusFalg = 1003;
		    			if(isAjax){
		    				req.getRequestDispatcher("/bxg/page/verifyLoginStatus?statusFalg="+statusFalg).forward(request,response);
		    			}else{
		    				response.sendRedirect(request.getContextPath() + "/bxg/page/login/1?errorMessage=1");
		    			}
		    		}else{
		    			statusFalg = 1002;
		    			if(isAjax){
		    				req.getRequestDispatcher("/bxg/page/verifyLoginStatus?statusFalg="+statusFalg).forward(request,response);
		    			}else{
		    				response.sendRedirect(request.getContextPath() + "/bxg/page/login/1");
		    			}
		    		}
		    	}else{
		    		statusFalg = 1002;
	    			if(isAjax){
	    				req.getRequestDispatcher("/bxg/page/verifyLoginStatus?statusFalg="+statusFalg).forward(request,response);
	    			}else{
	    				response.sendRedirect(request.getContextPath() + "/bxg/page/login/1");
	    			}
		    	}  
		    }else{ 
		    	if(!Arrays.asList(appExcludedPageArray).contains(currentURL)){
		    		String strToken = (String) request.getAttribute("token");
		 		    strToken = request.getParameter("token");
		    		Map<String,Object> mapApp = validateLoginFormApp(strToken);
			    	int code = mapApp.get("code")==null?-1:Integer.parseInt(String.valueOf(mapApp.get("code")));
			    	if(code == 1000){
			    		chain.doFilter(request, response);
			    	}else{
			    		 PrintWriter out = response.getWriter();//获取PrintWriter输出流
			    		 out.println(mapApp);
			    	}
				}else{
					chain.doFilter(request, response);
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
			String httpUrl = WxPayConst.returnOpenidUri+"/bxg/bs/checkToken?token="+token;
			//String httpUrl = "?token="+token;
			String str = HttpUtil.sendPostRequest(httpUrl,null);
			//System.out.println("is validate token effective. ehr back data:"+str);		
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

}
