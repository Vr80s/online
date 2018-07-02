package com.xczhihui.medical.enrol.service.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.enrol.mapper.MedicalEnrollmentRegulationsMapper;
import com.xczhihui.medical.enrol.mapper.MedicalEntryInformationMapper;
import com.xczhihui.medical.enrol.model.MedicalEnrollmentRegulations;
import com.xczhihui.medical.enrol.model.MedicalEntryInformation;
import com.xczhihui.medical.enrol.service.EnrolService;
import com.xczhihui.medical.enrol.vo.MedicalEnrollmenRtegulationsCardInfoVO;
import com.xczhihui.medical.enrol.vo.MedicalEntryInformationVO;
import com.xczhihui.medical.exception.MedicalException;

/**
 * Description: <br>
 *
 * @author: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time:  2018/5/22 0022-上午 11:26<br>
 */
@Service
public class EnrolServiceImpl implements EnrolService {

    private static Pattern p = Pattern.compile("^(1[3-8])\\d{9}$");
    private static String ENROL_URL = "/xcview/html/apprentice/inherited_introduction.html?merId=";

    @Autowired
    private MedicalEnrollmentRegulationsMapper medicalEnrollmentRegulationsMapper;
    @Autowired
    private MedicalEntryInformationMapper medicalEntryInformationMapper;

    @Override
    public Object getEnrollmentRegulationsList(int page, int size) {
        Page<MedicalEnrollmentRegulations> p = new Page<>(page, size);
        List<MedicalEnrollmentRegulations> medicalEnrollmentRegulations = medicalEnrollmentRegulationsMapper.selectListByPage(p);
        return p.setRecords(medicalEnrollmentRegulations);
    }

    @Override
    public MedicalEnrollmentRegulations getMedicalEnrollmentRegulationsById(int id, String userId) {
        MedicalEnrollmentRegulations m = new MedicalEnrollmentRegulations();
        m.setId(id);
        m.setDeleted(false);
        m.setStatus(true);
        MedicalEnrollmentRegulations medicalEnrollmentRegulations = medicalEnrollmentRegulationsMapper.selectOne(m);
        if (medicalEnrollmentRegulations == null) {
            throw new MedicalException("招生简章不存在或已结束");
        }
        if (userId != null) {
            MedicalEntryInformationVO medicalEntryInformationVO = getMedicalEntryInformationByUserIdAndERId(id, userId);
            if (medicalEntryInformationVO != null) {
                medicalEnrollmentRegulations.setEnrolled(true);
            }
        }
        List<String> allEnrolledUser = getAllEnrolledUser(medicalEnrollmentRegulations.getId());
        medicalEnrollmentRegulations.setAllEnrolledUser(allEnrolledUser);
        medicalEnrollmentRegulations.setAllEnrolledUserCount(getAllEnrolledUserCount(medicalEnrollmentRegulations.getId()));

        medicalEnrollmentRegulations.setDoctorPhoto(getDoctorPhoto(medicalEnrollmentRegulations.getId()));
        return medicalEnrollmentRegulations;
    }

    private String getDoctorPhoto(Integer id) {
        return medicalEntryInformationMapper.getDoctorPhoto(id);
    }

    private List<String> getAllEnrolledUser(Integer id) {
        return medicalEntryInformationMapper.getAllEnrolledUser(id);
    }

    private int getAllEnrolledUserCount(Integer id) {
        return medicalEntryInformationMapper.getAllEnrolledUserCount(id);
    }

    @Override
    public MedicalEntryInformationVO getMedicalEntryInformationByUserIdAndERId(int merId, String userId) {
        MedicalEntryInformation m = new MedicalEntryInformation();
        m.setUserId(userId);
        m.setMerId(merId);
        MedicalEntryInformation medicalEntryInformation = medicalEntryInformationMapper.selectOne(m);
        MedicalEntryInformationVO medicalEntryInformationVO = new MedicalEntryInformationVO();
        if (medicalEntryInformation != null) {
            BeanUtils.copyProperties(medicalEntryInformation, medicalEntryInformationVO);
        } else {
            medicalEntryInformationVO = null;
        }
        return medicalEntryInformationVO;
    }

    @Override
    public void saveMedicalEntryInformation(MedicalEntryInformationVO medicalEntryInformationVO) {
        validateMedicalEntryInformation(medicalEntryInformationVO);
        MedicalEntryInformation medicalEntryInformation = new MedicalEntryInformation();
        BeanUtils.copyProperties(medicalEntryInformationVO, medicalEntryInformation);
        medicalEntryInformation.setCreateTime(new Date());
        medicalEntryInformationMapper.insert(medicalEntryInformation);
    }

    @Override
    public MedicalEnrollmenRtegulationsCardInfoVO getMedicalEnrollmentRegulationsCardInfoById(int id, String userId, String returnOpenidUri) {
        MedicalEnrollmenRtegulationsCardInfoVO merci = medicalEnrollmentRegulationsMapper.getMedicalEnrollmentRegulationsCardInfoById(id);
        String profilePicture = medicalEnrollmentRegulationsMapper.selectUserPhoto4CardInfo(userId);
        merci.setProfilePicture(profilePicture);
        merci.setEnrolShareUrl(returnOpenidUri + ENROL_URL + id);
        return merci;
    }

    @Override
    public MedicalEnrollmentRegulations findById(int merId) {
        return medicalEnrollmentRegulationsMapper.selectById(merId);
    }

    @Override
    public List<MedicalEnrollmentRegulations> listByDoctorId(String doctorId) {
        return medicalEnrollmentRegulationsMapper.listByDoctorId(doctorId);
    }

    private void validateMedicalEntryInformation(MedicalEntryInformationVO medicalEntryInformationVO) {
        MedicalEnrollmentRegulations medicalEnrollmentRegulations = getMedicalEnrollmentRegulationsById(medicalEntryInformationVO.getMerId(), null);
        if (medicalEnrollmentRegulations == null) {
            throw new MedicalException("该招生不存在或已结束");
        }
        if (!isMobileNO(medicalEntryInformationVO.getTel())) {
            throw new MedicalException("手机号有误");
        }
        if (!isAge(medicalEntryInformationVO.getAge())) {
            throw new MedicalException("年龄有误");
        }
        if (medicalEntryInformationVO.getName() == null) {
            throw new MedicalException("姓名不为空");
        } else if (medicalEntryInformationVO.getName().length() > 32) {
            throw new MedicalException("姓名最多32个字");
        }
        if (medicalEntryInformationVO.getNativePlace() == null) {
            throw new MedicalException("籍贯不为空");
        } else if (medicalEntryInformationVO.getNativePlace().length() > 100) {
            throw new MedicalException("籍贯最多100个字");
        }
        if (medicalEntryInformationVO.getEducationExperience() == null) {
            throw new MedicalException("学习经历不为空");
        } else if (medicalEntryInformationVO.getEducationExperience().length() > 1000) {
            throw new MedicalException("学习经历最多1000个字");
        }
        if (medicalEntryInformationVO.getMedicalExperience() == null) {
            throw new MedicalException("行医经历不为空");
        } else if (medicalEntryInformationVO.getMedicalExperience().length() > 1000) {
            throw new MedicalException("行医经历最多1000个字");
        }
        if (medicalEntryInformationVO.getGoal() == null) {
            throw new MedicalException("学习目标不为空");
        } else if (medicalEntryInformationVO.getGoal().length() > 1000) {
            throw new MedicalException("学习目标最多1000个字");
        }
    }

    private boolean isMobileNO(String mobiles) {
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private boolean isAge(int age) {
        if (100 > age && age > 0) {
            return true;
        }
        return false;
    }

}
