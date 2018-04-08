package com.xczhihui.operate.service.impl;

import com.xczhihui.bxg.common.support.dao.SimpleHibernateDao;
import com.xczhihui.operate.service.MobilePartnerCourseService;
import com.xczhihui.operate.vo.MobilePartnerCourseVo;
import org.apache.commons.collections.FastHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 移动合伙人课程业务实现类
 * @Author Fudong.Sun【】
 * @Date 2017/3/9 20:56
 */
@Service
public class MobilePartnerCourseServiceImpl extends SimpleHibernateDao implements MobilePartnerCourseService {

    @Value("${share.course.id}")
    private Integer courseId;

    @Override
    public MobilePartnerCourseVo findCourseInfoByCourseId() {
        String sql = "select * from oe_course_share where course_id=:course_id";
        Map<String,Object> params = new FastHashMap();
        params.put("course_id",courseId);
        List<MobilePartnerCourseVo> list = this.findEntitiesByJdbc(MobilePartnerCourseVo.class,sql,params);
        return list.size()>0? list.get(0):null;
    }

    @Override
    public void saveOrUpdate(MobilePartnerCourseVo mobilePartnerCourseVo) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", mobilePartnerCourseVo.getId());
        params.addValue("course_id", mobilePartnerCourseVo.getCourse_id());
        params.addValue("img_url", mobilePartnerCourseVo.getImg_url());
        params.addValue("share_desc", mobilePartnerCourseVo.getShare_desc());
        params.addValue("work_flow", mobilePartnerCourseVo.getWork_flow());
        if(mobilePartnerCourseVo.getId()==null) {
            String sql = "insert into oe_course_share (id,course_id,img_url,share_desc,work_flow) values (REPLACE (UUID(), '-', ''),:course_id,:img_url,:share_desc,:work_flow)";
            this.getNamedParameterJdbcTemplate().update(sql, params);
        }else{
            String sql = "update oe_course_share set course_id=:course_id,img_url=:img_url,share_desc=:share_desc,work_flow=:work_flow where id=:id";
            this.getNamedParameterJdbcTemplate().update(sql, params);
        }
    }
}
