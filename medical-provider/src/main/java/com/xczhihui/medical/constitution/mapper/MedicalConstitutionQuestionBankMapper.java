package com.xczhihui.medical.constitution.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.constitution.model.MedicalConstitutionQuestionBank;
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
public interface MedicalConstitutionQuestionBankMapper extends BaseMapper<MedicalConstitutionQuestionBank> {

    @Select("SELECT * from medical_constitution_question_bank ORDER BY id")
    List<MedicalConstitutionQuestionBank> selectAll();
}