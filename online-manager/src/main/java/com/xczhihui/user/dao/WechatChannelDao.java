package com.xczhihui.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Course;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.user.vo.WechatChannelVo;

/**
 * 云课堂课程管理DAO
 * 
 * @author yxd
 *
 */
@Repository("wechatChannelDao")
public class WechatChannelDao extends HibernateDao<Course> {

	public Page<WechatChannelVo> findWechatChannelPage(
			WechatChannelVo WechatChannelVo, int pageNumber, int pageSize) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		
		StringBuilder sql = new StringBuilder(
				"	SELECT og.id,og.name,u.`name` createPerson,og.`create_time` createTime,og.sort,"
						+ " og.status as status,og.contact,og.mobile,og.city,og.province,og.area,"
						+ " og.city_id as realCitys,og.province_id as realProvince,og.area_id as realCounty,"
						+ " og.qr_code_img as qrCodeImg,og.custom_qr_code_url as customQrCodeUrl,og.custom_caoliao_qrcode as customCaoliaoQrcode "
						+ "FROM `oe_wechat_channel` "
						+ " og LEFT JOIN `user` u ON u.id = og.`create_person` WHERE og.`is_delete` = 0");

		if (WechatChannelVo.getStatus() != null) {
			paramMap.put("status", WechatChannelVo.getStatus());
			sql.append(" and og.status = :status ");
		}
		
		if (StringUtils.isNotBlank(WechatChannelVo.getContact())) { //联系人，联系电话
			paramMap.put("keyWord", "%" + WechatChannelVo.getContact()+ "%");
			sql.append(" and ( og.contact like :keyWord  or og.mobile like :keyWord  or og.name like :keyWord  ) ");
		}
		
		sql.append(" order by og.status desc, og.sort desc");
		Page<WechatChannelVo> WechatChannelVos = this.findPageBySQL(
				sql.toString(), paramMap, WechatChannelVo.class, pageNumber,
				pageSize);
		return WechatChannelVos;
	}

	public void deleteById(Integer id) {
		String sql = "UPDATE `oe_wechat_channel` SET is_delete = 1 WHERE  id = :id";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		this.getNamedParameterJdbcTemplate().update(sql, params);
	}

	public List<WechatChannelVo> findWechatChannelList() {

		StringBuilder sql = new StringBuilder(
				"	SELECT og.id,og.`create_time` createTime,og.sort,og.name,"
						+ " og.status as status,og.contact,og.mobile,og.city,og.province,og.area,"
						+ " og.city_id as cityId,og.province_id as provinceId,og.area_id as areaId "
						+ "FROM `oe_wechat_channel` as og WHERE og.`is_delete` = 0");
		Page<WechatChannelVo> page = this.findPageBySQL(sql.toString(), null,
				WechatChannelVo.class, 0, Integer.MAX_VALUE);
		return page.getItems();
	}

}
