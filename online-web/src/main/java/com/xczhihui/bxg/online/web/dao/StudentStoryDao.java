package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.online.common.domain.StudentStory;
import com.xczhihui.bxg.online.web.base.common.Constant;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 学员故事
 * @author majian
 * @date 2016-8-18 14:30:56
 */
@Repository
public class StudentStoryDao extends SimpleHibernateDao {


    /**
     * 查询首页的学员故事
     * @return
     */
    public List<StudentStory> findListByIndex(){
        DetachedCriteria dc = DetachedCriteria.forClass(StudentStory.class);
        dc.add(Restrictions.eq("isDelete", Constant._NOT_DELETED));
        dc.addOrder(Order.desc("createTime"));
        return this.findEntities(dc);
    }

}
