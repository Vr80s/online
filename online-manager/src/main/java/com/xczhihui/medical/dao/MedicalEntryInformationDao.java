package com.xczhihui.medical.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.xczhihui.bxg.online.common.domain.MedicalEntryInformation;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;

/**
 * Description：师承-报名
 * creed: Talk is cheap,show me the code
 *
 * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
 * @Date: 2018/5/21 18:00
 **/
@Repository
public class MedicalEntryInformationDao extends HibernateDao<MedicalEntryInformation> {
    public Page<MedicalEntryInformation> findEntryInformationPage(
            MedicalEntryInformation medicalEntryInformation, int pageNumber, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder("SELECT mei.id as id, mei.mer_id as merId,mei.name as name,mei.age as age,mei.sex as sex, " +
                " mei.native_place as nativePlace,mei.tel as tel,mei.create_time createTime,mer.deadline as deadline,mei.education,mei.apprentice as apprentice  " +
                " FROM" +
                " medical_entry_information mei ,medical_enrollment_regulations mer " +
                " WHERE mei.mer_id = mer.id and mei.deleted = 0 ");
        if (medicalEntryInformation.getMerId() != null) {
            paramMap.put("merId", medicalEntryInformation.getMerId());
            sql.append(" and mei.mer_id = :merId ");
        }
        sql.append(" order by mei.create_time desc ");
        Page<MedicalEntryInformation> list = this.findPageBySQL(sql.toString(),
                paramMap, MedicalEntryInformation.class, pageNumber, pageSize);
        return list;
    }

    /**
     * Description：查看详情
     * creed: Talk is cheap,show me the code
     *
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/22 13:56
     **/
    public MedicalEntryInformation entryInformationDetail(Integer id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        StringBuilder sql = new StringBuilder("SELECT * " +
                " FROM" +
                " medical_entry_information mei " +
                " WHERE mei.deleted = 0 and mei.id = :id ");
        List<MedicalEntryInformation> list = this.getNamedParameterJdbcTemplate().query(sql.toString(),
                paramMap, BeanPropertyRowMapper.newInstance(MedicalEntryInformation.class));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


}
