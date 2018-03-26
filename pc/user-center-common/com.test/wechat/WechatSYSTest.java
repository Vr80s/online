package wechat;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.xczhihui.user.center.utils.HttpUtil;
import com.xczhihui.user.center.utils.SingleAccessToken;

public class WechatSYSTest {

	
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
	
	
	/**
	 * 
	 * Description：获取永久素材   
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@Test
	public void testGET_PERMANENT_MATERISL() {
	/*	String token =SingleAccessToken.getInstance().getAccessToken().getToken();
		System.out.println(token);*/
		String url = GET_PERMANENT_MATERISL.replace("APPSECRET", "8_ESe5hkkvBWRLO-6W5YJdQ-RQUuq8SYq-JY54r2j41KFbsQX3NGFspHUk55ulY3JHvJujCrLP7XVmpa1EG4cnoTtqsJAEIUsjRNhSvOEDuyTHRG1hUOkiRCR-r-FMo24VE25kxfF3zZWcmgkqNPWhAJAHJQ");
		
		Map<String,String> parameters = new HashMap<String,String>();
		parameters.put("type", "news");
		parameters.put("offset", "0");
		parameters.put("count", "10");
		
		String str ="{\"type\":\"news\",\"offset\":0,\"count\":10}";
		
		String hehe =  HttpUtil.doHttpsPost(url, str);
		
		System.out.println(hehe);
	}
	
	/**
	 * 微信扫一扫，暂时不支持这个功能
	 * Description：
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@Test
	public void testSYS() {
		
		String token =SingleAccessToken.getInstance().getAccessToken().getToken();
		System.out.println(token);
		String url = OBTAIN_MERCHANT_INFORMATION.replace("APPSECRET", "8_UTNb4a2W81yG5oOmRu7vqZPT8l90ipITfkBFfoqf93u9OhY9Ily4uEuCDsTthxNRNMYG9YedckziFBqQMlSlDwpe9dTjbtExgq8BvIvjxEu7x9Toyfq-y2EdGXtSo-aMz5GWCS8kWrAqfpvcJJAcABAAAR");
		String goods_info  = HttpUtil.doGet(url);
		System.out.println(goods_info);
		
	}
	
	@Test
	public void testEWMTicket() {
		
		/*String token =SingleAccessToken.getInstance().getAccessToken().getToken();
		System.out.println(token);*/
		String url = QR_CODE_TICKET.replace("APPSECRET","8_7oaxQHY9nyj_6ZSUFO36w5_e6USU07EIny2znh-r64vjjdSFPMxx3B_Cy69luIzRWG82edzWGkCP1YJum-8lrdnrzH3vTyrCJXQtnUsQz3qm_pOoE9R_6Yciov4X7SLmPDRTeUnraEorz0zKSYBaAGAJPS" );
		String params = "{\"expire_seconds\":2592000,\"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"心承test\"}}}";
		String hehe = HttpUtil.doHttpsPost(url, params);
		
		System.out.println(hehe);
		
	}
	
	@Test
	public void testEWM() {
		//String token =SingleAccessToken.getInstance().getAccessToken().getToken();
		//System.out.println(token);
		String url = QR_CODE_IMG.replace("TICKETS", "gQGr7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyaEhqZ1pSdTFlRWkxQTRaVGhxY3cAAgTIfLdaAwQ8AAAA");
		String hehe = HttpUtil.doGet(url);
		System.out.println(hehe);
		
	}
	
	//{"ticket":"gQGr7zwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyaEhqZ1pSdTFlRWkxQTRaVGhxY3cAAgTIfLdaAwQ8AAAA","expire_seconds":60,"url":"http:\/\/weixin.qq.com\/q\/02hHjgZRu1eEi1A4ZThqcw"}

}
