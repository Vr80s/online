package com.xczhihui.gift.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.gift.dao.GiftDao;
import com.xczhihui.gift.service.GiftService;
import com.xczhihui.gift.vo.GiftVo;
import com.xczhihui.support.shiro.ManagerUserUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Gift;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;

/**
 * GiftServiceImpl:礼物业务层接口实现类
 * 
 * @author Rongcai Kang
 */
@Service("giftService")
public class GiftServiceImpl extends OnlineBaseServiceImpl implements
		GiftService {

	@Autowired
	private GiftDao giftDao;

	@Override
	public Page<GiftVo> findGiftPage(GiftVo giftVo, int pageNumber, int pageSize) {
		Page<GiftVo> page = giftDao.findGiftPage(giftVo, pageNumber, pageSize);
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
	public GiftVo getGiftById(Integer giftId) {

		GiftVo giftVo = null;

		if (giftId != null) {
			String hql = "from Gift where 1=1 and isDelete=0 and id = ?";
			GiftVo gift = dao.findByHQLOne(hql, new Object[] { giftId });
			if (gift != null) {
				giftVo = new GiftVo();
				giftVo.setId(gift.getId());
				giftVo.setCreateTime(gift.getCreateTime());
				giftVo.setStatus(gift.getStatus());

			}
		}
		return giftVo;
	}

	@Override
	public List<GiftVo> getGiftlist(String search) {
		String sql = "SELECT og.id,og.name,u.`name`  createPerson,og.`create_time` createTime,og.`smallimg_path` smallimgPath,og.`price`,"
				+ "og.`is_free` isFree,og.`is_continuous` isContinuous,og.`continuous_count` continuousCount  "
				+ "FROM `oe_gift` og LEFT JOIN `user` u ON u.id = og.`create_person` WHERE og.`is_delete` = 0 ";
		Map<String, Object> params = new HashMap<String, Object>();
		if ("".equals(search) || null == search) {
			List<GiftVo> voList = dao.findEntitiesByJdbc(GiftVo.class, sql,
					params);
			return voList;
		}
		sql += "and og.name like '%" + search + "%'";
		List<GiftVo> voList = dao.findEntitiesByJdbc(GiftVo.class, sql, params);
		return voList;
	}

	@Override
	public void addGift(GiftVo giftVo) throws IllegalAccessException,
			InvocationTargetException {

		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT IFNULL(MAX(sort),0) as sort FROM oe_gift ";
		List<Gift> temp = dao.findEntitiesByJdbc(Gift.class, sql, params);
		int sort;
		if (temp.size() > 0) {
			sort = temp.get(0).getSort().intValue() + 1;
		} else {
			sort = 1;
		}
		giftVo.setCreateTime(new Date());
		Gift gift = new Gift();
		BeanUtils.copyProperties(gift, giftVo);
		gift.setStatus("0");
		gift.setSort(sort);
		if (0 == gift.getPrice()) {
			gift.setIsFree(true); // 免费
		} else {
			gift.setIsFree(false); // 付费
		}
		gift.setCreatePerson(ManagerUserUtil.getId());
		// 增加密码和老师
		dao.save(gift);
	}

	@Override
	public void updateGift(GiftVo giftVo) throws IllegalAccessException,
			InvocationTargetException {
		Gift gift = dao.findOneEntitiyByProperty(Gift.class, "id",
				giftVo.getId());
		giftVo.setCreatePerson(gift.getCreatePerson());
		giftVo.setStatus(gift.getStatus());
		giftVo.setCreateTime(new Date());
		BeanUtils.copyProperties(gift, giftVo);
		if (0 == gift.getPrice()) {
			gift.setIsFree(true); // 免费
		} else {
			gift.setIsFree(false); // 付费
		}
		dao.update(gift);
	}

	@Override
	public void updateStatus(Integer id) {
		String hql = "from Gift where 1=1 and isDelete=0 and id = ?";
		Gift gift = dao.findByHQLOne(hql, new Object[] { id });

		if (gift.getStatus() != null && "1".equals(gift.getStatus())) {
			gift.setStatus("0");
		} else {
			gift.setStatus("1");
		}

		dao.update(gift);
	}

	@Override
	public void deleteGiftById(Integer id) {
		giftDao.deleteById(id);
	}

	@Override
	public void updateSortUp(Integer id) {
		String hqlPre = "from Gift where  isDelete=0 and id = ?";
		Gift giftPre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer giftPreSort = giftPre.getSort();

		String hqlNext = "from Gift where sort > (select sort from Gift where id= ? ) and isDelete=0 and status =1 order by sort asc";
		Gift giftNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer giftNextSort = giftNext.getSort();

		giftPre.setSort(giftNextSort);
		giftNext.setSort(giftPreSort);

		dao.update(giftPre);
		dao.update(giftNext);
	}

	@Override
	public void updateSortDown(Integer id) {
		String hqlPre = "from Gift where  isDelete=0 and id = ?";
		Gift giftPre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer giftPreSort = giftPre.getSort();
		String hqlNext = "from Gift where sort < (select sort from Gift where id= ? ) and isDelete=0 and status =1 order by sort desc";
		Gift giftNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer giftNextSort = giftNext.getSort();

		giftPre.setSort(giftNextSort);
		giftNext.setSort(giftPreSort);

		dao.update(giftPre);
		dao.update(giftNext);

	}

	@Override
	public void deletes(String[] ids) {
		for (String id : ids) {
			String hqlPre = "from Gift where isDelete=0 and id = ?";
			Gift gift = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (gift != null) {
				gift.setDelete(true);
				dao.update(gift);
			}
		}
	}

	public List<Gift> findByName(String name) {
		List<Gift> gifts = dao.findEntitiesByProperty(Gift.class, "gradeName",
				name);
		return gifts;
	}

	@Override
	public void updateBrokerage(String ids, String brokerage) {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			String hqlPre = "from Gift where isDelete=0 and id = ?";
			Gift gift = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (gift != null) {
				gift.setBrokerage(Double.valueOf(brokerage));
				gift.setCreateTime(new Date());
				dao.update(gift);
			}
		}

	}

}
