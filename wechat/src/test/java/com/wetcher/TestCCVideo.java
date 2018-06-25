package com.wetcher;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * 测试cc视频的接口
 * ClassName: TestCCVideo.java <br>
 * Description: <br>
 * Create by: name：yangxuan <br>email: 15936216273@163.com <br>
 * Create Time: 2018年3月14日<br>
 */
public class TestCCVideo {


	@Test
	public void testTime(){
		System.out.println(String.valueOf(new Date().getTime() / 1000));
		System.out.println(System.currentTimeMillis() / 1000);
	}

	
	/**
	 * 获取播放代码
	 * Description：
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@Test
	public void getPlayCode() {
	
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", "B5E673E55C702C42");
		paramsMap.put("videoid", "817766A2FD7B02F19C33DC5901307461");
		paramsMap.put("autoplay", "true");
		paramsMap.put("playerwidth", "100");
		paramsMap.put("playerheight", "120");
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
//		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,"K45btKhytR527yfTAjEp6z4fb3ajgu66");
//		System.out.println(requestURL);
//		String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/video/playcode?" + requestURL);
//		System.out.println(responsestr);
		
	}
	
	/**
	 * 获取视频详情
	 * Description：
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@Test
	public void getVideoDetails() {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", "B5E673E55C702C42");
		paramsMap.put("videoid", "070F3FC7BEAF701F9C33DC5901307461");
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
//		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,"K45btKhytR527yfTAjEp6z4fb3ajgu66");
//		System.out.println(requestURL);
//		String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/video/v3?" + requestURL);
//		System.out.println(responsestr);
	}

	/**
	 * 编辑视频详情
	 * Description：
	 * @return void
	 * @author name：yangxuan <br>email: 15936216273@163.com
	 *
	 */
	@Test
	public void editVideoDetails() {
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.put("userid", "B5E673E55C702C42");
		paramsMap.put("videoid", "070F3FC7BEAF701F9C33DC5901307461");
		
		paramsMap.put("format", "json");
		long time = System.currentTimeMillis();
//		String requestURL = APIServiceFunction.createHashedQueryString(paramsMap, time,"K45btKhytR527yfTAjEp6z4fb3ajgu66");
//		System.out.println(requestURL);
//		String responsestr = APIServiceFunction.HttpRetrieve("http://spark.bokecc.com/api/video/v3?" + requestURL);
//		System.out.println(responsestr);
	}
	
	
}
