package com.xczhihui.medical.enrol.service.impl;

import static com.xczhihui.common.util.enums.EntryInformationType.ONLINE_APPLY;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.common.util.SmsUtil;
import com.xczhihui.common.util.enums.ApprenticeStatus;
import com.xczhihui.common.util.enums.EntryInformationType;
import com.xczhihui.common.util.enums.OnlineApprenticeStatus;
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
    private static final Object APPLY_LOCK = new Object();
    private static final Object SAVE_LOCK = new Object();

    @Value("${online.apprentice.apply.success.sms.code}")
    private String applySuccessSmsCode;
    @Value("${online.apprentice.apply.fail.sms.code}")
    private String applyFailSmsCode;

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
        synchronized (SAVE_LOCK) {
            Integer merId = medicalEntryInformationVO.getMerId();
            String userId = medicalEntryInformationVO.getUserId();
            //招生简章
            validateMedicalEntryInformation(medicalEntryInformationVO);
            if (merId != null && merId != 0) {
                MedicalEnrollmentRegulations medicalEnrollmentRegulations = medicalEnrollmentRegulationsMapper.selectById(merId);
                if (medicalEnrollmentRegulations == null || medicalEnrollmentRegulations.getDeleted() || !medicalEnrollmentRegulations.getStatus()) {
                    throw new MedicalException("该招生不存在或已结束");
                }
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
                    if (onlineEntryInformation.getApplied() && onlineEntryInformation.getApprentice() == ApprenticeStatus.YES.getVal()) {
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
                    onlineEntryInformation.setMerId(0);
                    onlineEntryInformation.setType(ONLINE_APPLY.getCode());
                    onlineEntryInformation.setDoctorId(doctorId);
                    onlineEntryInformation.setCreateTime(new Date());
                    medicalEntryInformationMapper.insert(onlineEntryInformation);
                }
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
    public List<Map<String, Object>> listByDoctorId(String doctorId) {
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
        oldMedicalEnrollmentRegulations.setAttachmentName(medicalEnrollmentRegulations.getAttachmentName());
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
            mer.setAllEnrolledUserCount(getAllEnrolledUserCount(mer.getId()));
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
    public Page<MedicalEntryInformationVO> listByDoctorId(String doctorId, Integer type, Integer status, int page, int size) {
        Page<MedicalEntryInformationVO> medicalEntryInformationVOPage = new Page<>(page, size);
        List<MedicalEntryInformationVO> medicalEntryInformationVOS = medicalEntryInformationMapper.listEntryInformationByDoctorId(doctorId, type, status, medicalEntryInformationVOPage);
        medicalEntryInformationVOS.forEach(medicalEntryInformationVO ->
                medicalEntryInformationVO.setRegulationName(medicalEntryInformationVO.getRegulationName() == null ? "医师师承页面" : medicalEntryInformationVO.getRegulationName()));
        medicalEntryInformationVOPage.setRecords(medicalEntryInformationVOS);
        return medicalEntryInformationVOPage;
    }

    @Override
    public void updateStatusEntryInformationById(int id, int apprentice) {
        synchronized (APPLY_LOCK) {
            MedicalEntryInformation medicalEntryInformation = medicalEntryInformationMapper.selectById(id);
            if (medicalEntryInformation != null) {
                if ((apprentice == ApprenticeStatus.YES.getVal() || apprentice == ApprenticeStatus.NO.getVal())) {
                    //审核通过并且是弟子，不允许再次审核
                    if (medicalEntryInformation.getApplied() && medicalEntryInformation.getApprentice() == ApprenticeStatus.YES.getVal()) {
                        throw new MedicalException("已成为弟子，不可以再审核");
                    }
                    medicalEntryInformation.setApprentice(apprentice);
                    //标记为已审核
                    medicalEntryInformation.setApplied(true);
                    medicalEntryInformationMapper.updateById(medicalEntryInformation);
                    if (medicalEntryInformation.getType() == ONLINE_APPLY.getCode()) {
                        Map<String, String> smsParams = new HashMap<>(1);
                        String doctorId = medicalEntryInformation.getDoctorId();
                        MedicalDoctorVO medicalDoctorVO = medicalDoctorBusinessService.findSimpleById(doctorId);
                        if (medicalDoctorVO != null) {
                            smsParams.put("name", medicalDoctorVO.getName());
                            if (apprentice == ApprenticeStatus.YES.getVal()) {
                                SmsUtil.sendSMS(applySuccessSmsCode, smsParams, medicalEntryInformation.getTel());
                            } else {
                                SmsUtil.sendSMS(applyFailSmsCode, smsParams, medicalEntryInformation.getTel());
                            }
                        }
                    }
                } else {
                    throw new MedicalException("参数错误");
                }
            }
        }
    }

    @Override
    public MedicalEntryInformation findOnlineEntryInformation(String accountId, String doctorId) {
        MedicalEntryInformation m = new MedicalEntryInformation();
        m.setUserId(accountId);
        m.setMerId(0);
        m.setType(ONLINE_APPLY.getCode());
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
            settings.setCost(apprenticeSettings.getCost());
            apprenticeSettingsMapper.updateAllColumnById(settings);
        }
    }

    @Override
    public List<SimpleUserVO> findApprenticesByDoctorId(String doctorId) {
        return medicalEntryInformationMapper.findApprenticesByDoctorId(doctorId);
    }

    @Override
    public boolean isApprentice(String doctorId, String accountId) {
        Integer cnt = medicalEntryInformationMapper.countByDoctorIdAndAccountId(doctorId, accountId);
        return cnt != null && cnt > 0;
    }

    @Override
    public int getOnlineApprenticeStatus(String doctorId, String accountId) {
        MedicalEntryInformation onlineEntryInformation = findOnlineEntryInformation(accountId, doctorId);
        if (onlineEntryInformation == null) {
            return OnlineApprenticeStatus.NOT_ENTRY.getVal();
        } else if (!onlineEntryInformation.getApplied()) {
            return OnlineApprenticeStatus.NOT_APPLY.getVal();
        } else if (onlineEntryInformation.getApplied() && onlineEntryInformation.getApprentice() == ApprenticeStatus.YES.getVal()) {
            return OnlineApprenticeStatus.PASSED.getVal();
        } else {
            return OnlineApprenticeStatus.NOT_PASS.getVal();
        }
    }

    @Override
    public List<Map<String, String>> listByDoctorIdAndCourseId(String doctorId, String courseId) {
        return medicalEntryInformationMapper.listByDoctorIdAndCourseId(doctorId, courseId);
    }

    @Override
    public void saveCourseTeaching(String doctorId, String courseId, String apprenticeIds) {
        checkCourseDoctor(doctorId, courseId);
        medicalEntryInformationMapper.deleteCourseTeachingByCourseId(courseId);
        if (StringUtils.isNotBlank(apprenticeIds)) {
            List<String> userIds = Arrays.asList(apprenticeIds.split(","));
            medicalEntryInformationMapper.saveCourseTeaching(courseId, userIds);
        }
    }

    @Override
    public void saveCourseTeaching4Init(Integer courseId) {
        List<String> apprenticeIds = medicalEntryInformationMapper.getApprenticeIdsByCourseId(courseId.toString());
        medicalEntryInformationMapper.saveCourseTeaching(courseId.toString(), apprenticeIds);
    }

    void checkCourseDoctor(String doctorId, String courseId) {
        if (medicalEntryInformationMapper.checkCourseDoctor(doctorId, courseId) < 1) {
            throw new MedicalException("医师不具有该课程权限");
        }
    }

    @Override
    public Map<String, Object> findApprenticeInfo(String doctorId, String accountId) {
        return medicalEntryInformationMapper.findApprenticeInfo(doctorId, accountId);
    }

    @Override
    public boolean apprenticeApplying(String doctorId, String accountId) {
        Integer count = medicalEntryInformationMapper.countApplyingEntryInformation(doctorId, accountId);
        return count != null && count > 0;
    }

    @Override
    public boolean checkAuthTeachingCourse(String userId, Integer courseId) {
        Integer count = medicalEntryInformationMapper.countCourseTeaching(courseId, userId);
        return count != null && count > 0;
    }

    @Override
    public Integer countApprentice(String doctorId) {
        Integer count = medicalEntryInformationMapper.countApprenticeByDoctorId(doctorId);
        return count == null ? 0 : count;
    }

    @Override
    public MedicalEntryInformationVO findEntryInformationById(Integer id) {
        return medicalEntryInformationMapper.findById(id);
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
