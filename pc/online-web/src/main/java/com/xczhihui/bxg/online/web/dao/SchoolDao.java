package com.xczhihui.bxg.online.web.dao;/**
 * Created by admin on 2016/8/29.
 */

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.web.vo.SchoolVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学校底层实现类
 *
 * @author 康荣彩
 * @create 2016-08-29 18:25
 */
@Repository
public class SchoolDao extends SimpleHibernateDao {

    /**
     * 根据市级编号 查找对应的学校信息
     * @param cityId
     * @return
     */
    public List<SchoolVo> getSchoolList(String cityId) {

        String sql=" select  id, name  from school where city_id =? ";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql, new Object[]{cityId}, BeanPropertyRowMapper.newInstance(SchoolVo.class));
    }
}
