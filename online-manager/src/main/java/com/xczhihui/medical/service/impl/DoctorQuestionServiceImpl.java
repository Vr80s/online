package com.xczhihui.medical.service.impl;

import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorQuestion;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.medical.service.DoctorQuestionService;
import com.xczhihui.medical.vo.DoctorQuestionVO;

/**
 * MenuServiceImpl:菜单业务层接口实现类
 *
 * @author Rongcai Kang
 */
@Service
public class DoctorQuestionServiceImpl extends OnlineBaseServiceImpl implements
            DoctorQuestionService {

    @Override
    public Page<MedicalDoctorQuestion> selectQuestion(DoctorQuestionVO searchVo, int currentPage, int pageSize) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public MedicalDoctorQuestion findMedicalDoctorQuestionById(Integer id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateMedicalDoctorQuestion(MedicalDoctorQuestion old) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteMedicalDoctorQuestionById(Integer id) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deletes(String[] _ids) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateStatus(Integer id) {
        // TODO Auto-generated method stub
        
    }


}
