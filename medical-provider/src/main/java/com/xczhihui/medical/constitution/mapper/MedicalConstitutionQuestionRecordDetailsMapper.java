package com.xczhihui.medical.constitution.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.constitution.model.MedicalConstitutionQuestionRecordDetails;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-10-15
 */
public interface MedicalConstitutionQuestionRecordDetailsMapper extends BaseMapper<MedicalConstitutionQuestionRecordDetails> {

    @Insert("<script>" +
            "insert into medical_constitution_question_record_details(question_id, question_no, answer, record_id, create_time) "
            + "values "
            + "<foreach collection =\"medicalQuestionRecordDetailsList\" item=\"medicalQuestionRecordDetails\" index= \"index\" separator =\",\"> "
            + "(#{medicalQuestionRecordDetails.questionId},#{medicalQuestionRecordDetails.questionNo},#{medicalQuestionRecordDetails.answer},#{medicalQuestionRecordDetails.recordId},#{medicalQuestionRecordDetails.createTime}) "
            + "</foreach > "
            + "</script>")
    void insertBatch(@Param("medicalQuestionRecordDetailsList") List<MedicalConstitutionQuestionRecordDetails> medicalQuestionRecordDetailsList);

    @Select("select * from medical_constitution_question_record_details mcqrd where mcqrd.record_id = #{recordId}")
    List<MedicalConstitutionQuestionRecordDetails> selectByRecordId(Integer recordId);
}