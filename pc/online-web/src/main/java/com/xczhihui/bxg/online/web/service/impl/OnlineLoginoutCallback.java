package com.xczhihui.bxg.online.web.service.impl;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.support.domain.BxgUser;
import com.xczhihui.bxg.common.web.auth.service.LoginoutCallback;
import com.xczhihui.bxg.common.web.util.UserLoginUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.user.center.service.UserCenterAPI;
import com.xczhihui.user.center.web.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
@Service("onlineLoginoutCallbackService")
public class OnlineLoginoutCallback implements LoginoutCallback {
	
	//private static Map<String, SingletonUserLogin> logins = new HashMap<String, SingletonUserLogin>();

	@Resource(name = "simpleHibernateDao")
	private SimpleHibernateDao dao;
	@Autowired
	private UserCenterAPI api;
	/**
	 * 记录最后登录时间、IP、访问次数
	 */
	@Override
	public void onLogin(HttpServletRequest request, HttpServletResponse response) {
		OnlineUser ou = (OnlineUser)UserLoginUtil.getLoginUser(request);
		OnlineUser u = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName",ou.getLoginName());
		Date last = u.getLastLoginDate();
		//如果是第一次登录，写一个cookie给前端
		if (last == null) {
			CookieUtil.setCookie(response, "first_login", "1", "ixincheng.com", "/", 5);
		}
		u.setLastLoginDate(new Date());
		u.setVisitSum(u.getVisitSum() + 1);
		u.setLastLoginIp(this.getIpAddress(request));
		dao.update(u);
	}
	/**
	 * 记录停留时间，当前时间 - 最后登录时间
	 */
	@Override
	public void onLogout(HttpServletRequest request, HttpServletResponse response) {
		BxgUser user = UserLoginUtil.getLoginUser(request);
		//清空分享码cookie
		CookieUtil.setCookie(response, "_usercode_", "", "ixincheng.com", "/", 0);
		if (user != null) {
			//logins.remove(user.getId());
			OnlineUser u = dao.findOneEntitiyByProperty(OnlineUser.class, "loginName",user.getLoginName());
			if (u != null) {
				Date login = u.getLastLoginDate();
				if (login != null){
					u.setStayTime((int)((System.currentTimeMillis() - login.getTime())/1000));
					dao.update(u);
				}
			}
		}
	}

	/**
	 * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
	 * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，
	 * 而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
	 * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
	 * 192.168.1.100 * * 用户真实IP为： 192.168.1.110
	 * @param request
	 * @return
	 */
	public String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
