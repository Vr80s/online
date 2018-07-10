package com.xczhihui.user.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.domain.MedicalDoctorAccount;
import com.xczhihui.bxg.online.common.domain.MedicalHospitalAccount;
import com.xczhihui.bxg.online.common.domain.OnlineUser;
import com.xczhihui.common.util.EmailUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.user.dao.OnlineUserDao;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.vhall.VhallUtil;

@Service("onlineUserService")
public class OnlineUserServiceImpl implements OnlineUserService {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OnlineUserDao dao;

    @Override
    public Page<OnlineUser> findUserPage(String lastLoginIp,
                                         String createTimeStart, String createTimeEnd,
                                         String lastLoginTimeStart, String lastLoginTimeEnd,
                                         String searchName, Integer status, Integer lstatus, int pageNumber,
                                         int pageSize) {
        return dao.findUserPage(lastLoginIp, createTimeStart, createTimeEnd,
                lastLoginTimeStart, lastLoginTimeEnd, searchName, status,
                lstatus, pageNumber, pageSize);
    }

    @Override
    public void updateUserStatus(String loginName, int status) {
        OnlineUser u = dao.findOneEntitiyByProperty(OnlineUser.class,
                "loginName", loginName);
        if (u != null) {
            u.setStatus(status);
            dao.update(u);
        }
    }

    @Override
    public void updateMenuForUser(OnlineUser entity) {
        OnlineUser temp = dao.findOneEntitiyByProperty(OnlineUser.class, "id",
                entity.getId());
        temp.setMenuId(entity.getMenuId());
        dao.update(temp);

    }

    // 查询所有的讲师
    @Override
    public List<Map<String, Object>> getAllUserLecturer() {
        return dao.getAllUserLecturer();
    }

    @Override
    public void updateUserLecturer(String userId, int isLecturer,
                                   String description) {
        OnlineUser u = dao.findOneEntitiyByProperty(OnlineUser.class, "id",
                userId);
        // Integer ylisLecturer = u.getIsLecturer();
        /**
         * 获取房间号
         */
        u.setIsLecturer(isLecturer);
        u.setDescription(description);
        Integer roomNumber = u.getRoomNumber();

        // if((isLecturer == 1 && ylisLecturer ==0) && (null==u.getRoomNumber()
        // || 0==u.getRoomNumber())){//如果是取消职位或者已经存在这个职位了，房间号就不应该变了
        // u.setRoomNumber(count);
        // }
        if (isLecturer == 1 && (roomNumber == null || roomNumber == 0)) {
            roomNumber = dao.getCurrent();
            u.setRoomNumber(roomNumber);
        }
        dao.update(u);
        VhallUtil.changeUserPower(u.getVhallId(), isLecturer + "", "0");// 设置讲师权限
    }

    @Override
    public OnlineUser getOnlineUserByUserId(String userId) {
        OnlineUser ou = dao.getOnlineUserByUserId(userId);
        return ou;
    }

    @Override
    public OnlineUser getUserByLoginName(String loginName) {
        return dao.findOneEntitiyByProperty(OnlineUser.class, "loginName", loginName);
    }

    @Override
    public List<Map<String, Object>> getAllCourseName() {
        return dao.getAllCourseName();
    }

    @Override
    public void updateUserLogin(String userId, String loginName) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(loginName)) {
            throw new RuntimeException("参数不全啊，小伙子");
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = df.format(new Date());
        String newLoginName = loginName + "-" + date;

        // 更新用户表中的密码
        OnlineUser o = getOnlineUserByUserId(userId);
        if (o == null) {
            throw new RuntimeException("用户id你蒙的？");
        }
        if (!o.getLoginName().equals(loginName)) {
            throw new RuntimeException("用户id与手机号不对应");
        }
        o.setLoginName(newLoginName);
        dao.update(o);

        /**
         * 删除第三方信息
         */
        String sql_wx = "DELETE FROM wxcp_client_user_wx_mapping WHERE client_id = ?";
        dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql_wx, new Object[]{userId});

        String sql_wb = "DELETE FROM weibo_client_user_mapping WHERE user_id = ?";
        dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql_wb, new Object[]{userId});

        String sql_qq = "DELETE FROM qq_client_user_mapping WHERE user_id = ?";
        dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql_qq, new Object[]{userId});
//
//        /**
//         * 删除医馆和医师信息
//         */
//        String medical_doctor_account = "select * from medical_doctor_account where account_id = '" + userId + "'";
//        List<MedicalDoctorAccount> mda = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
//                .query(medical_doctor_account, new BeanPropertyRowMapper<MedicalDoctorAccount>(MedicalDoctorAccount.class));
//
//        if (mda != null && mda.size() > 0) {
//            String sql_ys = "DELETE FROM medical_doctor WHERE id = ?";
//            MedicalDoctorAccount mdad = mda.get(0);
//            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql_ys, new Object[]{mdad.getDoctorId()});
//        }
//        String medical_hospital_account = "select * from medical_hospital_account where account_id = '" + userId + "'";
//        List<MedicalHospitalAccount> mha = dao.getNamedParameterJdbcTemplate().getJdbcOperations()
//                .query(medical_hospital_account, new BeanPropertyRowMapper<MedicalHospitalAccount>(MedicalHospitalAccount.class));
//        if (mha != null && mha.size() > 0) {
//            String sql_yg = "DELETE FROM medical_hospital WHERE id = ?";
//            MedicalHospitalAccount mha_d = mha.get(0);
//            dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql_yg, new Object[]{mha_d.getDoctorId()});
//        }
//        /**
//         * 删除主播信息
//         */
//        String sql_zb = "DELETE FROM course_anchor WHERE user_id = ?";
//        dao.getNamedParameterJdbcTemplate().getJdbcOperations().update(sql_zb, new Object[]{userId});
//
//        try {
//            EmailUtil.modifyLoginNameMailBySSL("原账号" + loginName + "==>"
//                    + newLoginName);
//        } catch (Exception e) {
//            logger.error("发送modifyUser邮件失败");
//        }
    }

}
