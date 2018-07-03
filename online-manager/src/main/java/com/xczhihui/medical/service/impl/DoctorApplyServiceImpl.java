package com.xczhihui.medical.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczhihui.anchor.dao.AnchorDao;
import com.xczhihui.bxg.online.common.consts.MedicalDoctorApplyConst;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.common.support.lock.RedissonUtil;
import com.xczhihui.common.util.CodeUtil;
import com.xczhihui.common.util.TimeUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.enums.AnchorType;
import com.xczhihui.course.enums.MessageTypeEnum;
import com.xczhihui.course.enums.RouteTypeEnum;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.util.TextStyleUtil;
import com.xczhihui.medical.dao.*;
import com.xczhihui.medical.service.DoctorApplyService;
import com.xczhihui.support.shiro.ManagerUserUtil;
import com.xczhihui.user.dao.UserDao;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.vhall.VhallUtil;

/**
 * 医师入驻申请服务实现层
 *
 * @author zhuwenbao
 */
@Service
public class DoctorApplyServiceImpl implements DoctorApplyService {

    private static final String APPROVE_PASS_MESSAGE = "您的{0}申请已于{1}通过认证，现在您已经是一名主播了，" +
            "快去发布课程吧~如需帮助请联系客服0898-32881934。" + TextStyleUtil.LEFT_TAG + "去看看>>" + TextStyleUtil.RIGHT_TAG;
    private static final String APPROVE_NOT_PASS_MESSAGE = "您的{0}认证未能通过，原因: {1}，如有疑问请联系客服0898-32881934。"
            + TextStyleUtil.LEFT_TAG + "查看详情" + TextStyleUtil.RIGHT_TAG;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DoctorApplyDao doctorApplyDao;
    @Autowired
    private DoctorDao doctorDao;
    @Autowired
    private DoctorAccountDao doctorAccountDao;
    @Autowired
    private DoctorApplyDepartmentDao doctorApplyDepartmentDao;
    @Autowired
    private DoctorDepartmentDao doctorDepartmentDao;
    @Autowired
    private DoctorAuthenticationInformationDao doctorAuthenticationInformationDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private HospitalAccountDao hospitalAccountDao;
    @Autowired
    private HospitalDao hospitalDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AnchorDao anchorDao;
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private ICommonMessageService commonMessageService;
    @Autowired
    private RedissonUtil redissonUtil;

    @Value("${course.anchor.vod.divide}")
    private BigDecimal vodDivide;

    @Value("${course.anchor.live.divide}")
    private BigDecimal liveDivide;

    @Value("${course.anchor.offline.divide}")
    private BigDecimal offlineDivide;

    @Value("${course.anchor.gift.divide}")
    private BigDecimal giftDivide;

    @Value("${sms.anchor.approve.pass.code}")
    private String smsAnchorApprovePassCode;
    @Value("${sms.anchor.approve.notPass.code}")
    private String smsAnchorApproveNotPassCode;

    @Value("${weixin.anchor.approve.pass.code}")
    private String weixinAnchorApprovePassCode;

    /**
     * 获取医师入驻申请列表
     *
     * @param searchVo    查询条件
     * @param currentPage 当前页
     * @param pageSize    每页显示的列数
     * @return 医师入驻申请分页列表
     */
    @Override
    public Page<MedicalDoctorApply> list(MedicalDoctorApply searchVo,
                                         int currentPage, int pageSize) {

        return doctorApplyDao.list(searchVo, currentPage, pageSize);
    }

    /**
     * 更新医师入驻申请状态
     *
     * @param doctorApply 更新的数据封装类
     */
    @Override
    public String updateStatus(MedicalDoctorApply doctorApply) {
        if (doctorApply == null) {
            return null;
        }
        if (StringUtils.isBlank(doctorApply.getId())) {
            throw new RuntimeException("请选择要修改的目标");
        }
        if (doctorApply.getStatus() == null) {
            throw new RuntimeException("缺少请求参数：status");
        }

        String id = doctorApply.getId();
        Integer status = doctorApply.getStatus();

        // 根据id获取被修改的认证信息详情
        MedicalDoctorApply apply = doctorApplyDao.find(id);

        if (apply == null) {
            throw new RuntimeException("修改的目标不存在");
        }

        RLock lock = redissonUtil.getRedisson().getLock(MedicalDoctorApplyConst.LOCK_PREFIX + apply.getId());
        boolean getLock = false;
        String doctorId = null;
        try {
            if (getLock = lock.tryLock(3, 10, TimeUnit.SECONDS)) {

                if (apply == null) {
                    throw new RuntimeException("操作失败：该条信息不存在");
                }

                // 如果该条记录已经被通过或者被拒绝 不能再修改
                if (apply.getStatus().equals(0)) {
                    throw new RuntimeException("该条认证已经被拒，不能再修改");
                }
                if (apply.getStatus().equals(1)) {
                    throw new RuntimeException("该条认证已经认证成功，不能再修改");
                }

                // 如果该条信息被删除 不能再修改
                if (apply.getDeleted()) {
                    throw new RuntimeException("该条认证已经被删除，用户已重新认证");
                }

                switch (status) {
                    // 当status = 1 即认证通过
                    case 1:
                        doctorId = this.authenticationPassHandle(apply);
                        break;
                    // 当status = 0 即认证被拒
                    case 0:
                        apply.setRemark(doctorApply.getRemark());
                        this.authenticationRejectHandle(apply);
                        break;
                    default:
                        break;
                }

                // 更新认证状态
                apply.setStatus(status);
                apply.setUpdateTime(new Date());
                doctorApplyDao.update(apply);

            }

        } catch (InterruptedException e) {

            logger.error("-------------- redisson get lock interruptedException："
                    + e);

        } finally {

            if (!getLock) {
                return doctorId;
            }

            lock.unlock();

            logger.info("--------------  redisson release lock");
        }
        return doctorId;
    }

    @Override
    public MedicalDoctorApply findById(String id) {

        MedicalDoctorApply medicalDoctorApply = doctorApplyDao.find(id);
        if (medicalDoctorApply == null) {
            throw new RuntimeException("该条记录不存在");
        }

        List<MedicalDepartment> departments = new ArrayList<>();

        List<MedicalDoctorApplyDepartment> applyDepartments = doctorApplyDepartmentDao
                .findByApplyId(medicalDoctorApply.getId());
        if (applyDepartments != null && !applyDepartments.isEmpty()) {
            for (MedicalDoctorApplyDepartment applyDepartment : applyDepartments) {
                MedicalDepartment department = departmentDao
                        .findById(applyDepartment.getDepartmentId());
                if (department != null) {
                    departments.add(department);
                }
            }
        }

        if (departments != null && !departments.isEmpty()) {
            medicalDoctorApply.setDepartments(departments);
        }

        return medicalDoctorApply;
    }

    /**
     * 处理认证通过逻辑
     */
    private String authenticationPassHandle(MedicalDoctorApply apply) {

        // 判断用户是否已经认证医馆（医师 医馆只能认证一个）
        MedicalHospitalAccount hospitalAccount = hospitalAccountDao.findByAccountId(apply.getUserId());
        if (hospitalAccountDao.findByAccountId(apply.getUserId()) != null && StringUtils.isNotBlank(hospitalAccount.getDoctorId())) {
            MedicalHospital hospital = hospitalDao.find(hospitalAccount.getDoctorId());
            if (hospital != null && hospital.isAuthentication() == true) {
                throw new RuntimeException("该用户已拥有已认证医馆，不能再进行认证医师");
            }
        }

        MedicalDoctorAccount doctorAccount = doctorAccountDao.findByAccountId(apply.getUserId());
        // 判断用户是否已经是医师 如果是，则表示其重新认证
        if (doctorAccount != null) {
            this.applyAgain(apply, doctorAccount.getDoctorId());
            return null;
        }

        Date now = new Date();
        String doctorId = CodeUtil.getRandomUUID();

        // 新增医师与用户的对应关系：medical_doctor_account
        this.addMedicalDoctorAccount(apply.getUserId(), doctorId, now);

        // 新增医师信息：medical_doctor
        String authenticationInformationId = CodeUtil.getRandomUUID();
        this.addMedicalDoctor(doctorId, authenticationInformationId, apply, 2, now);

        // 新增医师与科室的对应关系：medical_doctor_department
        List<MedicalDoctorApplyDepartment> medicalDoctorApplyDepartments = doctorApplyDepartmentDao
                .findByApplyId(apply.getId());

        // 将MedicalDoctorApplyDepartment数据格式转化成：MedicalDoctorDepartment
        if (medicalDoctorApplyDepartments != null
                && !medicalDoctorApplyDepartments.isEmpty()) {
            medicalDoctorApplyDepartments.stream().forEach(
                    department -> this.addMedicalDepartment(department,
                            doctorId, now));
        }

        // 新增认证成功信息：medical_doctor_authentication_information
        this.addMedicalDoctorAuthenticationInformation(
                authenticationInformationId, apply, 2, now);

        // 设置oe_user表中的is_lecturer为1
        userDao.updateIsLecturerById(1, apply.getUserId());

        // 设置讲师权限
        OnlineUser user = onlineUserService.getOnlineUserByUserId(apply
                .getUserId());
        VhallUtil.changeUserPower(user.getVhallId(), "1", "0");

        // course_anchor` 表中新增一条信息
        CourseAnchor courseAnchor = new CourseAnchor();
        courseAnchor.setUserId(apply.getUserId());
        courseAnchor.setType(AnchorType.DOCTOR.getCode());
        courseAnchor.setCreateTime(new Date());
        courseAnchor.setLiveDivide(liveDivide);
        courseAnchor.setOfflineDivide(offlineDivide);
        courseAnchor.setVodDivide(vodDivide);
        courseAnchor.setGiftDivide(giftDivide);
        courseAnchor.setDeleted(false);
        courseAnchor.setDetail(apply.getDescription());
        courseAnchor.setStatus(true);

        courseAnchor.setClientType(apply.getClientType());
        if (StringUtils.isNotBlank(user.getName())) {
            courseAnchor.setName(user.getName());
        }
        if (StringUtils.isNotBlank(user.getSmallHeadPhoto())) {
            courseAnchor.setProfilePhoto(user.getSmallHeadPhoto());
        }
        anchorDao.save(courseAnchor);

        sendApprovePassMessage(courseAnchor, apply.getCreateTime());

        return doctorId;
    }

    /**
     * 医师认证审核通过消息
     *
     * @param courseAnchor
     */
    private void sendApprovePassMessage(CourseAnchor courseAnchor, Date applyTime) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            String type = "医师";
            String dateStr = simpleDateFormat.format(new Date());
            String content = MessageFormat.format(APPROVE_PASS_MESSAGE, type, dateStr);
            Map<String, String> params = new HashMap<>(2);
            params.put("type", type);
            params.put("date", dateStr);

            Map<String, String> weixinParams = new HashMap<>(5);
            weixinParams.put("first", TextStyleUtil.clearStyle(content));
            weixinParams.put("keyword1", courseAnchor.getName());
            weixinParams.put("keyword2", "认证通过");
            weixinParams.put("keyword3", TimeUtil.getYearMonthDayHHmm(applyTime));
            weixinParams.put("remark", "");
            commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
                    .buildWeb(content)
                    .buildAppPush(content)
                    .buildSms(smsAnchorApprovePassCode, params)
                    .buildWeixin(weixinAnchorApprovePassCode, weixinParams)
                    .build(courseAnchor.getUserId(), RouteTypeEnum.ANCHOR_WORK_TABLE_PAGE, ManagerUserUtil.getId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 医师认证审核不通过消息
     *
     * @param apply
     */
    private void sendApproveNotPassMessage(MedicalDoctorApply apply) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String type = "医师";
        String reason = apply.getRemark();
        String content = MessageFormat.format(APPROVE_NOT_PASS_MESSAGE, type, reason);
        Map<String, String> params = new HashMap<>();
        params.put("type", type);
        commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
                .buildWeb(content)
                .buildAppPush(content)
                .buildSms(smsAnchorApproveNotPassCode, params)
                .build(apply.getUserId(), RouteTypeEnum.DOCTOR_APPROVE_PAGE, ManagerUserUtil.getId()));
    }

    /**
     * 重新认证
     */
    private void applyAgain(MedicalDoctorApply apply, String doctorId) {

        Date now = new Date();

        // 获取用户的医师信息
        MedicalDoctor doctor = doctorDao.find(doctorId);

        MedicalDoctorAuthenticationInformation authenticationInformation = new MedicalDoctorAuthenticationInformation();

        authenticationInformation.setId(doctor.getAuthenticationInformationId());
        authenticationInformation.setUpdatePerson(apply.getUserId());
        authenticationInformation.setUpdateTime(now);
        authenticationInformation.setCardNegative(apply.getCardNegative());
        authenticationInformation.setCardPositive(apply.getCardPositive());
        authenticationInformation.setQualificationCertificate(apply.getQualificationCertificate());
        authenticationInformation.setProfessionalCertificate(apply.getProfessionalCertificate());
        if (StringUtils.isNotBlank(apply.getTitleProve())) {
            authenticationInformation.setTitleProve(apply.getTitleProve());
        }
        if (StringUtils.isNotBlank(apply.getHeadPortrait())) {
            authenticationInformation.setHeadPortrait(apply.getHeadPortrait());
        }

        // 更新用户的医师认证信息
        doctorAuthenticationInformationDao.update(authenticationInformation);

        // 更新医师信息
        doctor.setName(apply.getName());
        doctor.setCardNum(apply.getCardNum());
        if (StringUtils.isNotBlank(apply.getTitle())) {
            doctor.setTitle(apply.getTitle());
        }
        if (StringUtils.isNotBlank(apply.getField())) {
            doctor.setFieldText(apply.getField());
        }
        if (StringUtils.isNotBlank(apply.getDescription())) {
            doctor.setDescription(apply.getDescription());
        }
        if (StringUtils.isNotBlank(apply.getProvince())) {
            doctor.setProvince(apply.getProvince());
        }
        if (StringUtils.isNotBlank(apply.getCity())) {
            doctor.setCity(apply.getCity());
        }
        if (StringUtils.isNotBlank(apply.getDetailedAddress())) {
            doctor.setDetailedAddress(apply.getDetailedAddress());
        }
        doctor.setSourceId(apply.getId());
        doctorDao.update(doctor);

        // 新增医师与科室的对应关系：medical_doctor_department
        List<MedicalDoctorApplyDepartment> medicalDoctorApplyDepartments = doctorApplyDepartmentDao.findByApplyId(apply.getId());

        // 将MedicalDoctorApplyDepartment数据格式转化成：MedicalDoctorDepartment
        if (medicalDoctorApplyDepartments != null && !medicalDoctorApplyDepartments.isEmpty()) {
            medicalDoctorApplyDepartments.stream().forEach(department -> this.addMedicalDepartment(department,doctorId, now));
        }
    }

    private void addMedicalDepartment(MedicalDoctorApplyDepartment department,String doctorId, Date createTime) {
        MedicalDoctorDepartment doctorDepartment = new MedicalDoctorDepartment();
        doctorDepartment.setId(CodeUtil.getRandomUUID());
        doctorDepartment.setDoctorId(doctorId);
        doctorDepartment.setDepartmentId(department.getDepartmentId());
        doctorDepartment.setCreateTime(createTime);
        doctorDepartmentDao.save(doctorDepartment);
    }

    /**
     * 处理认证失败逻辑
     */
    private void authenticationRejectHandle(MedicalDoctorApply apply) {
        sendApproveNotPassMessage(apply);
    }

    /**
     * 新增一条认证成功信息(往medical_doctor_authentication_information表插入一条信息）
     *
     * @param authenticationInformationId medical_doctor_authentication_information表主键
     * @param apply                       认证信息的封装
     * @param type                        新增数据的类型（1：系统生成 2:管理员认证）
     * @param createTime                  创建时间
     */
    private void addMedicalDoctorAuthenticationInformation(String authenticationInformationId, MedicalDoctorApply apply, int type, Date createTime) {
        MedicalDoctorAuthenticationInformation authenticationInformation = new MedicalDoctorAuthenticationInformation();
        authenticationInformation.setId(authenticationInformationId);
        if (StringUtils.isNotBlank(apply.getCardPositive())) {
            authenticationInformation.setCardPositive(apply
                    .getCardPositive());
        }
        if (StringUtils.isNotBlank(apply.getCardNegative())) {
            authenticationInformation.setCardNegative(apply
                    .getCardNegative());
        }
        if (StringUtils.isNotBlank(apply.getQualificationCertificate())) {
            authenticationInformation.setQualificationCertificate(apply
                    .getQualificationCertificate());
        }
        if (StringUtils.isNotBlank(apply.getTitleProve())) {
            authenticationInformation.setTitleProve(apply.getTitleProve());
        }
        if (StringUtils.isNotBlank(apply.getProfessionalCertificate())) {
            authenticationInformation.setProfessionalCertificate(apply
                    .getProfessionalCertificate());
        }
        if (StringUtils.isNotBlank(apply.getHeadPortrait())) {
            authenticationInformation.setHeadPortrait(apply
                    .getHeadPortrait());
        }

        authenticationInformation.setDeleted(false);
        authenticationInformation.setStatus(true);
        authenticationInformation.setCreateTime(createTime);
        doctorAuthenticationInformationDao.save(authenticationInformation);
    }

    /**
     * 新增医师信息(往medical_doctor表插入一条信息）
     *
     * @param doctorId                    medical_doctor表的主键
     * @param authenticationInformationId medical_doctor_authentication_information表的主键
     * @param apply                       用户提交的认证信息封装
     * @param type                        新增类型（1：系统生成 2:管理员认证）
     * @param createTime                  创建时间
     */
    private void addMedicalDoctor(String doctorId, String authenticationInformationId, MedicalDoctorApply apply, int type, Date createTime) {

        MedicalDoctor doctor = new MedicalDoctor();

        doctor.setId(doctorId);
        doctor.setAuthenticationInformationId(authenticationInformationId);
        doctor.setName(apply.getName());
        doctor.setCardNum(apply.getCardNum());
        if (StringUtils.isNotBlank(apply.getTitle())) {
            doctor.setTitle(apply.getTitle());
        }
        if (StringUtils.isNotBlank(apply.getDescription())) {
            doctor.setDescription(apply.getDescription());
        }
        if (StringUtils.isNotBlank(apply.getProvince())) {
            doctor.setProvince(apply.getProvince());
        }
        if (StringUtils.isNotBlank(apply.getCity())) {
            doctor.setCity(apply.getCity());
        }
        if (StringUtils.isNotBlank(apply.getDetailedAddress())) {
            doctor.setDetailedAddress(apply.getDetailedAddress());
        }
        if (StringUtils.isNotBlank(apply.getField())) {
            doctor.setFieldText(apply.getField());
        }
        doctor.setClientType(apply.getClientType());
        doctor.setStatus(true);
        doctor.setCreateTime(createTime);
        doctor.setSourceId(apply.getId());
        doctorDao.save(doctor);
    }

    /**
     * 新增一条用户和认证医师的关联关系 medical_doctor_account
     *
     * @param userId     用户id
     * @param doctorId   医师id
     * @param createTime 创建时间
     */
    private void addMedicalDoctorAccount(String userId, String doctorId, Date createTime) {
        MedicalDoctorAccount doctorAccount = new MedicalDoctorAccount();
        doctorAccount.setId(UUID.randomUUID().toString().replace("-", ""));
        doctorAccount.setAccountId(userId);
        doctorAccount.setDoctorId(doctorId);
        doctorAccount.setCreateTime(createTime);
        doctorAccountDao.save(doctorAccount);
    }

}
