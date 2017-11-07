package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.web.vo.InfomationVo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 动态资讯相关
 *
 * @author Haicheng Jiang
 */
@Repository
public class InfomationDao extends SimpleHibernateDao {

    public List<InfomationVo> list(Integer sum) {
        if (sum == null || sum == 0) {
            sum = 8;
        }
        String sql = "select * from oe_information where status=?  and is_delete =0 order by sort desc limit 0,?";
        return this.getNamedParameterJdbcTemplate().getJdbcOperations().query(sql,
                BeanPropertyRowMapper.newInstance(InfomationVo.class), 1,sum);
    }
    public void updateClickCount(Integer id) {
        String sql = "update oe_information set click_count=(if (click_count is null,1,click_count+1)) where id=?";
        this.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql,id);
    }

}
