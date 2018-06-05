package com.xczhihui.course.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.course.vo.CriticizeVo;

/**
 * @author hejiwei
 */
@Repository
public class CriticizeDao extends SimpleHibernateDao {

    public Page<CriticizeVo> list(String keyword, int page, int size) {
        keyword = StringUtils.isNotBlank(keyword) ? "%" + keyword.trim() : null;
        String sql = "select cc.id, oc.grade_name as courseName, cc.content, ca.name as anchorName," +
                " ou.name as createPerson, cc.create_time as createTime, cc.criticize_lable as label" +
                " from `oe_criticize` cc" +
                " left join oe_course oc on cc.`course_id` = oc.id " +
                " left join course_anchor ca on ca.`user_id` = cc.`user_id`" +
                " left join oe_user ou on cc.`create_person` = ou.id" +
                " where cc.is_delete = 0";
        Map<String, Object> params = new HashMap<>(1);
        if (keyword != null) {
            sql = sql + " and (oc.grade_name like :keyword OR ca.name like :keyword)";
            params.put("keyword", keyword);
        }
        String orderSql = " order by cc.create_time desc";
        return this.findPageBySQL(sql + orderSql, params, CriticizeVo.class, page, size);
    }

    public void deleteByIds(List<String> ids) {
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("ids", ids);
        this.getNamedParameterJdbcTemplate().update("update oe_criticize set is_delete = 1 where id in (:ids)", mapSqlParameterSource);
    }
}
