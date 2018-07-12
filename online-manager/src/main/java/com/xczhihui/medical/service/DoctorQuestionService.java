package com.xczhihui.medical.service;

import com.xczhihui.bxg.online.common.domain.MedicalDoctorQuestion;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.medical.vo.DoctorQuestionVO;

/**
 * MenuService:菜单业务层接口类 * @author Rongcai Kang
 */
public interface DoctorQuestionService {

    Page<MedicalDoctorQuestion> selectQuestion(DoctorQuestionVO searchVo, int currentPage, int pageSize);

    MedicalDoctorQuestion findMedicalDoctorQuestionById(Integer id);

    void updateMedicalDoctorQuestion(MedicalDoctorQuestion old);

    void deleteMedicalDoctorQuestionById(Integer id);

    void deletes(String[] _ids);

    void updateStatus(Integer id);
  
}
