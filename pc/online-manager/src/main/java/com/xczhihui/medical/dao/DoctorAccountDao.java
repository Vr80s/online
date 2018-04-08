package com.xczhihui.medical.dao;

import com.xczhihui.bxg.online.common.domain.MedicalDoctorAccount;
import com.xczhihui.common.dao.HibernateDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医师-用户对应关系DAO
 * @author zhuwenbao
 */
@Repository
public class DoctorAccountDao extends HibernateDao<MedicalDoctorAccount> {

    /**
     * 根据用户id获取其对应的doctorId
     * @param userId 用户id
     * @return
     */
    public MedicalDoctorAccount findByAccountId(String userId) {

        String hql = "select id,doctor_id,account_id from medical_doctor_account where account_id=:accountId";
        Map<String,Object> paramsMap = new HashMap();
        paramsMap.put("accountId",userId);
        List<MedicalDoctorAccount> result = this.getNamedParameterJdbcTemplate().query(hql, paramsMap, BeanPropertyRowMapper.newInstance(MedicalDoctorAccount.class));
        if(result.size()>0){
            return result.get(0);
        }
        return null;
    }
}
