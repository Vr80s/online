package com.xczhihui.wechat.course.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.vo.RankingUserVO;


/**
 * @author yuruixin
 */
public interface IGiftService {

	/**
	 * Description：获取礼物榜单
	 * creed: Talk is cheap,show me the code
	 * @author name：yuxin <br>email: yuruixin@ixincheng.com
	 * @Date: 下午 5:21 2018/1/22 0022
	 **/
	Page<RankingUserVO> rankingList(Page page,String liveId) ;
}
