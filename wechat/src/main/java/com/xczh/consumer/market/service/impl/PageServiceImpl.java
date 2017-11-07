package com.xczh.consumer.market.service.impl;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.dao.BasicSimpleDao;
import com.xczh.consumer.market.service.PageService;
import com.xczh.consumer.market.utils.JdbcUtil;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 页面跳转相关
 *
 * @author Alex Wang
 */

@Service
public class PageServiceImpl implements PageService {
	@Autowired
	private BasicSimpleDao basicSimpleDao;

	@Override
	public boolean needWriteShareCode(String shareCode,HttpServletRequest req) throws Exception {
		Object loginUser = req.getSession().getAttribute("_user_");
		String sql = "select id from oe_user where share_code = ?";
		List<Map<String, Object>> result =
				basicSimpleDao.query(JdbcUtil.getCurrentConnection(), sql, new MapListHandler(),shareCode);
		//无效的分享码
		if (result.size() <= 0) {
			return false;
		}
		//用户登陆着
		if (loginUser != null) {
			OnlineUser ou = (OnlineUser)loginUser;
			sql = "select share_code,parent_id from oe_user where id = ?";
			Map<String, Object> mp =
					basicSimpleDao.query(JdbcUtil.getCurrentConnection(),sql, new MapHandler(),ou.getId());
			//当前用户没有上级
			if (mp.get("parent_id") == null) {
				return true;
			}
			//分享码与传过来的不一致
			if (mp.get("share_code") != null && !mp.get("share_code").toString().equals(shareCode)) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}
}
