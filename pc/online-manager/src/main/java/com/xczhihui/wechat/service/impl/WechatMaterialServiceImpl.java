package com.xczhihui.wechat.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xczhihui.wechat.service.WechatMaterialService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.WechatMaterial;
import com.xczhihui.bxg.online.common.domain.WechatMediaManager;
import com.xczhihui.wechat.dao.WechatMaterialDao;

@Service("wechatMaterialService")
public class WechatMaterialServiceImpl extends OnlineBaseServiceImpl implements
		WechatMaterialService {

	@Autowired
	private WechatMaterialDao wechatMaterialDao;

	@Override
	public Page<WechatMaterial> findWechatMaterialPage(
			WechatMaterial WechatMaterial, int pageNumber, int pageSize) {
		Page<WechatMaterial> page = wechatMaterialDao.findWechatMaterialPage(
				WechatMaterial, pageNumber, pageSize);
		return page;
	}

	/**
	 * 根据礼物ID号，查找对应的礼物对象
	 * 
	 * @param WechatMaterialId
	 *            礼物id
	 * @return Example 分页列表
	 */
	@Override
	public WechatMaterial getWechatMaterialById(Integer WechatMaterialId) {

		WechatMaterial WechatMaterial = null;

		if (WechatMaterialId != null) {
			String hql = "from WechatMaterial where 1=1 and isDelete=0 and id = ?";
			WechatMaterial wechatMaterial = dao.findByHQLOne(hql,
					new Object[] { WechatMaterialId });
			if (WechatMaterial != null) {
				WechatMaterial = new WechatMaterial();
				WechatMaterial.setId(WechatMaterial.getId());
				WechatMaterial.setCreateTime(WechatMaterial.getCreateTime());
			}
		}
		return WechatMaterial;
	}

	@Override
	public List<WechatMaterial> getWechatMateriallist(String search) {
		String sql = "SELECT og.id,og.name,u.`name`  createPerson,og.`create_time` createTime,og.`smallimg_path` smallimgPath,og.`price`,"
				+ "og.`is_free` isFree,og.`is_continuous` isContinuous,og.`continuous_count` continuousCount  "
				+ "FROM `oe_WechatMaterial` og LEFT JOIN `user` u ON u.id = og.`create_person` WHERE og.`is_delete` = 0 ";
		Map<String, Object> params = new HashMap<String, Object>();
		if ("".equals(search) || null == search) {
			List<WechatMaterial> voList = dao.findEntitiesByJdbc(
					WechatMaterial.class, sql, params);
			return voList;
		}
		sql += "and og.name like '%" + search + "%'";
		List<WechatMaterial> voList = dao.findEntitiesByJdbc(
				WechatMaterial.class, sql, params);
		return voList;
	}

	@Override
	public void addWechatMaterial(WechatMaterial WechatMaterial) {

		dao.save(WechatMaterial);
	}

	@Override
	public void updateWechatMaterial(WechatMaterial WechatMaterial)
			throws IllegalAccessException, InvocationTargetException {
		WechatMaterial wechatMaterial = dao.findOneEntitiyByProperty(
				WechatMaterial.class, "id", WechatMaterial.getId());
		WechatMaterial.setCreatePerson(WechatMaterial.getCreatePerson());
		WechatMaterial.setCreateTime(new Date());
		BeanUtils.copyProperties(WechatMaterial, WechatMaterial);
		dao.update(WechatMaterial);
	}

	@Override
	public void updateStatus(Integer id) {
		String hql = "from WechatMaterial where 1=1 and isDelete=0 and id = ?";
		WechatMaterial WechatMaterial = dao.findByHQLOne(hql,
				new Object[] { id });

		dao.update(WechatMaterial);
	}

	@Override
	public void deleteWechatMaterialById(Integer id) {
		wechatMaterialDao.deleteById(id);
	}

	@Override
	public void updateSortUp(Integer id) {
		String hqlPre = "from WechatMaterial where  isDelete=0 and id = ?";
		WechatMaterial WechatMaterialPre = dao.findByHQLOne(hqlPre,
				new Object[] { id });

		String hqlNext = "from WechatMaterial where sort > (select sort from WechatMaterial where id= ? ) and isDelete=0 and status =1 order by sort asc";
		WechatMaterial WechatMaterialNext = dao.findByHQLOne(hqlNext,
				new Object[] { id });

		dao.update(WechatMaterialPre);
		dao.update(WechatMaterialNext);
	}

	@Override
	public void updateSortDown(Integer id) {
		String hqlPre = "from WechatMaterial where  isDelete=0 and id = ?";
		WechatMaterial WechatMaterialPre = dao.findByHQLOne(hqlPre,
				new Object[] { id });
		dao.update(WechatMaterialPre);

	}

	@Override
	public void deletes(String[] ids) {
		for (String id : ids) {
			String hqlPre = "from WechatMaterial where isDelete=0 and id = ?";
			WechatMaterial WechatMaterial = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (WechatMaterial != null) {
				WechatMaterial.setDelete(true);
				dao.update(WechatMaterial);
			}
		}
	}

	public List<WechatMaterial> findByName(String name) {
		List<WechatMaterial> WechatMaterials = dao.findEntitiesByProperty(
				WechatMaterial.class, "gradeName", name);
		return WechatMaterials;
	}

	@Override
	public void updateBrokerage(String ids, String brokerage) {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			String hqlPre = "from WechatMaterial where isDelete=0 and id = ?";
			WechatMaterial WechatMaterial = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (WechatMaterial != null) {
				dao.update(WechatMaterial);
			}
		}

	}

	@Override
	public void addWechatMediaManager(WechatMediaManager wechatMediaManager) {

		dao.save(wechatMediaManager);
	}
}
