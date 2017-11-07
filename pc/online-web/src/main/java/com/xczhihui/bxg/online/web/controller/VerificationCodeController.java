package com.xczhihui.bxg.online.web.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.xczhihui.bxg.common.util.bean.ResponseObject;
import com.xczhihui.bxg.online.common.base.controller.OnlineBaseController;
import com.xczhihui.bxg.online.web.service.VerificationCodeService;

/**
 * 动态码
 * 
 * @author Haicheng Jiang
 */
@RestController
@RequestMapping(value="/online/verificationCode")
public class VerificationCodeController extends OnlineBaseController {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private VerificationCodeService service;
	/**
	 * 发送手机动态码
	 * 
	 * 业务逻辑： 1、动态码有效期XX分钟，XX分钟之内发送的动态码都一样
	 * 2、同一账号两次发送间隔至少XX秒
	 *
	 * @param phone 手机号
	 * @param vtype 动态码类型，1注册，2重置密码
	 * @return 成功失败
	 */
	@RequestMapping(value = "sendmessage", method = RequestMethod.POST)
	public ResponseObject sendmessage(String phone, String vtype,String vcode,HttpServletRequest req) {
		Object sessionCode = req.getSession().getAttribute("randomCode");
		if (sessionCode == null || !sessionCode.toString().equals(vcode)) {
			logger.info("图形验证码错误！");
			return ResponseObject.newErrorResponseObject("验证码错误");
		}
		return ResponseObject.newSuccessResponseObject(service.addMessage(phone,vtype));
	}

	/**
	 * 发送邮件验证信息
	 * 
	 * 业务逻辑： 
	 * 1、动态码有效期XX分钟，XX分钟之内发送的验证信息都一样
	 * 2、同一账号两次发送间隔至少XX秒
	 * 
	 * @param email  邮箱地址
	 * @param vtype 类型，1注册，2重置密码
	 * @return 成功失败
	 */
	@RequestMapping(value = "sendemail", method = RequestMethod.POST)
	public ResponseObject sendEmail(String email,HttpServletRequest req) {
		return ResponseObject.newSuccessResponseObject(service.addMessage(email, "2"));
	}
	/**
	 * 校验动态码
	 * @param phone
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "checkCode", method = RequestMethod.POST)
	public ResponseObject checkCode(String phone,String code) {
		return ResponseObject.newSuccessResponseObject(service.checkCode(phone, code));
	}
	
	@RequestMapping(value = "vcode", method = RequestMethod.GET)
	public void getUploadUrl(HttpServletRequest req, HttpServletResponse response,HttpSession session) throws Exception {
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
		    session.setAttribute("randomCode", randomCode.toString());//把运算符结果值set到session中，用于其他文件进行验证码校对    
		    buffImg.flush();
		    g.dispose();
		    response.setContentType("image/jpeg");
		    response.setHeader("Pragma", "no-cache");
		    response.setHeader("Cache-Control", "no-cache");
		    response.setDateHeader("Expires", 0);
		    ServletOutputStream outputStream = response.getOutputStream();
		    ImageIO.write(buffImg, "jpeg", outputStream);
		    outputStream.close();
	}
}
