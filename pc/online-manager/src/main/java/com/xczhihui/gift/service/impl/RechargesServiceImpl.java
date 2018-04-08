package com.xczhihui.gift.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.gift.vo.RechargesVo;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.Recharges;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.gift.dao.RechargesDao;
import com.xczhihui.gift.service.RechargesService;
import com.xczhihui.support.shiro.ManagerUserUtil;

/**
 * RechargesServiceImpl:礼物业务层接口实现类
 * 
 * @author Rongcai Kang
 */
@Service("rechargesService")
public class RechargesServiceImpl extends OnlineBaseServiceImpl implements
	RechargesService {

	@Autowired
	private RechargesDao rechargesDao;

	@Override
	public Page<RechargesVo> findRechargesPage(RechargesVo rechargesVo, int pageNumber, int pageSize) {
		Page<RechargesVo> page = rechargesDao.findRechargesPage(rechargesVo, pageNumber, pageSize);
		return page;

	}

	/**
	 * 根据礼物ID号，查找对应的礼物对象
	 * 
	 * @param RechargesId
	 *            礼物id
	 * @return Example 分页列表
	 */
	@Override
	public RechargesVo getRechargesById(Integer RechargesId) {

		RechargesVo RechargesVo = null;

		if (RechargesId != null) {
			String hql = "from Recharges where 1=1 and isDelete=0 and id = ?";
			RechargesVo Recharges = dao.findByHQLOne(hql, new Object[] { RechargesId });
			if (Recharges != null) {
				RechargesVo = new RechargesVo();
				RechargesVo.setId(Recharges.getId());
				RechargesVo.setCreateTime(Recharges.getCreateTime());
				RechargesVo.setStatus(Recharges.getStatus());

			}
		}
		return RechargesVo;
	}

	@Override
	public List<RechargesVo> getRechargeslist(String search) {
		String sql = "SELECT og.id,og.name,u.`name`  createPerson,og.`create_time` createTime,og.`smallimg_path` smallimgPath,og.`price`,"
				+ "og.`is_free` isFree,og.`is_continuous` isContinuous,og.`continuous_count` continuousCount  "
				+ "FROM `oe_Recharges` og LEFT JOIN `user` u ON u.id = og.`create_person` WHERE og.`is_delete` = 0 ";
		Map<String, Object> params = new HashMap<String, Object>();
		if ("".equals(search) || null == search) {
			List<RechargesVo> voList = dao.findEntitiesByJdbc(RechargesVo.class, sql,
					params);
			return voList;
		}
		sql += "and og.name like '%" + search + "%'";
		List<RechargesVo> voList = dao.findEntitiesByJdbc(RechargesVo.class, sql, params);
		return voList;
	}

	@Override
	public void addRecharges(RechargesVo rechargesVo) throws IllegalAccessException,
			InvocationTargetException {

		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT IFNULL(MAX(sort),0) as sort FROM oe_recharges ";
		List<Recharges> temp = dao.findEntitiesByJdbc(Recharges.class, sql, params);
		int sort;
		if (temp.size() > 0) {
			sort = temp.get(0).getSort().intValue() + 1;
		} else {
			sort = 1;
		}
		rechargesVo.setCreateTime(new Date());
		Recharges recharges = new Recharges();
		BeanUtils.copyProperties(recharges, rechargesVo);
		recharges.setStatus("0");
		recharges.setSort(sort);
		recharges.setIsDelete(false);
		recharges.setCreatePerson(ManagerUserUtil.getId());
		// 增加密码和老师
		dao.save(recharges);
	}

	@Override
	public void updateRecharges(RechargesVo rechargesVo) throws IllegalAccessException,
			InvocationTargetException {
		Recharges Recharges = dao.findOneEntitiyByProperty(Recharges.class, "id",
				rechargesVo.getId());
		rechargesVo.setCreatePerson(Recharges.getCreatePerson());
		rechargesVo.setStatus(Recharges.getStatus());
		rechargesVo.setCreateTime(new Date());
		BeanUtils.copyProperties(Recharges, rechargesVo);

		
		dao.update(Recharges);
	}

	@Override
	public void updateStatus(Integer id) {
		String hql = "from Recharges where 1=1 and isDelete=0 and id = ?";
		Recharges Recharges = dao.findByHQLOne(hql, new Object[] { id });

		if (Recharges.getStatus() != null && "1".equals(Recharges.getStatus())) {
			Recharges.setStatus("0");
		} else {
			Recharges.setStatus("1");
		}

		dao.update(Recharges);
	}

	@Override
	public void deleteRechargesById(Integer id) {
		rechargesDao.deleteById(id);
	}

	@Override
	public void updateSortUp(Integer id) {
		String hqlPre = "from Recharges where  isDelete=0 and id = ?";
		Recharges RechargesPre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer RechargesPreSort = RechargesPre.getSort();

		String hqlNext = "from Recharges where sort > (select sort from Recharges where id= ? ) and isDelete=0 and status =1 order by sort asc";
		Recharges RechargesNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer RechargesNextSort = RechargesNext.getSort();

		RechargesPre.setSort(RechargesNextSort);
		RechargesNext.setSort(RechargesPreSort);

		dao.update(RechargesPre);
		dao.update(RechargesNext);
	}

	@Override
	public void updateSortDown(Integer id) {
		String hqlPre = "from Recharges where  isDelete=0 and id = ?";
		Recharges RechargesPre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer RechargesPreSort = RechargesPre.getSort();
		String hqlNext = "from Recharges where sort < (select sort from Recharges where id= ? ) and isDelete=0 and status =1 order by sort desc";
		Recharges RechargesNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer RechargesNextSort = RechargesNext.getSort();

		RechargesPre.setSort(RechargesNextSort);
		RechargesNext.setSort(RechargesPreSort);

		dao.update(RechargesPre);
		dao.update(RechargesNext);

	}

	@Override
	public void deletes(String[] ids) {
		for (String id : ids) {
			String hqlPre = "from Recharges where isDelete=0 and id = ?";
			Recharges recharges = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (recharges != null) {
				recharges.setIsDelete(true);
				dao.update(recharges);
			}
		}
	}

	public List<Recharges> findByName(String name) {
		List<Recharges> Rechargess = dao.findEntitiesByProperty(Recharges.class, "gradeName",
				name);
		return Rechargess;
	}

	@Override
	public void updateBrokerage(String ids, String brokerage) {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			String hqlPre = "from Recharges where isDelete=0 and id = ?";
			Recharges Recharges = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (Recharges != null) {
				Recharges.setCreateTime(new Date());
				dao.update(Recharges);
			}
		}
		
	}

}
