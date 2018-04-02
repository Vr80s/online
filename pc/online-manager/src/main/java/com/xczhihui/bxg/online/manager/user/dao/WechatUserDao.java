package com.xczhihui.bxg.online.manager.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.common.domain.WechatUser;
import com.xczhihui.bxg.online.manager.common.dao.HibernateDao;

/**
 * 网站用户
 *
 * @author Haicheng Jiang
 */
@Repository("WechatUserDao")
public class WechatUserDao extends HibernateDao<WechatUser> {
    /**
     * 查询用户信息。
     *
     * @param lastLoginIp
     * @param createTimeStart
     * @param createTimeEnd
     * @param lastLoginTimeStart
     * @param lastLoginTimeEnd
     * @param searchName
     * @param status
     * @param pageNumber
     * @param pageSize
     * @return
     */
    public Page<WechatUser> findUserPage(String lastLoginIp, String createTimeStart, String createTimeEnd,
    		 int pageNumber,int pageSize) {
    	
    	
        String sql = "select wcu.subscribe,wcu.nickname,wcu.sex,wcu.city,wcu.country,wcu.province,"
        		+ "wcu.language,wcu.headimgurl,wcu.subscribe_time as subscribeTime, "
        		+ "wcu.create_time as createTime,wcu.last_update_time as lastUpdateTime,"
        		+ "owc.name as channelName,ou.login_name as loginName"
                + " from wxcp_client_user_wx_mapping wcu "
                + " left join oe_user ou on wcu.client_id = ou.id "
                + " left join oe_wechat_channel owc on wcu.qr_scene = owc.id "
                + " where 1=1 ";

        Map<String, Object> paramMap = new HashMap<String, Object>();
 
//        if (StringUtils.hasText(createTimeStart)) {
//            sql += (" and u.create_time >= :createTimeStart and u.create_time <= :createTimeEnd ");
//            paramMap.put("createTimeStart", createTimeStart + " 00:00:00");
//            paramMap.put("createTimeEnd", createTimeStart + " 23:59:59");
//        }
//        if (StringUtils.hasText(lastLoginTimeStart)) {
//            sql += (" and u.last_login_date >= :lastLoginTimeStart and u.last_login_date <= :lastLoginTimeEnd ");
//            paramMap.put("lastLoginTimeStart", lastLoginTimeStart + " 00:00:00");
//            paramMap.put("lastLoginTimeEnd", lastLoginTimeStart + " 23:59:59");
//        }
//        if (StringUtils.hasText(searchName)) {
//            sql += " and (u.name like :searchName or u.login_name like :searchName or o.real_name like :searchName or o.mobile like :searchName or o.qq like :searchName or o.email like :searchName ) ";
//            paramMap.put("searchName", "%" + searchName.trim() + "%");
//        }
//        sql += " order by u.create_time desc";
        
        Page<WechatUser> pg = this.findPageBySQL(sql, paramMap, WechatUser.class, pageNumber, pageSize);

        return pg;
    }

    /**
     * 查找所有的讲师
     * Description：
     *
     * @return List<Map<String,Object>>
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public List<Map<String, Object>> getAllUserLecturer() {
        // TODO Auto-generated method stub
        String sql = "SELECT \n" +
                "  ou.id,\n" +
                "  ou.name,\n" +
                "  ou.login_name AS logo \n" +
                "FROM\n" +
                "  oe_user ou\n" +
                "  JOIN `course_anchor` ca\n" +
                "  ON ou.id=ca.`user_id`\n" +
                "WHERE is_lecturer = 1 \n" +
                "  AND ca.status = 1 ";
//		String sql = "select id,name,ifnull(mobile,email) as logo from oe_user where is_lecturer = 1";
        List<Map<String, Object>> list = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql);
        return list;
    }

    /**
     * Description：得到当前最大房间号,默认从10000开始
     *
     * @return int
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public int getCurrent() {
        String sql = "select max(room_number) from oe_user";
        int count = super.queryForInt(sql, null);
        if (count == 0) {
            //说明存在
            count = 10000;
        } else {
            count = count + 1;
        }
        return count;
    }

    public WechatUser getWechatUserByUserId(String userId) {
        return this.find(userId);
    }

    public List<Map<String, Object>> getAllCourseName() {
        String sql = "SELECT c.id,c.grade_name AS courseName,ou.name AS name FROM oe_course AS c,oe_user AS ou  WHERE c.user_lecturer_id = ou.id AND c.is_delete=0 AND c.status = 1  AND ou.status =0";
        List<Map<String, Object>> list = this.getNamedParameterJdbcTemplate().getJdbcOperations().queryForList(sql);
        return list;
    }
}
