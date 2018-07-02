package com.xczhihui.bxg.online.web.dao;/**
 * Created by admin on 2016/9/19.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.web.vo.AskTagVo;
import com.xczhihui.common.support.dao.SimpleHibernateDao;

/**
 * 标签底层实现类
 *
 * @author 康荣彩
 * @create 2016-09-19 17:00
 */
@Repository
public class AskTagDao extends SimpleHibernateDao {


    /**
     * 根据学科ID号查找对应的标签
     *
     * @param menuId
     * @return
     */
    public List<AskTagVo> getTagsByMenuId(Integer menuId) {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = "select t.name,t.id from oe_ask_tag as t  where t.is_delete = 0 and t.status=1  and t.menu_id = :menuId  ORDER BY t.seq  desc";
        params.put("menuId", menuId);
        return this.getNamedParameterJdbcTemplate().query(sql, params, new BeanPropertyRowMapper<AskTagVo>(AskTagVo.class));
    }
}
