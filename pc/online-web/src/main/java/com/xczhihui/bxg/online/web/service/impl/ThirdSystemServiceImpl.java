package com.xczhihui.bxg.online.web.service.impl;/**
 * Created by admin on 2016/9/19.
 */

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.util.HttpUtil;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.common.support.config.OnlineConfig;
import com.xczhihui.bxg.online.web.dao.ThirdSystemDao;
import com.xczhihui.bxg.online.web.service.ThirdSystemService;
import com.xczhihui.bxg.online.web.service.VerificationCodeService;
import com.xczhihui.bxg.online.web.utils.PayCommonUtil;
import com.xczhihui.bxg.online.web.utils.XMLUtil;
import com.xczhihui.user.center.utils.CodeUtil;

/**
 * 第三方系统接口提供
 * @author Haicheng Jiang
 *
 */
@Service
public class ThirdSystemServiceImpl extends OnlineBaseServiceImpl implements ThirdSystemService {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private VerificationCodeService vs;
	
	private static Map<String, String> KEY = new HashMap<String, String>();
	static {
		KEY.put("univ", "Sj2Yd6Djkgqp3ouiDy3ks6fjDdj5ljhsf62mM1MsI");
		KEY.put("dual", "OksGdk3894qp934Ldf1lsS4jfljhsf6dppsSg223l");
		KEY.put("bxg",  "gasdkhjwl4krSDfs4589SDf9s863GSAgdfsgs8955");
	}
	
	@Autowired
	private ThirdSystemDao dao;
	
	@Override
	public String sendMobileMessage(String systemName,String mobile, String content,long timestamp, String sign) {
		String key = KEY.get(systemName);
		String md5Upper = com.xczhihui.bxg.common.util.CodeUtil.md5Upper(systemName+mobile+content+timestamp+key);
		if (!md5Upper.equals(sign)) {
			logger.warn("sendMobileMessage api 签名错误！");
			return "sendMobileMessage api 签名错误！";
		}
		if ((System.currentTimeMillis() - timestamp) / 1000 > 3) {
			logger.warn("sendMobileMessage api 超过正常时间，可能被攻击！");
			return "sendMobileMessage api 超过正常时间，可能被攻击！";
		}
		
		Map<String, String> param = new HashMap<String, String>();
		param.put("cpid","14694");
		param.put("password",CodeUtil.MD5Encode("bxgjianghc_"+(System.currentTimeMillis()/1000L)+"_topsky"));
		param.put("timestamp",String.valueOf(System.currentTimeMillis()/1000L));
		param.put("channelid","17408");
		param.put("msg",content);
		param.put("tele",mobile);
		//发短信
		String msg = HttpUtil.sendPostRequest("http://admin.sms9.net/houtai/sms.php", param);
		//判断发送结果
		if (msg == null || msg.contains("error")) {
			return ("发送短信失败！"+msg);
		}
		return "ok";
	}

	@Override
	public OnlineUser getUserInfo(String systemName, String loginName, long timestamp, String sign) {
		String key = KEY.get(systemName);
		String md5Upper = com.xczhihui.bxg.common.util.CodeUtil.md5Upper(systemName+loginName+timestamp+key);
		if (!md5Upper.equals(sign)) {
			logger.warn("sendMobileMessage api 签名错误！");
			throw new RuntimeException("sendMobileMessage api 签名错误！");
		}
		if ((System.currentTimeMillis() - timestamp) / 1000 > 3) {
			logger.warn("sendMobileMessage api 超过正常时间，可能被攻击！");
			throw new RuntimeException("sendMobileMessage api 超过正常时间，可能被攻击！");
		}
		return dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", loginName);
	}

	@Override
	public void addSendVerificationCode(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Map<String, String> m = this.check(req);
		vs.addMessage(m.get("mobile"), m.get("vtype"));
	}
	
	private Map<String, String> check(HttpServletRequest req) throws Exception{
		InputStream inputStream = req.getInputStream();
		StringBuffer sb = new StringBuffer();
		String str = null;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((str = in.readLine()) != null) {
			sb.append(str);
		}
		in.close();
		inputStream.close();
		// 解析xml成map
		Map<String, String> m = XMLUtil.doXMLParse(sb.toString());
		if (m != null && !m.isEmpty()) {
			// 过滤空 设置 TreeMap
			SortedMap<Object, Object> packageParams = new TreeMap<>();
			for (Map.Entry<String, String> e : m.entrySet()) {
				if (StringUtils.hasText(e.getValue()) && StringUtils.hasText(e.getKey())) {
					packageParams.put(e.getKey(), e.getValue());
				}
			}
			if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, OnlineConfig.WECHAT_API_KEY)) {
				return m;
			}
		}
		throw new RuntimeException("签名错误");
	}
}
