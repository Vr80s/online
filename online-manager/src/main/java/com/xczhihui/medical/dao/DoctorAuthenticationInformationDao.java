package com.xczhihui.medical.dao;

import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.common.domain.MedicalDoctorAuthenticationInformation;
import com.xczhihui.common.dao.HibernateDao;

/**
 * 医师认证信息Dao
 *
 * @author zhuwenbao
 */
@Repository
public class DoctorAuthenticationInformationDao extends
        HibernateDao<MedicalDoctorAuthenticationInformation> {
}
