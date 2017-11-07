package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.web.vo.MenuVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单dao层
 *
 * @author majian
 * @date 2016-8-29 15:52:31
 */
@Repository
public class MenuDao extends SimpleHibernateDao {


    public List<MenuVo> findUserCourseMenus(String userId) {
        Map<String,Object> params=new HashMap<String,Object>();
        params.put("userId",userId);
        String sql=" select om.id ,om.name,om.sort from oe_menu om left join oe_course oc on om.id=oc.menu_id  left join apply_r_grade_course app on oc.id = app.course_id" +
                   " left join  oe_apply ap on app.apply_id = ap.id  where app.is_delete=0  and om.is_delete=0 and  ap.user_id =:userId group by om.name order by om.sort;";
        return this.getNamedParameterJdbcTemplate().query(sql,params,new BeanPropertyRowMapper<MenuVo>(MenuVo.class));
    }

}
