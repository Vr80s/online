package com.xczhihui.bxg.online.web.dao;

import com.xczhihui.common.support.dao.SimpleHibernateDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.Focus;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @create 2017-08-20 13:06
 **/
@Repository
public class FocusDao extends SimpleHibernateDao {

    public Page<Focus> findMyFocus(String userId, Integer number, Integer pageSize){

        StringBuffer sql = new StringBuffer("");
        sql.append(" select lecturer_id as lecturerId,lecturer_name as lecturerName,"
                + "lecturer_head_img as lecturerHeadImg, ");
        sql.append(" (select count(*) from oe_focus where lecturer_id = lecturerId) as fansCount,");  //粉丝总数
        sql.append(" (select if(count(*) =0,1,2) from oe_focus where user_id = lecturerId and lecturer_id =:userId ) as isFocus");
        sql.append(" from oe_focus as of                              ");
        sql.append(" where of.user_id = :userId                           ");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId",userId);
        Page<Focus> page = this.findPageBySQL(sql.toString(), paramMap, Focus.class, number, pageSize);
        return page;
    }

    /***
     * 我的粉丝
     * @return
     * @throws SQLException
     */
    public Page<Focus> findMyFans(String userId, Integer pageNumber, Integer pageSize){
        StringBuffer sql = new StringBuffer("");

        sql.append(" select user_id as lecturerId,user_name as lecturerName,user_head_img as lecturerHeadImg,");
        sql.append(" (select count(*) from oe_focus where lecturer_id = lecturerId) as fansCount,"); //粉丝数
        sql.append(" (select if(count(*)=0,0,2)  from oe_focus where lecturer_id = lecturerId and user_id = :userId) as isFocus");//咱俩是否关注了
        sql.append(" from oe_focus as of                              ");
        sql.append(" where of.lecturer_id =:userId                              ");

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId",userId);
        Page<Focus> page = this.findPageBySQL(sql.toString(), paramMap, Focus.class, pageNumber, pageSize);
        return page;
    }


}
