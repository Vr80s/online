package com.xczhihui.medical.constitution.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xczhihui.medical.constitution.model.MedicalConstitutionProposal;
import com.xczhihui.medical.constitution.vo.ConstitutionScore;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author yuxin
 * @since 2018-10-15
 */
public interface MedicalConstitutionProposalMapper extends BaseMapper<MedicalConstitutionProposal> {

    @Select("SELECT * from medical_constitution_proposal mcp where mcp.constitution_id = #{constitutionId}")
    MedicalConstitutionProposal selectByConstitutionId(Integer constitutionId);
}