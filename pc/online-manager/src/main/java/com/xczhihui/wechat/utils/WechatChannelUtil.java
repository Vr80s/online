package com.xczhihui.wechat.utils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import com.xczhihui.bxg.common.support.service.AttachmentCenterService;
import com.xczhihui.user.center.utils.AccessToken;
import com.xczhihui.user.center.utils.HttpUtil;


@Component
public class WechatChannelUtil {

	
	//获取商户信息
	private static String OBTAIN_MERCHANT_INFORMATION = "https://api.weixin.qq.com/scan/merchantinfo/get?access_token=APPSECRET";
	
	//创建商品
	private static String CREATE_GOODS = "https://api.weixin.qq.com/scan/merchantinfo/get?access_token=APPSECRET";
	
	//获取二维码的ticket
	private static String QR_CODE_TICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=APPSECRET";
	
	//通过ticket 获取二维码图片
	private static String QR_CODE_IMG = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKETS";
	
	//获取永久素材   
	private static String GET_PERMANENT_MATERISL ="https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=APPSECRET"; 
		
	
	public static String access_token_url="https://api.weixin.qq.com/cgi-bin/token?"
	        + "grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	
    @Autowired
    private AttachmentCenterService service;
	
	
	public static  QrCodeVo getQrCodeVo(Integer qrSceneId,String token) throws Exception {
		
		QrCodeVo qr = new QrCodeVo();
		
        JSONObject jsonObjectToken = JSONObject.fromObject(token);
		String  access_token = (String)jsonObjectToken.get("access_token");
		Integer expires_in = (Integer)jsonObjectToken.get("expires_in");
		
		if(!StringUtils.isNotBlank(access_token)){
			throw new RuntimeException("获取token有误");
		}
		System.out.println("accessToken:"+access_token);
        /*
         * 生产临时的和永久的参数是不一样的哦。
         * 
         * action_name	二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，
         * 				QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
         */
		//获取二维码票据
		String url = QR_CODE_TICKET.replace("APPSECRET",access_token);
		String params = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": "+qrSceneId+"}}}";
		String Ticket = HttpUtil.doHttpsPost(url, params);
		System.out.println(Ticket);
		
		JSONObject jsonObject = JSONObject.fromObject(Ticket);
		String qr_ticket = (String) jsonObject.get("ticket");
		String qr_url = (String) jsonObject.get("url");
		qr.setCustomQrCodeUrl(qr_url);
		qr.setTicket(qr_ticket);
		
		//根据票据获取  --  二维码图片
		String ticket_url = QR_CODE_IMG.replace("TICKETS", qr_ticket);
		qr.setWechatUrl(ticket_url);
		qr.setCreateTime(new Date());
		return qr;
	}
	
	
	/**
	 * 通过获得的二维码票据  --》得到二维码
	 * Description：
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 * @throws Exception 
	 *
	 */
//	@Test
//	public void testEWM() throws Exception {
//		//String token =SingleAccessToken.getInstance().getAccessToken().getToken();
//		//System.out.println(token);
//		String url = QR_CODE_IMG.replace("TICKETS", "gQFZ8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyMVJyZVpidTFlRWkxUldKTzFxY1cAAgR64MpaAwQAjScA");
//		String hehe = HttpUtil.doGet(url);
//		System.out.println(hehe);
//		
//		//把这个二维码，我们自己也存储下啦。哈哈
//		//byte[] bs123 = Base64Utils.decodeFromString(hehe);
//		generateImage(hehe,"D://1.jpg");
//		
//		
//	}
	
	public static boolean generateImage(String imgStr,String imgFile)throws Exception {
	    // 对字节数组字符串进行Base64解码并生成图片
	    if (imgStr == null) // 图像数据为空
	        return false;
	    
	    //BASE64Decoder decoder = new BASE64Decoder();
	    try {
	        // Base64解码
	        byte[] b = Base64Utils.decodeFromString(imgStr);
	        for (int i = 0; i < b.length; ++i) {
	            if (b[i] < 0) {// 调整异常数据
	                b[i] += 256;
	            }
	        }
	        // 生成jpeg图片
	        String imgFilePath = imgFile;// 新生成的图片
	        OutputStream out = new FileOutputStream(imgFilePath);
	        out.write(b);
	        out.flush();
	        out.close();
	        return true;
	    } catch (Exception e) {
	        throw e;
	    }
	}
	
}
