package com.xczh.consumer.market.controller.threeparties;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xczh.consumer.market.service.CoreMessageService;
import com.xczh.consumer.market.utils.ConfigUtil;

/**
 * 微信消息公共接口 ClassName: WechatMessageController.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>
 * email: 15936216273@163.com <br>
 * Create Time: 2018年3月19日<br>
 */
@Controller
@RequestMapping("/xczh/message")
public class WechatMessageController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory
			.getLogger(WechatMessageController.class);

	
	@Value("${wxToken}")
	private String wxToken;
	
	@Autowired
	private CoreMessageService coreMessageService;

	@RequestMapping(method = RequestMethod.GET)
	public void get(HttpServletRequest req, HttpServletResponse res) throws IOException {
		// 微信加密签名
	    String signature = req.getParameter("signature");	    
	    // 随机字符串
	    String echostr = req.getParameter("echostr");
	    // 时间戳
	    String timestamp = req.getParameter("timestamp");
	    // 随机数
	    String nonce = req.getParameter("nonce");
	    
	    
	    String[] str = { wxToken, timestamp, nonce };
	    Arrays.sort(str); // 字典序排序
	    String bigStr = str[0] + str[1] + str[2];
	       
        //SHA1加密
        //String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();
	    
	    String digest = "";
	    try {
	    	MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(bigStr.getBytes("UTF-8"));
	        digest = byteToHex(crypt.digest());
	        digest = digest.toLowerCase();
	    } catch (NoSuchAlgorithmException e) {
	    	e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	    	e.printStackTrace();
	    }
	    	    
	    //if(true) {
	    //	LOGGER.info("signature=" + signature);
	    //	LOGGER.info("echostr=" + echostr);
	    //	LOGGER.info("timestamp=" + timestamp);
	    //	LOGGER.info("nonce=" + nonce);
	    //	LOGGER.info("digest=" + digest);
	    //}
	   
	    // 确认请求来至微信
	    if (digest.equals(signature)) {
	    	
	    	LOGGER.info("get方法-----》认证成功："+signature);
	    	
	    	res.getWriter().print(echostr);
	    } else {
	    	
	    	LOGGER.info("get方法-----》认证失败："+signature);
	    	
	    	res.getWriter().print("error");
	    }
	    
	    
	}

	@RequestMapping(method = RequestMethod.POST)
	public void post(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		LOGGER.info("post方法-----》接受用户传递过来的消息，或者微信端的消息，我们进行响应。");
		
		response.setCharacterEncoding("UTF-8");
		// 调用核心业务类接收消息、处理消息
		String respMessage = coreMessageService.processRequest(request);
		// 响应消息
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(respMessage);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
			out = null;
		}
	}

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
	
    /**
	 * 将字节数组转换为十六进制字符串
	 *
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++) {
			strDigest += byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 *
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}   
	
	
}
