package com.xczhihui.gift.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import com.xczhihui.gift.vo.RewardVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.Reward;
import com.xczhihui.gift.dao.RewardDao;
import com.xczhihui.gift.service.RewardService;

/**
 * GiftServiceImpl:礼物业务层接口实现类
 * 
 * @author Rongcai Kang
 */
@Service("rewardService")
public class RewardServiceImpl extends OnlineBaseServiceImpl implements RewardService {

	@Autowired
	private RewardDao rewardDao;

	@Override
	public Page<RewardVo> findRewardPage(RewardVo giftVo, int pageNumber, int pageSize) {
		Page<RewardVo> page = rewardDao.findRewardPage(giftVo, pageNumber, pageSize);
		return page;

	}

	/**
	 * 根据礼物ID号，查找对应的礼物对象
	 * 
	 * @param giftId
	 *            礼物id
	 * @return Example 分页列表
	 */
	@Override
	public RewardVo getRewardById(Integer giftId) {

		RewardVo giftVo = null;

		if (giftId != null) {
			String hql = "from Reward where 1=1 and isDelete=0 and id = ?";
			Reward gift = dao.findByHQLOne(hql, new Object[] { giftId });
			if (gift != null) {
				giftVo = new RewardVo();
				giftVo.setId(gift.getId());
				giftVo.setCreateTime(gift.getCreateTime());
				giftVo.setStatus(gift.isStatus());
			}
		}
		return giftVo;
	}


	@Override
	public void updateReward(RewardVo giftVo) throws IllegalAccessException,
			InvocationTargetException {
		Reward gift = dao.findOneEntitiyByProperty(Reward.class, "id",
				giftVo.getId());
		giftVo.setCreatePerson(gift.getCreatePerson());
		giftVo.setStatus(gift.isStatus());
		giftVo.setCreateTime(new Date());
		if(giftVo.getPrice()==0){
			giftVo.setIsFreeDom(true);
		}else{
			giftVo.setIsFreeDom(false);
		}
		BeanUtils.copyProperties(gift, giftVo);
		dao.update(gift);
	}

	@Override
	public void updateStatus(Integer id) {
		String hql = "from Reward where 1=1 and isDelete=0 and id = ?";
		Reward gift = dao.findByHQLOne(hql, new Object[] { id });

		if (gift.isStatus()) {
			gift.setStatus(false);
		} else {
			gift.setStatus(true);
		}

		dao.update(gift);
	}

	@Override
	public void updateSortUp(Integer id) {
		String hqlPre = "from Reward where  isDelete=0 and id = ?";
		Reward giftPre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer giftPreSort = giftPre.getSort();

		String hqlNext = "from Reward where sort > (select sort from Reward where id= ? ) and isDelete=0 and status=1 order by sort asc";
		Reward giftNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer giftNextSort = giftNext.getSort();

		giftPre.setSort(giftNextSort);
		giftNext.setSort(giftPreSort);

		dao.update(giftPre);
		dao.update(giftNext);
	}

	@Override
	public void updateSortDown(Integer id) {
		String hqlPre = "from Reward where  isDelete=0 and id = ?";
		Reward giftPre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer giftPreSort = giftPre.getSort();
		String hqlNext = "from Reward where sort < (select sort from Reward where id= ? ) and isDelete=0 and status=1 order by sort desc";
		Reward giftNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer giftNextSort = giftNext.getSort();

		giftPre.setSort(giftNextSort);
		giftNext.setSort(giftPreSort);

		dao.update(giftPre);
		dao.update(giftNext);

	}

	@Override
	public void updateBrokerage(String ids, String brokerage) {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			String hqlPre = "from Reward where isDelete=0 and id = ?";
			Reward reward = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (reward != null) {
				reward.setBrokerage(Double.valueOf(brokerage));
				reward.setCreateTime(new Date());
				dao.update(reward);
			}
		}
		
	}

}
