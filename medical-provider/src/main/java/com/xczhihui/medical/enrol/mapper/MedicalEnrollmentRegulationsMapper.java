package com.xczhihui.medical.enrol.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.enrol.model.MedicalEnrollmentRegulations;
import com.xczhihui.medical.enrol.vo.MedicalEnrollmenRtegulationsCardInfoVO;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-05-22
 */
public interface MedicalEnrollmentRegulationsMapper extends BaseMapper<MedicalEnrollmentRegulations> {

    @Select("SELECT mer.`id`,mer.`cover_img`,mer.`title` FROM medical_enrollment_regulations mer " +
            " WHERE mer.`deleted`=0 AND mer.`status`=1 ORDER BY mer.`create_time` DESC ")
    List<MedicalEnrollmentRegulations> selectListByPage(Page<MedicalEnrollmentRegulations> page);

    @Select("SELECT ou.`small_head_photo` FROM `oe_user` ou where ou.id=#{userId}")
    String selectUserPhoto4CardInfo(String userId);

    @Select("SELECT \n" +
            "  mer.`title`,  mer.`propaganda`,\n" +
            "  mer.`doctor_introduction` doctorIntroduction,\n" +
            "  mer.`tuition`,  mer.`count_limit` countLimit,\n" +
            "  mer.`deadline`,  mer.`study_address` studyAddress \n" +
            "FROM  `medical_enrollment_regulations` mer where mer.id = #{id}")
    MedicalEnrollmenRtegulationsCardInfoVO getMedicalEnrollmentRegulationsCardInfoById(Integer id);

    /**
     * 医师的招生简章
     *
     * @param doctorId 医师id
     * @return
     */
    @Select({"SELECT id, title from medical_enrollment_regulations where doctor_id = #{doctorId} and status = 1 and deleted = false"})
    List<MedicalEnrollmentRegulations> listByDoctorId(String doctorId);

    /**
     * 分页获取医师的招生简章
     *
     * @param doctorId doctorId
     * @param page     page
     * @return
     */
    @Select({"select id, title, cover_img as coverImg, tuition, count_limit as countLimit, " +
            " deadline, learning_process as learningProcess, start_time as startTime, end_time as endTime, " +
            " study_address as studyAddress, ceremony_address as ceremonyAddress, poster_img as posterImg, regulations," +
            " entry_form_attachment as entryFormAttachment, create_time as createTime, status" +
            " from medical_enrollment_regulations " +
            " where doctor_id = #{doctorId}" +
            " order by create_time desc"})
    List<MedicalEnrollmentRegulations> listPageByDoctorId(@Param("doctorId") String doctorId, Page<MedicalEnrollmentRegulations> page);

    /**
     * 查询医师上架招生简章
     *
     * @param page 分页参数
     * @return
     */
    @Select("SELECT mer.`id`,mer.`cover_img`,mer.`title` FROM medical_enrollment_regulations mer " +
            " WHERE mer.`deleted`=0 AND mer.`status`=1 ORDER BY mer.`create_time` DESC ")
    List<MedicalEnrollmentRegulations> listOnShelfByDoctorId(Page<MedicalEnrollmentRegulations> page);
}