package com.xczhihui.medical.dao;

import com.xczhihui.bxg.online.common.domain.MedicalHospitalAccount;
import com.xczhihui.common.dao.HibernateDao;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 医馆-帐号对应关系Dao
 * 
 * @author zhuwenbao
 */
@Repository
public class HospitalAccountDao extends HibernateDao<MedicalHospitalAccount> {

	/**
	 * 通过用户id获取医馆id
	 * 
	 * @param userId
	 *            用户id
	 */
	public MedicalHospitalAccount findByAccountId(String userId) {

		String hql = "select id,doctor_id,account_id from medical_hospital_account where account_id=:accountId";

		Map<String, Object> paramsMap = new HashMap();
		paramsMap.put("accountId", userId);

		List<MedicalHospitalAccount> result = this
				.getNamedParameterJdbcTemplate().query(
						hql,
						paramsMap,
						BeanPropertyRowMapper
								.newInstance(MedicalHospitalAccount.class));

		if (result.size() > 0) {
			return result.get(0);
		}

		return null;

	}
}
