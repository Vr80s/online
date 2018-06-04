package com.xczhihui.medical.dao;

import com.xczhihui.bxg.online.common.domain.MedicalEnrollmentRegulations;
import com.xczhihui.bxg.online.common.domain.MedicalEntryInformation;
import com.xczhihui.common.dao.HibernateDao;
import com.xczhihui.common.util.bean.Page;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 师承管理DAO
 *
 * @author yxd
 */
@Repository
public class MedicalEnrollmentRegulationsDao extends HibernateDao<MedicalEnrollmentRegulations> {

    /**
     * Description： 师承分页列表
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/22 13:56
     **/
    public Page<MedicalEnrollmentRegulations> findEnrollmentRegulationsPage(
            MedicalEnrollmentRegulations medicalEnrollmentRegulations, int pageNumber, int pageSize) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        StringBuilder sql = new StringBuilder("SELECT mer.id as id,mer.doctor_id as doctorId," +
                "mer.title as title," +
                "mer.cover_img as coverImg," +
                "mer.propaganda as propaganda, " +
                " mer.doctor_introduction as doctorIntroduction," +
                "mer.tuition as tuition," +
                "mer.count_limit as countLimit," +
                "mer.deadline as deadline," +
                "mer.learning_process as learningProcess,"+
                "mer.start_time as startTime," +
                "mer.end_time as endTime," +
                "mer.study_address as studyAddress," +
                "mer.rights_and_interests as rightsAndInterests,"+
                "mer.qualification as qualification,"+
                "mer.ceremony_address as ceremonyAddress,"+
                "mer.regulations as regulations,"+
                "mer.entry_form_attachment as entryFormAttachment,"+
                "mer.update_time as updateTime,"+
                "mer.updator as updator,"+
                "mer.status as status,"+
                " (select count(*) from medical_entry_information mei where mei.mer_id = mer.id) as countPeople, "+
                " md.name as doctorName " +
                " FROM" +
                " medical_enrollment_regulations mer ,medical_doctor md " +
                " WHERE mer.doctor_id = md.id and mer.deleted = 0 order by mer.status desc,mer.create_time desc");
        Page<MedicalEnrollmentRegulations> list = this.findPageBySQL(sql.toString(),
                paramMap, MedicalEnrollmentRegulations.class, pageNumber, pageSize);
        return list;
    }
    /**
     * Description：通过id进行查找
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/22 13:56
     **/
    public MedicalEnrollmentRegulations findById(Integer id ) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        StringBuilder sql = new StringBuilder("SELECT * " +
                " FROM" +
                " medical_enrollment_regulations mer  " +
                " WHERE mer.id = :id ");
        List<MedicalEnrollmentRegulations> list = this.getNamedParameterJdbcTemplate().query(sql.toString(),
                paramMap, BeanPropertyRowMapper.newInstance(MedicalEnrollmentRegulations.class));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * Description：查看详情
     * creed: Talk is cheap,show me the code
     * @author name：wangyishuai <br>email: wangyishuai@ixincheng.com
     * @Date: 2018/5/22 13:56
     **/
    public MedicalEnrollmentRegulations enrollmentRegulationsDetail(Integer id ) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", id);
        StringBuilder sql = new StringBuilder("SELECT mer.id as id,mer.doctor_id as doctorId," +
                "mer.title as title," +
                "mer.cover_img as coverImg," +
                "mer.propaganda as propaganda, " +
                " mer.doctor_introduction as doctorIntroduction," +
                "mer.tuition as tuition," +
                "mer.count_limit as countLimit," +
                "mer.deadline as deadline," +
                "mer.learning_process as learningProcess,"+
                "mer.start_time as startTime," +
                "mer.end_time as endTime," +
                "mer.study_address as studyAddress," +
                "mer.rights_and_interests as rightsAndInterests,"+
                "mer.qualification as qualification,"+
                "mer.ceremony_address as ceremonyAddress,"+
                "mer.regulations as regulations,"+
                "mer.entry_form_attachment as entryFormAttachment,"+
                "mer.create_time as createTime,"+
                "mer.creator as creator,"+
                "mer.update_time as updateTime,"+
                "mer.updator as updator,"+
                "mer.status as status,"+
                "mer.poster_img as posterImg,"+
                "mer.contact_way as contactWay,"+
                " (select count(*) from medical_entry_information mei where mei.mer_id = mer.id) as countPeople, "+
                " md.name as doctorName " +
                " FROM" +
                " medical_enrollment_regulations mer ,medical_doctor md " +
                " WHERE mer.doctor_id = md.id and mer.deleted = 0 and mer.id = :id ");
        List<MedicalEnrollmentRegulations> list = this.getNamedParameterJdbcTemplate().query(sql.toString(),
                paramMap, BeanPropertyRowMapper.newInstance(MedicalEnrollmentRegulations.class));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

}
