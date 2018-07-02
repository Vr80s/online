package com.xczh.consumer.market.dao;

import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.xczh.consumer.market.bean.OnlineUser;
import com.xczh.consumer.market.utils.JdbcUtil;

/**
 * 用户操作dao
 *
 * @author zhangshixiong
 * @date 2017-02-22
 */
@Repository
public class OnlineUserMapper extends BasicSimpleDao {
    /**
     * 根据登录名查找用户
     *
     * @param loginName
     * @return
     */
    public OnlineUser findUserByLoginName(String loginName) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append(" select id,name,login_name as loginName,password,sex,mobile,email,is_delete as isDelete, visitor, ");
        sql.append(" small_head_photo as smallHeadPhoto,big_head_photo as bigHeadPhoto,status as status, ");
        sql.append(" create_person as createPerson,create_time as createTime,last_login_date as lastLoginDate, ");
        sql.append(" last_login_ip as lastLoginIp,visit_sum as visitSum,stay_time as stayTime,info,jobyears, ");
        sql.append(" occupation,occupation_other as occupationOther,target,is_apply as isApply,full_address as fullAddress, ");
        sql.append(" menu_id as menuId,union_id as unionId,user_type as userType,ref_id as refId,parent_id as parentId, ");
        sql.append(" share_code as shareCode,change_time as changeTime,origin,type,vhall_id as vhallId,"
                + "vhall_pass as vhallPass,vhall_name as vhallName, ");
        sql.append(" region_id as district,region_area_id as province,region_city_id as city,");
        sql.append(" province_name as provinceName,city_name as cityName,is_lecturer as isLecturer,info as info,");

        sql.append(" (select val from oe_common as common where common.id = occupation) as occupationText ");
        sql.append(" from oe_user where login_name = ? and status = 0  and is_delete =0  ");
        Object params[] = {loginName};
        OnlineUser o = this.query(JdbcUtil.getCurrentConnection(), sql.toString(),
                new BeanHandler<>(OnlineUser.class), params);
        return o;
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public OnlineUser findUserById(String id) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append(" select id,name,login_name as loginName,password,sex,mobile,email,is_delete as isDelete, ");
        sql.append(" small_head_photo as smallHeadPhoto,big_head_photo as bigHeadPhoto,status as status, ");
        sql.append(" create_person as createPerson,create_time as createTime,last_login_date as lastLoginDate, ");
        sql.append(" last_login_ip as lastLoginIp,visit_sum as visitSum,stay_time as stayTime,info,jobyears, ");
        sql.append(" occupation,occupation_other as occupationOther,target,is_apply as isApply,full_address as fullAddress, ");
        sql.append(" menu_id as menuId,union_id as unionId,user_type as userType,ref_id as refId,parent_id as parentId, ");
        sql.append(" share_code as shareCode,change_time as changeTime,origin,type,room_number as roomNumber, ");
        sql.append(" region_id as district,region_area_id as province,region_city_id as city,");
        sql.append(" province_name as provinceName,city_name as cityName,is_lecturer as isLecturer,info as info, ");
        sql.append(" vhall_id as vhallId,vhall_pass as vhallPass,vhall_name as vhallName, ");
        sql.append(" county_name as countyName, ");
        sql.append(" (select val from oe_common as common where common.id = occupation) as occupationText ");
        sql.append(" from oe_user where id = ? and status = 0  and is_delete =0  ");
        Object params[] = {id};
        return this.query(JdbcUtil.getCurrentConnection(), sql.toString(),
                new BeanHandler<>(OnlineUser.class), params);
    }


    /**
     * Description：更新用户中心提交的数据
     *
     * @return void
     * @throws SQLException
     * @author name：yangxuan <br>
     * email: 15936216273@163.com
     */
    public void updateUserCenter(OnlineUser original, Map<String, String> map)
            throws SQLException {
        StringBuilder sb = new StringBuilder("");
        sb.append("update oe_user set ");

        if (StringUtils.hasText(map.get("info"))
                && !map.get("info").equals(original.getName())) {
            sb.append(" info ='" + map.get("info") + "',");
        }
        if (StringUtils.hasText(map.get("nickname"))
                && !map.get("nickname").equals(original.getName())) {
            sb.append(" name ='" + map.get("nickname") + "',");
        }
        if (StringUtils.hasText(map.get("province"))
                && !map.get("province").equals(original.getProvince())) {
            sb.append(" region_area_id = '" + map.get("province") + "',");
        }
        if (StringUtils.hasText(map.get("city"))
                && !map.get("city").equals(original.getCity())) {
            sb.append(" region_city_id = '" + map.get("city") + "',");
        }
        if (StringUtils.hasText(map.get("district"))
                && !map.get("district").equals(original.getCity())) {
            sb.append(" region_id = '" + map.get("district") + "',");
        }
        if (StringUtils.hasText(map.get("provinceName"))
                && !map.get("provinceName").equals(original.getProvince())) {
            sb.append(" province_name = '" + map.get("provinceName") + "',");
        }
        if (StringUtils.hasText(map.get("cityName"))
                && !map.get("cityName").equals(original.getCity())) {
            sb.append(" city_name = '" + map.get("cityName") + "',");
        }
        if (StringUtils.hasText(map.get("countyName"))
                && !map.get("countyName").equals(original.getProvince())) {
            sb.append(" county_name = '" + map.get("countyName") + "',");
        }

        if (StringUtils.hasText(map.get("email"))
                && !map.get("email").equals(original.getEmail())) {
            sb.append(" email = '" + map.get("email") + "',");
        }
        if (StringUtils.hasText(map.get("sex"))
                && Integer.parseInt(map.get("sex")) != original.getSex()) {
            sb.append(" sex =" + Integer.parseInt(map.get("sex")) + ",");
        }
        if (StringUtils.hasText(map.get("smallHeadPhoto"))
                && !map.get("smallHeadPhoto").equals(
                original.getSmallHeadPhoto())) {
            sb.append(" small_head_photo ='" + map.get("smallHeadPhoto") + "',");
        }
        if (StringUtils.hasText(map.get("occupation"))) {
            sb.append(" occupation =" + Integer.parseInt(map.get("occupation")) + ",");
        }
        if (StringUtils.hasText(map.get("occupationOther")) //身份信息
                && !map.get("occupationOther").equals(original.getOccupationOther())) {
            sb.append(" occupation_other ='" + map.get("occupationOther") + "',");
        }

        String sql = sb.toString();
        if (sql.indexOf(",") != -1) {
            sql = sql.substring(0, sb.length() - 1);
            sql += " where id = ? ";
            Object[] params = {original.getId()};
            this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
        }
    }


    /**
     * 更新用户的登录名  -- 手机号
     *
     * @param user
     * @throws SQLException
     */
    public void updateUserLoginName(OnlineUser user) throws SQLException {
        String sql = " update oe_user set login_name = ? where id = ? ";
        Object[] params = {user.getLoginName(), user.getId()};
        this.update(JdbcUtil.getCurrentConnection(), sql.toString(), params);
    }


    /**
     * Description：查找主播的详情    主播详细信息和精彩致辞在course_anchor表中
     *
     * @param lecturerId
     * @return Map<String,Object>
     * @throws SQLException
     * @author name：yangxuan <br>email: 15936216273@163.com
     */
    public Map<String, Object> findHostById(String lecturerId) throws SQLException {
        StringBuffer sql = new StringBuffer();
        sql.append("select ca.user_id,ca.name,ca.profile_photo as small_head_photo,"
                + "ca.video,ca.detail,ca.type as type,"
                + " (select md.work_time from medical_doctor md where id = (select doctor_id from medical_doctor_account as mda  "
                + "    where mda.account_id = ?) )  as workTime  "
                + "  from  oe_user ou inner join course_anchor ca on  ou.id = ca.user_id where ou.id = ?");
        Object params[] = {lecturerId, lecturerId};
        return this.query(JdbcUtil.getCurrentConnection(), sql.toString(), new MapHandler(), params);
    }


    public void updateShareCode(String id, String shareCode) {
        String sql = "update oe_user set share_code = ? where id = ?";
        Object[] params = {shareCode, id};
        try {
            this.update(JdbcUtil.getCurrentConnection(), sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
