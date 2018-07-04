package com.xczhihui.medical.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.medical.service.DoctorPostsCommentService;
import com.xczhihui.medical.vo.DoctorPostsCommentVO;

/**
 * @author hejiwei
 */
@Service
public class DoctorPostsCommentServiceImpl implements DoctorPostsCommentService {

    @Autowired
    private SimpleHibernateDao simpleHibernateDao;

    @Override
    public Page<DoctorPostsCommentVO> list(String keyword, int page, int pageSize) {
        String sql = "select ou.name as nickname,  mdpc.content, mdpc.create_time as createTime, md.name as doctorName, mdpc.id as id " +
                " from medical_doctor_posts_comment mdpc , oe_user ou, medical_doctor_posts mdp , `medical_doctor` md " +
                " where mdpc.`user_id` = ou.id and mdpc.`posts_id` = mdp.id and mdp.`doctor_id` = md.id and mdpc.deleted = false";
        Map<String, Object> params = new HashMap<>(1);
        if (StringUtils.isNotBlank(keyword)) {
            keyword =  "%" + keyword + "%";
            params.put("keyword", keyword);
            sql = sql + " and md.name like :keyword";
        }
        return simpleHibernateDao.findPageBySQL(sql, params, DoctorPostsCommentVO.class, page, pageSize);
    }

    @Override
    public void deleteByIds(List<Integer> ids) {
        String sql = "update medical_doctor_posts_comment set deleted = true where id in (:ids)";
        Map<String, Object> params = new HashMap<>(1);
        params.put("ids", ids);
        simpleHibernateDao.getNamedParameterJdbcTemplate().update(sql, params);
    }
}
