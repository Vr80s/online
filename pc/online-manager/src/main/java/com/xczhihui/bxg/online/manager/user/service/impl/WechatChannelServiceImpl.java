package com.xczhihui.bxg.online.manager.user.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.WechatChannel;
import com.xczhihui.bxg.online.manager.support.shiro.ManagerUserUtil;
import com.xczhihui.bxg.online.manager.user.dao.WechatChannelDao;
import com.xczhihui.bxg.online.manager.user.service.WechatChannelService;
import com.xczhihui.bxg.online.manager.user.vo.WechatChannelVo;

/**
 * WechatChannelServiceImpl:礼物业务层接口实现类
 * 
 * @author Rongcai Kang
 */
@Service("wechatChannelService")
public class WechatChannelServiceImpl extends OnlineBaseServiceImpl implements
	WechatChannelService {

	@Autowired
	private WechatChannelDao wechatChannelDao;

	@Override
	public Page<WechatChannelVo> findWechatChannelPage(WechatChannelVo WechatChannelVo, int pageNumber, int pageSize) {
		Page<WechatChannelVo> page = wechatChannelDao.findWechatChannelPage(WechatChannelVo, pageNumber, pageSize);
		return page;

	}

	/**
	 * 根据礼物ID号，查找对应的礼物对象
	 * 
	 * @param WechatChannelId
	 *            礼物id
	 * @return Example 分页列表
	 */
	@Override
	public WechatChannelVo getWechatChannelById(Integer WechatChannelId) {

		WechatChannelVo WechatChannelVo = null;

		if (WechatChannelId != null) {
			String hql = "from WechatChannel where 1=1 and isDelete=0 and id = ?";
			WechatChannelVo WechatChannel = dao.findByHQLOne(hql, new Object[] { WechatChannelId });
			if (WechatChannel != null) {
				WechatChannelVo = new WechatChannelVo();
				WechatChannelVo.setId(WechatChannel.getId());
				WechatChannelVo.setCreateTime(WechatChannel.getCreateTime());
				WechatChannelVo.setStatus(WechatChannel.getStatus());

			}
		}
		return WechatChannelVo;
	}

	@Override
	public List<WechatChannelVo> getWechatChannellist(String search) {
		String sql = "SELECT og.id,og.name,u.`name`  createPerson,og.`create_time` createTime,og.`smallimg_path` smallimgPath,og.`price`,"
				+ "og.`is_free` isFree,og.`is_continuous` isContinuous,og.`continuous_count` continuousCount  "
				+ "FROM `oe_wechat_channel` og LEFT JOIN `user` u ON u.id = og.`create_person` WHERE og.`is_delete` = 0 ";
		Map<String, Object> params = new HashMap<String, Object>();
		if ("".equals(search) || null == search) {
			List<WechatChannelVo> voList = dao.findEntitiesByJdbc(WechatChannelVo.class, sql,
					params);
			return voList;
		}
		sql += "and og.name like '%" + search + "%'";
		List<WechatChannelVo> voList = dao.findEntitiesByJdbc(WechatChannelVo.class, sql, params);
		return voList;
	}

	@Override
	public void addWechatChannel(WechatChannelVo WechatChannelVo) throws IllegalAccessException,
			InvocationTargetException {

		Map<String, Object> params = new HashMap<String, Object>();
		String sql = "SELECT IFNULL(MAX(sort),0) as sort FROM oe_wechat_channel ";
		List<WechatChannel> temp = dao.findEntitiesByJdbc(WechatChannel.class, sql, params);
		int sort;
		if (temp.size() > 0) {
			sort = temp.get(0).getSort().intValue() + 1;
		} else {
			sort = 1;
		}
		WechatChannelVo.setCreateTime(new Date());
		WechatChannel WechatChannel = new WechatChannel();
		BeanUtils.copyProperties(WechatChannel, WechatChannelVo);
		WechatChannel.setStatus("0");
		WechatChannel.setSort(sort);
		WechatChannel.setDelete(false);
		WechatChannel.setCreatePerson(ManagerUserUtil.getId());
		// 增加密码和老师
		dao.save(WechatChannel);
	}

	@Override
	public void updateWechatChannel(WechatChannelVo WechatChannelVo) throws IllegalAccessException,
			InvocationTargetException {
		WechatChannel WechatChannel = dao.findOneEntitiyByProperty(WechatChannel.class, "id",
				WechatChannelVo.getId());
		WechatChannelVo.setCreatePerson(WechatChannel.getCreatePerson());
		WechatChannelVo.setStatus(WechatChannel.getStatus());
		WechatChannelVo.setCreateTime(new Date());
		BeanUtils.copyProperties(WechatChannel, WechatChannelVo);

		
		dao.update(WechatChannel);
	}

	@Override
	public void updateStatus(Integer id) {
		String hql = "from WechatChannel where 1=1 and isDelete=0 and id = ?";
		WechatChannel WechatChannel = dao.findByHQLOne(hql, new Object[] { id });

		if (WechatChannel.getStatus() != null && "1".equals(WechatChannel.getStatus())) {
			WechatChannel.setStatus("0");
		} else {
			WechatChannel.setStatus("1");
		}

		dao.update(WechatChannel);
	}

	@Override
	public void deleteWechatChannelById(Integer id) {
		wechatChannelDao.deleteById(id);
	}

	@Override
	public void updateSortUp(Integer id) {
		String hqlPre = "from WechatChannel where  isDelete=0 and id = ?";
		WechatChannel WechatChannelPre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer WechatChannelPreSort = WechatChannelPre.getSort();

		String hqlNext = "from WechatChannel where sort > (select sort from WechatChannel where id= ? ) and isDelete=0 and status =1 order by sort asc";
		WechatChannel WechatChannelNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer WechatChannelNextSort = WechatChannelNext.getSort();

		WechatChannelPre.setSort(WechatChannelNextSort);
		WechatChannelNext.setSort(WechatChannelPreSort);

		dao.update(WechatChannelPre);
		dao.update(WechatChannelNext);
	}

	@Override
	public void updateSortDown(Integer id) {
		String hqlPre = "from WechatChannel where  isDelete=0 and id = ?";
		WechatChannel WechatChannelPre = dao.findByHQLOne(hqlPre, new Object[] { id });
		Integer WechatChannelPreSort = WechatChannelPre.getSort();
		String hqlNext = "from WechatChannel where sort < (select sort from WechatChannel where id= ? ) and isDelete=0 and status =1 order by sort desc";
		WechatChannel WechatChannelNext = dao.findByHQLOne(hqlNext, new Object[] { id });
		Integer WechatChannelNextSort = WechatChannelNext.getSort();

		WechatChannelPre.setSort(WechatChannelNextSort);
		WechatChannelNext.setSort(WechatChannelPreSort);

		dao.update(WechatChannelPre);
		dao.update(WechatChannelNext);

	}

	@Override
	public void deletes(String[] ids) {
		for (String id : ids) {
			String hqlPre = "from WechatChannel where isDelete=0 and id = ?";
			WechatChannel WechatChannel = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (WechatChannel != null) {
				WechatChannel.setDelete(true);
				dao.update(WechatChannel);
			}
		}
	}

	public List<WechatChannel> findByName(String name) {
		List<WechatChannel> WechatChannels = dao.findEntitiesByProperty(WechatChannel.class, "gradeName",
				name);
		return WechatChannels;
	}

	@Override
	public void updateBrokerage(String ids, String brokerage) {
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			String hqlPre = "from WechatChannel where isDelete=0 and id = ?";
			WechatChannel WechatChannel = dao.findByHQLOne(hqlPre,
					new Object[] { Integer.valueOf(id) });
			if (WechatChannel != null) {
				WechatChannel.setCreateTime(new Date());
				dao.update(WechatChannel);
			}
		}
		
	}
	
	@Override
	public List<WechatChannelVo> findWechatChannelList(){
		return wechatChannelDao.findWechatChannelList();
	}

}
