package com.xczhihui.medical.constitution.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.constitution.model.MedicalConstitutionRecipe;
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
public interface MedicalConstitutionRecipeMapper extends BaseMapper<MedicalConstitutionRecipe> {

    @Select("SELECT * from medical_constitution_recipe mcr where mcr.constitution_id = #{constitutionId}")
    List<MedicalConstitutionRecipe> selectByConstitutionId(Integer constitutionId);
}