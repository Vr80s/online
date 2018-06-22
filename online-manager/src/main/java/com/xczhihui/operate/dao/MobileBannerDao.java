package com.xczhihui.operate.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.operate.vo.MobileBannerVo;

@Repository
public class MobileBannerDao extends SimpleHibernateDao {

    public Page<MobileBannerVo> findMobileBannerPage(
            MobileBannerVo mobileBannerVo, Integer pageNumber, Integer pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder(" SELECT " + "	t.id, "
                + "	t.name, " + "	t.url, " + "	t.click_sum, "
                + "	t.create_person, " + "	t.create_time, " + "	t.`status`, "
                + "	t.seq, " + "	t.img_path, "
                + "	t.banner_type as bannerType, "
                + "	t.link_type as linkType, "
                + "	t.link_condition as linkCondition, t.route_type as routeType, t.link_param as linkParam, "
                + "	t2.name createPersonName " + " FROM "
                + "	oe_course_mobile_banner t, " + "	user t2 "
                + " where t.create_person = t2.login_name ");

        if (mobileBannerVo.getName() != null
                && !"".equals(mobileBannerVo.getName())) {
            sql.append(" and t.name like :name ");
            paramMap.put("name", "%" + mobileBannerVo.getName() + "%");
        }

        if (mobileBannerVo.getStatus() != null
                && !"".equals(mobileBannerVo.getStatus())) {
            sql.append(" and t.status = :status ");
            paramMap.put("status", mobileBannerVo.getStatus());
        }

        if (mobileBannerVo.getBannerType() != null
                && !"".equals(mobileBannerVo.getBannerType())) {
            sql.append(" and t.banner_type = :bannerType ");
            paramMap.put("bannerType", mobileBannerVo.getBannerType());
        }
        sql.append(" order by t.status desc,t.seq desc ");

        System.out.println("mobile:" + sql.toString());

        Page<MobileBannerVo> ms = this.findPageBySQL(sql.toString(), paramMap,
                MobileBannerVo.class, pageNumber, pageSize);
        return ms;
    }

    public void update(String id, String routeType, String linkParam) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("id", id).addValue("routeType", routeType).addValue("linkParam", linkParam);
        this.getNamedParameterJdbcTemplate().update("update oe_course_mobile_banner set route_type = :routeType, link_param = :linkParam where id = :id", mapSqlParameterSource);
    }
}
