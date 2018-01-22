package com.xczh.consumer.market.controller.course;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczh.consumer.market.utils.ResponseObject;
import com.xczhihui.wechat.course.service.IGiftService;
import com.xczhihui.wechat.course.vo.RankingUserVO;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;


/**
 * Description：礼物相关控制层
 * creed: Talk is cheap,show me the code
 * @author name：yuxin <br>email: yuruixin@ixincheng.com
 * @Date: 下午 5:19 2018/1/22 0022
 **/
@Controller
@RequestMapping(value = "/xczh/gift")
public class GiftController {

	@Autowired
	private IGiftService giftService;

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GiftController.class);

	/**
	 * 礼物榜单（直播间）
	 * @return
	 * @throws SQLException
	 */
	@ResponseBody
	@RequestMapping(value = "/rankingList")
	public ResponseObject rankingList(@RequestParam(value="liveId")String liveId, @RequestParam(value="current")Integer current, @RequestParam(value="size")Integer size) throws SQLException {
		Page<RankingUserVO> page = new Page<>();
		page.setCurrent(current);
		page.setSize(size);
		return ResponseObject.newSuccessResponseObject(giftService.rankingList(page,liveId));
	}

}
