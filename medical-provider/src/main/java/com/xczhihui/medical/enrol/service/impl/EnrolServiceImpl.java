package com.xczhihui.medical.enrol.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.enums.EntryInformationType;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.enrol.mapper.ApprenticeSettingsMapper;
import com.xczhihui.medical.enrol.mapper.MedicalEnrollmentRegulationsMapper;
import com.xczhihui.medical.enrol.mapper.MedicalEntryInformationMapper;
import com.xczhihui.medical.enrol.model.ApprenticeSettings;
import com.xczhihui.medical.enrol.model.MedicalEnrollmentRegulations;
import com.xczhihui.medical.enrol.model.MedicalEntryInformation;
import com.xczhihui.medical.enrol.service.EnrolService;
import com.xczhihui.medical.enrol.vo.MedicalEnrollmenRtegulationsCardInfoVO;
import com.xczhihui.medical.enrol.vo.MedicalEntryInformationVO;
import com.xczhihui.medical.exception.MedicalException;
import com.xczhihui.medical.headline.vo.SimpleUserVO;

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
    @Autowired
    private ApprenticeSettingsMapper apprenticeSettingsMapper;

    @Autowired
    private IMedicalDoctorBusinessService medicalDoctorBusinessService;

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
            medicalEnrollmentRegulations.setOnline(true);
            MedicalEntryInformationVO medicalEntryInformationVO = getMedicalEntryInformationByUserIdAndERId(id, userId);
            if (medicalEntryInformationVO != null) {
                medicalEnrollmentRegulations.setEnrolled(true);
            }
        } else {
            medicalEnrollmentRegulations.setOnline(false);
        }
        List<String> allEnrolledUser = getAllEnrolledUser(medicalEnrollmentRegulations.getId());
        medicalEnrollmentRegulations.setAllEnrolledUser(allEnrolledUser);
        medicalEnrollmentRegulations.setAllEnrolledUserCount(getAllEnrolledUserCount(medicalEnrollmentRegulations.getId()));

        Map<String, String> doctorPhotoAndName = getDoctorPhotoAndName(medicalEnrollmentRegulations.getId());
        medicalEnrollmentRegulations.setDoctorPhoto(doctorPhotoAndName.get("headPortrait"));
        medicalEnrollmentRegulations.setName(doctorPhotoAndName.get("name"));
        return medicalEnrollmentRegulations;
    }

    private Map<String, String> getDoctorPhotoAndName(Integer id) {
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
        m.setType(EntryInformationType.ENROLLMENT_REGULATIONS_APPLY.getCode());
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
        Integer merId = medicalEntryInformationVO.getMerId();
        String userId = medicalEntryInformationVO.getUserId();
        //招生简章
        if (merId != null && merId != 0) {
            MedicalEnrollmentRegulations medicalEnrollmentRegulations = medicalEnrollmentRegulationsMapper.selectById(merId);
            if (medicalEnrollmentRegulations == null || medicalEnrollmentRegulations.getDeleted() || !medicalEnrollmentRegulations.getStatus()) {
                throw new MedicalException("该招生不存在或已结束");
            }
            validateMedicalEntryInformation(medicalEntryInformationVO);
            if (medicalEntryInformationMapper.findOne(userId, merId) != null) {
                throw new MedicalException("您已报名！");
            }
            MedicalEntryInformation medicalEntryInformation = new MedicalEntryInformation();
            BeanUtils.copyProperties(medicalEntryInformationVO, medicalEntryInformation);
            medicalEntryInformation.setCreateTime(new Date());
            medicalEntryInformation.setType(EntryInformationType.ENROLLMENT_REGULATIONS_APPLY.getCode());
            medicalEntryInformation.setDoctorId(medicalEnrollmentRegulations.getDoctorId());
            medicalEntryInformationMapper.insert(medicalEntryInformation);
        } else {
            //在线弟子申请
            String doctorId = medicalEntryInformationVO.getDoctorId();
            if (StringUtils.isBlank(doctorId)) {
                throw new MedicalException("医师id不能为空");
            }
            MedicalEntryInformation onlineEntryInformation = findOnlineEntryInformation(medicalEntryInformationVO.getUserId(), doctorId);
            if (onlineEntryInformation != null) {
                if (onlineEntryInformation.getApplied() && onlineEntryInformation.getApprentice() == 1) {
                    throw new MedicalException("您已是该医师的弟子");
                }
                if (!onlineEntryInformation.getApplied()) {
                    throw new MedicalException("申请在审核中，请耐心等待");
                }
                onlineEntryInformation.setAge(medicalEntryInformationVO.getAge());
                onlineEntryInformation.setEducation(medicalEntryInformationVO.getEducation());
                onlineEntryInformation.setEducationExperience(medicalEntryInformationVO.getEducationExperience());
                onlineEntryInformation.setMedicalExperience(medicalEntryInformationVO.getMedicalExperience());
                onlineEntryInformation.setGoal(medicalEntryInformationVO.getGoal());
                onlineEntryInformation.setName(medicalEntryInformationVO.getName());
                onlineEntryInformation.setSex(medicalEntryInformationVO.getSex());
                onlineEntryInformation.setNativePlace(medicalEntryInformationVO.getNativePlace());
                onlineEntryInformation.setTel(medicalEntryInformationVO.getTel());
                onlineEntryInformation.setWechat(medicalEntryInformationVO.getWechat());
                onlineEntryInformation.setCreateTime(new Date());
                //更新审核状态为待审核
                onlineEntryInformation.setApplied(false);
                medicalEntryInformationMapper.updateAllColumnById(onlineEntryInformation);
            } else {
                onlineEntryInformation = new MedicalEntryInformation();
                BeanUtils.copyProperties(medicalEntryInformationVO, onlineEntryInformation);
                onlineEntryInformation.setMerId(null);
                onlineEntryInformation.setType(EntryInformationType.ONLINE_APPLY.getCode());
                onlineEntryInformation.setDoctorId(doctorId);
                onlineEntryInformation.setCreateTime(new Date());
                medicalEntryInformationMapper.insert(onlineEntryInformation);
            }
        }
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

    @Override
    public void save(MedicalEnrollmentRegulations medicalEnrollmentRegulations) {
        medicalEnrollmentRegulationsMapper.insert(medicalEnrollmentRegulations);
    }

    @Override
    public void updateById(MedicalEnrollmentRegulations medicalEnrollmentRegulations, Integer id) {
        MedicalEnrollmentRegulations oldMedicalEnrollmentRegulations = medicalEnrollmentRegulationsMapper.selectById(id);
        if (!oldMedicalEnrollmentRegulations.getDoctorId().equals(medicalEnrollmentRegulations.getDoctorId())) {
            throw new MedicalException("非法请求");
        }
        oldMedicalEnrollmentRegulations.setTitle(oldMedicalEnrollmentRegulations.getTitle());
        oldMedicalEnrollmentRegulations.setEntryFormAttachment(oldMedicalEnrollmentRegulations.getEntryFormAttachment());
        oldMedicalEnrollmentRegulations.setCeremonyAddress(medicalEnrollmentRegulations.getCeremonyAddress());
        oldMedicalEnrollmentRegulations.setCountLimit(medicalEnrollmentRegulations.getCountLimit());
        oldMedicalEnrollmentRegulations.setDeadline(medicalEnrollmentRegulations.getDeadline());
        oldMedicalEnrollmentRegulations.setCoverImg(medicalEnrollmentRegulations.getCoverImg());
        oldMedicalEnrollmentRegulations.setTuition(medicalEnrollmentRegulations.getTuition());
        oldMedicalEnrollmentRegulations.setStudyAddress(medicalEnrollmentRegulations.getStudyAddress());
        oldMedicalEnrollmentRegulations.setRegulations(medicalEnrollmentRegulations.getRegulations());
        oldMedicalEnrollmentRegulations.setEntryFormAttachment(medicalEnrollmentRegulations.getEntryFormAttachment());
        oldMedicalEnrollmentRegulations.setUpdateTime(new Date());
        medicalEnrollmentRegulationsMapper.updateAllColumnById(oldMedicalEnrollmentRegulations);
    }

    @Override
    public Page<MedicalEnrollmentRegulations> listPageByDoctorId(String doctorId, int page, int pageSize) {
        Page<MedicalEnrollmentRegulations> medicalEnrollmentRegulationsPage = new Page<>(page, pageSize);
        MedicalDoctorVO medicalDoctorVO = medicalDoctorBusinessService.findSimpleById(doctorId);
        List<MedicalEnrollmentRegulations> medicalEnrollmentRegulations = medicalEnrollmentRegulationsMapper.listPageByDoctorId(doctorId, medicalEnrollmentRegulationsPage);
        medicalEnrollmentRegulations.forEach(mer -> {
            if (medicalDoctorVO != null) {
                mer.setName(medicalDoctorVO.getName());
            }
        });
        medicalEnrollmentRegulationsPage.setRecords(medicalEnrollmentRegulations);
        return medicalEnrollmentRegulationsPage;
    }

    @Override
    public void updateRegulationsStatus(int id, String doctorId, boolean status) {
        MedicalEnrollmentRegulations medicalEnrollmentRegulations = medicalEnrollmentRegulationsMapper.selectById(id);
        medicalEnrollmentRegulations.setStatus(status);
        medicalEnrollmentRegulationsMapper.updateAllColumnById(medicalEnrollmentRegulations);
    }

    @Override
    public Page<MedicalEntryInformationVO> listByDoctorId(String doctorId, Integer type, Integer apprentice, int page, int size) {
        Page<MedicalEntryInformationVO> medicalEntryInformationVOPage = new Page<>(page, size);
        medicalEntryInformationVOPage.setRecords(medicalEntryInformationMapper.listEntryInformationByDoctorId(doctorId, type, apprentice, medicalEntryInformationVOPage));
        return medicalEntryInformationVOPage;
    }

    @Override
    public void updateStatusEntryInformationById(int id, int apprentice) {
        MedicalEntryInformation medicalEntryInformation = medicalEntryInformationMapper.selectById(id);
        if (medicalEntryInformation != null) {
            if ((apprentice == 1 || apprentice == 0) && !medicalEntryInformation.getApplied()) {
                medicalEntryInformation.setApprentice(apprentice);
                //标记为已审核
                medicalEntryInformation.setApplied(true);
                medicalEntryInformationMapper.updateById(medicalEntryInformation);
                //TODO 发送短信(只有在线申请的才发送)
            } else {
                throw new MedicalException("已审核过或参数错误");
            }
        }
    }

    @Override
    public MedicalEntryInformation findOnlineEntryInformation(String accountId, String doctorId) {
        MedicalEntryInformation m = new MedicalEntryInformation();
        m.setUserId(accountId);
        m.setMerId(null);
        m.setType(EntryInformationType.ONLINE_APPLY.getCode());
        m.setDoctorId(doctorId);
        return medicalEntryInformationMapper.selectOne(m);
    }

    @Override
    public ApprenticeSettings findSettingsByDoctorId(String doctorId) {
        return apprenticeSettingsMapper.findByDoctorId(doctorId);
    }

    @Override
    public void saveApprenticeSettings(ApprenticeSettings apprenticeSettings) {
        String doctorId = apprenticeSettings.getDoctorId();
        ApprenticeSettings settings = findSettingsByDoctorId(doctorId);
        if (settings == null) {
            apprenticeSettings.setId(null);
            apprenticeSettings.setCreateTime(new Date());
            apprenticeSettingsMapper.insert(apprenticeSettings);
        } else {
            String requirement = apprenticeSettings.getRequirement();
            String welfare = apprenticeSettings.getWelfare();
            if (StringUtils.isBlank(requirement) || StringUtils.isBlank(welfare)) {
                throw new MedicalException("参数错误");
            }
            settings.setRequirement(requirement);
            settings.setWelfare(welfare);
            apprenticeSettingsMapper.updateAllColumnById(settings);
        }
    }

    @Override
    public List<SimpleUserVO> findApprenticesByDoctorId(String doctorId) {
        return medicalEntryInformationMapper.findApprenticesByDoctorId(doctorId);
    }

    private void validateMedicalEntryInformation(MedicalEntryInformationVO medicalEntryInformationVO) {
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
        return 100 > age && age > 0;
    }
}
