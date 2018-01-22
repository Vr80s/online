package com.xczhihui.wechat.course.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.wechat.course.mapper.GiftMapper;
import com.xczhihui.wechat.course.service.IGiftService;
import com.xczhihui.wechat.course.vo.RankingUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  礼物相关服务实现类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class GiftServiceImpl  implements IGiftService {

	@Autowired
	private GiftMapper giftMapper;

	@Override
	public Page<RankingUserVO> rankingList(Page page,String liveId) {
		List<RankingUserVO> records = giftMapper.selectRankingList(page,liveId);
		return page.setRecords(records);
	}
}
