package com.xczhihui.headline.service.impl;

/**
 * Created by admin on 2016/8/29.
 */

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.xczhihui.headline.service.BxsTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.stereotype.Service;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.headline.dao.BxsTagDao;
import com.xczhihui.headline.vo.TagVo;
import com.xczhihui.support.shiro.ManagerUserUtil;

/**
 * 标签管理层的实现类
 *
 * @author 王高伟
 * @create 2016-10-16 16:41:27
 */
@Service
public class BxsTagServiceImpl extends OnlineBaseServiceImpl implements
		BxsTagService {

	@Autowired
	private BxsTagDao tagDao;

	@Override
	public List<TagVo> findTagVo(TagVo tagVo) {
		return null;// tagDao.findTag(tagVo);
	}

	@Override
	public Page<TagVo> findPage(TagVo searchVo, int currentPage, int pageSize) {
		// TODO Auto-generated method stub
		Page<TagVo> page = tagDao.findTagPage(searchVo, currentPage, pageSize);
		return page;

	}

	@Override
	public String addTag(TagVo tagVo) {
		if (tagVo.getName() == null || tagVo.getName() == "") {
			return "标签名称不能为空！";
		}

		String[] nameArray = tagVo.getName().split(",");
		Set<String> comp = new HashSet<String>();

		for (int i = 0; i < nameArray.length; i++) {
			// 判断单个标签不能超过10个字符
			if (nameArray[i].length() > 20) {
				return ("单个标签不能超过20个字符！");
			}
			comp.add(nameArray[i].trim());
		}

		if (nameArray.length != comp.size()) {
			return "标签名称重复！";
		}

		String sql = "INSERT INTO `oe_bxs_tag` (`id`, `name`, `create_person`, `create_time`, `is_delete`, `status`) VALUES (:id, :name, :createPerson, :createTime, 0, :status)";

		for (int i = 0; i < nameArray.length; i++) {
			TagVo bxsTag = new TagVo();
			int hasTagName = tagDao.queryForInt(
					"select count(1) from oe_bxs_tag obt where obt.name = ?",
					new Object[] { nameArray[i].trim() });

			if (hasTagName > 0) {
				return "标签已存在！";
			}

			if ("".equals(nameArray[i].trim())) {
				return "标签名称不能为空！";
			}
			bxsTag.setId(UUID.randomUUID().toString().replace("-", ""));
			bxsTag.setName(nameArray[i].trim());
			bxsTag.setCreatePerson(ManagerUserUtil.getUsername()); // 当前登录人
			bxsTag.setCreateTime(new Date()); // 当前时间
			bxsTag.setStatus(0);

			tagDao.getNamedParameterJdbcTemplate().update(sql,
					new BeanPropertySqlParameterSource(bxsTag));
		}

		return "新增成功！";
	}

	@Override
	public String deletes(String[] ids) {
		TagVo tagVo = new TagVo();
		String msg = "";
		for (String id : ids) {
			tagVo.setId(id);
			tagDao.getNamedParameterJdbcTemplate().update(
					"delete from oe_bxs_tag where id = :id",
					new BeanPropertySqlParameterSource(tagVo));
		}
		return msg;
	}

	@Override
	public void updateStatus(String id) {
		TagVo tagVo = new TagVo();
		tagVo.setId(id);
		String sql = "update oe_bxs_tag set  `status` = abs(`status` - 1) where id = :id ";
		tagDao.getNamedParameterJdbcTemplate().update(sql,
				new BeanPropertySqlParameterSource(tagVo));
	}

	@Override
	public void updateTag(TagVo tagVo) {
		String sql = "select count(1) from oe_bxs_tag t where t.id <> ? and t.`name` = ? ";

		if (tagVo.getName().length() > 20) {
			throw new IllegalArgumentException("单个标签不能超过20个字符！");
		}

		if (tagDao.queryForInt(sql,
				new Object[] { tagVo.getId(), tagVo.getName() }) > 0) {
			throw new IllegalArgumentException("标签名字重复！");
		}

		sql = "update oe_bxs_tag set `name` = :name where id = :id ";
		tagDao.getNamedParameterJdbcTemplate().update(sql,
				new BeanPropertySqlParameterSource(tagVo));
	}

}
