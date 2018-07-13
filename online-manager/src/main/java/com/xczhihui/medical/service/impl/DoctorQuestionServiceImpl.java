package com.xczhihui.medical.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xczhihui.bxg.online.common.base.service.impl.OnlineBaseServiceImpl;
import com.xczhihui.bxg.online.common.domain.MedicalDepartment;
import com.xczhihui.bxg.online.common.domain.MedicalDoctor;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorQuestion;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.medical.dao.DepartmentDao;
import com.xczhihui.medical.dao.DoctorQuestionDao;
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

    @Autowired
    private DoctorQuestionDao doctorQuestionDao;
    
    @Override
    public Page<MedicalDoctorQuestion> selectQuestion(DoctorQuestionVO searchVo,
            int currentPage, int pageSize) {
        Page<MedicalDoctorQuestion> page = doctorQuestionDao.selectQuestion(
                searchVo, currentPage, pageSize);
        return page;
    }

    @Override
    public MedicalDoctorQuestion findMedicalDoctorQuestionById(Integer id) {
        MedicalDoctorQuestion mdq = dao.findOneEntitiyByProperty(
                MedicalDoctorQuestion.class, "id", id);
        return mdq;
    }

    @Override
    public void updateMedicalDoctorQuestion(MedicalDoctorQuestion old) {
        dao.update(old);
    }

    @Override
    public void deleteMedicalDoctorQuestionById(Integer id) {
        doctorQuestionDao.deleteById(id);
    }

    @Override
    public void deletes(String[] _ids) {
        
        for (String id : _ids) {
            String hqlPre = "from MedicalDoctor where id = ?";
            MedicalDoctor medicalDoctor = dao.findByHQLOne(hqlPre,
                    new Object[]{id});
            if (medicalDoctor != null) {
                medicalDoctor.setDeleted(true);
                dao.update(medicalDoctor);
            }
        }
        
    }

    @Override
    public void updateStatus(Integer id) {
        String hql = "from MedicalDoctor where 1=1 and deleted=0 and id = ?";
        MedicalDoctor MedicalDoctor = dao
                .findByHQLOne(hql, new Object[]{id});
        if (MedicalDoctor.getStatus()) {
            MedicalDoctor.setStatus(false);
        } else {
            MedicalDoctor.setStatus(true);
        }
        dao.update(MedicalDoctor);
    }


}
