package com.xczhihui.bxg.online.web.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Resource;

import com.xczhihui.common.util.enums.VCodeType;
import com.xczhihui.user.center.service.UserCenterService;
import com.xczhihui.user.center.vo.OeUserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.support.domain.SystemVariate;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.bxg.online.common.domain.VerificationCode;
import com.xczhihui.bxg.online.web.service.VerificationCodeService;
import com.xczhihui.bxg.online.web.utils.MailUtil;

/**
 * 动态码相关
 * @author Haicheng Jiang
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserCenterService userCenterService;
	@Autowired
	private EmailService emailService;
	private SimpleHibernateDao dao;
	//数据字典
	private Map<String, String> attrs = new HashMap<String, String>();
	
	@Value("${web.url}")
	private String weburl;

	@Override
	public String addMessage(String username, String vtype) {
		
		initSystemVariate();
		if (VCodeType.getType(Integer.valueOf(vtype)) == null) {
			throw new RuntimeException ("动态码类型错误！1注册2.重置密码3.提现");
		}

		OeUserVO userVO = userCenterService.getUserVO(username);

		/**
		 * 新注册，根据用户中心判断用户是否存在
		 */
		if (VCodeType.REGISTER.getCode() == Integer.valueOf(vtype) && userVO != null) {
			throw new RuntimeException (String.format("该%s已注册，请直接登录！",username.contains("@") ? "邮箱" : "手机号"));
		}

		OnlineUser o = dao.findByHQLOne("from OnlineUser where loginName=?", username);
		/**
		 * 重置密码，根据本地库判断用户是否存在
		 */
		if (VCodeType.RESET.getCode() == Integer.valueOf(vtype) && (o == null || userVO == null)) {
			throw new RuntimeException ("用户不存在！");
		}
		/**
		 * 重置密码，根据用户中心判断用户是否被禁用
		 */
		if (VCodeType.RESET.getCode() == Integer.valueOf(vtype) && userVO.getStatus() == -1) {
			throw new RuntimeException ("用户已禁用！");
		}

		// 产生随机4位动态码
		String vcode = String.valueOf(ThreadLocalRandom.current().nextInt(1000,10000));
		List<VerificationCode> codes = dao.findByHQL("from VerificationCode where phone=? and vtype=? ", username,vtype);
		if (codes != null && codes.size() > 0) {// 如果存在历史动态码，按业务逻辑判断
			
			VerificationCode code = codes.get(0);
			
			if (System.currentTimeMillis() - code.getCreateTime().getTime() < 1000 * Integer.valueOf(attrs.get("message_provider_interval_time"))) {
				//发送，判断邮箱还是手机
				if (username.contains("@")){
					throw new RuntimeException ("同一邮箱两次发送间隔至少" + Integer.valueOf(attrs.get("message_provider_interval_time")) + "秒！");
				}else{
					throw new RuntimeException ("同一手机号两次发送间隔至少" + Integer.valueOf(attrs.get("message_provider_interval_time")) + "秒！");
				}
			} else if (System.currentTimeMillis() - code.getCreateTime().getTime() > 1000 * 60
					* Integer.valueOf(attrs.get("message_provider_valid_time"))) {
				code.setVcode(vcode);
			} else {
				vcode = code.getVcode();
			}
			code.setCreateTime(new Date());
			dao.update(code);
		} else {// 不存在历史动态码，直接发送
			// 保持动态码信息
			VerificationCode c = new VerificationCode();
			c.setCreateTime(new Date());
			c.setDelete(false);
			c.setPhone(username);
			c.setVcode(vcode);
			c.setVtype(vtype);
			dao.save(c);
		}

		//发送，判断邮箱还是手机
		if (username.contains("@")) {
			this.sendEmail(username, vcode, vtype);
		} else {
			this.sendPhone(username, vcode, vtype);
		}
		
		return "发送成功！";
	}
	
	private void sendPhone(String phone, String vcode,String vtype){

		if (VCodeType.getType(Integer.valueOf(vtype)) == null) {
			throw new RuntimeException ("动态码类型错误！1注册2.重置密码3.提现");
		}
		
		//判断发送结果
		SendSmsResponse response = null;
		try {
			response = SmsUtil.sendSms(phone, vcode);
		} catch (ClientException e) {
			e.printStackTrace();
		}
		if (response==null || !"OK".equals(response.getCode())) {
			throw new RuntimeException ("发送动态码失败！");
		}
		
	}
	
	private void sendEmail(String email,String vcode, String vtype){
		
		String content = null;
		
		//"邮箱"+"!@!"+"UUID"
		vcode = email+"!@!"+vcode;
		
		if ("2".equals(vtype)) {
			//找回密码邮件内容
			content = MailUtil.getEmailContent(weburl+"/online/user/toResetEmail?vcode="+vcode);//yuruixin_20170825
		} else if ("1".equals(vtype)) {
			//构建注册邮件验证信息
			content = MailUtil.getRegisterEmailContent(weburl+"/online/user/registEmailValidate?vcode="+vcode);
		}
		
		//发送邮件
		try {
			emailService.sendEmail(email, attrs.get("message_provider_email_subject"), content, "text/html;charset=UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("验证邮件发送失败，请检查邮箱是否存在！");
		}
	}

	public void initSystemVariate(){
		//查数据字典
		List<SystemVariate> lst = dao.findByHQL("select t1 from SystemVariate t1,SystemVariate t2 "
				+ "where t1.parentId=t2.id and t2.name=?","message_provider");
		for (SystemVariate systemVariate : lst) {
			attrs.put(systemVariate.getName(), systemVariate.getValue());
		}
	}
	
	@Override
	public String checkCode(String phone, String code) {
		initSystemVariate();

		//在用户重新获取登录对象
		OeUserVO userVO = userCenterService.getUserVO(phone);
		if(userVO == null){
			throw new RuntimeException("用户不存在！");
		}
		//动态码验证
		List<VerificationCode>  codes = dao.findByHQL("from VerificationCode where phone=? and vcode=? ", phone,code);
		if (codes == null || codes.size() <= 0) {
			throw new RuntimeException("动态码不正确！");
		}
		if (System.currentTimeMillis() - codes.get(0).getCreateTime().getTime() > 1000 * 60
				* Integer.valueOf(attrs.get("message_provider_valid_time"))) {
			throw new RuntimeException ("动态码超时，请重新发送！");
		}
		return "动态码正确！";
	}

	@Resource(name="simpleHibernateDao")
	public void setDao(SimpleHibernateDao dao) {
		this.dao = dao;
	}
}
