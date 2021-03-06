package com.xczhihui.medical.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xczhihui.anchor.dao.AnchorDao;
import com.xczhihui.bxg.online.common.consts.MedicalHospitalApplyConst;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.common.support.lock.RedissonUtil;
import com.xczhihui.common.util.TimeUtil;
import com.xczhihui.common.util.bean.Page;
import com.xczhihui.common.util.enums.AnchorType;
import com.xczhihui.common.util.enums.MessageTypeEnum;
import com.xczhihui.common.util.enums.RouteTypeEnum;
import com.xczhihui.course.params.BaseMessage;
import com.xczhihui.course.service.ICommonMessageService;
import com.xczhihui.course.util.TextStyleUtil;
import com.xczhihui.medical.dao.*;
import com.xczhihui.medical.service.HospitalApplyService;
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
public class HospitalApplyServiceImpl implements HospitalApplyService {

    private static final String APPROVE_PASS_MESSAGE = "您的{0}申请已于{1}通过认证，现在您已经是一名主播了，" +
            "快去发布课程吧~如需帮助请联系客服0898-32881934。" + TextStyleUtil.LEFT_TAG + "去看看>>" + TextStyleUtil.RIGHT_TAG;
    private static final String APPROVE_NOT_PASS_MESSAGE = "您的{0}认证未能通过，原因: {1}，如有疑问请联系客服0898-32881934。"
            + TextStyleUtil.LEFT_TAG + "查看详情" + TextStyleUtil.RIGHT_TAG;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Value("${sms.anchor.approve.pass.code}")
    private String smsAnchorApprovePassCode;
    @Value("${sms.anchor.approve.notPass.code}")
    private String smsAnchorApproveNotPassCode;

    @Autowired
    private HospitalApplyDao hospitalApplyDao;
    @Autowired
    private HospitalDao hospitalDao;
    @Autowired
    private HospitalAccountDao hospitalAccountDao;
    @Autowired
    private HospitalAuthenticationDao hospitalAuthenticationDao;
    @Autowired
    private DoctorAccountDao doctorAccountDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private AnchorDao anchorDao;
    @Autowired
    private RedissonUtil redissonUtil;
    @Autowired
    private OnlineUserService onlineUserService;
    @Autowired
    private ICommonMessageService commonMessageService;

    @Value("${course.anchor.vod.divide}")
    private BigDecimal vodDivide;

    @Value("${course.anchor.live.divide}")
    private BigDecimal liveDivide;

    @Value("${course.anchor.offline.divide}")
    private BigDecimal offlineDivide;

    @Value("${course.anchor.gift.divide}")
    private BigDecimal giftDivide;

    @Value("${weixin.anchor.approve.pass.code}")
    private String weixinAnchorApprovePassCode;
    @Value("${weixin.anchor.approve.not.pass.code}")
    private String weixinAnchorApproveNotPassCode;

    /**
     * 获取医师入驻申请列表
     *
     * @param searchVo    查询条件
     * @param currentPage 当前页
     * @param pageSize    每页显示的列数
     * @return 医师入驻申请分页列表
     */
    @Override
    public Page<MedicalHospitalApply> list(MedicalHospitalApply searchVo, int currentPage, int pageSize) {
        return hospitalApplyDao.list(searchVo, currentPage, pageSize);
    }

    /**
     * 更新医师入驻申请状态
     *
     * @param hospitalApply 更新的数据封装类
     */
    @Override
    public String updateStatus(MedicalHospitalApply hospitalApply) {
        String hospitalId = null;
        if (hospitalApply == null) {
            return hospitalId;
        }

        String id = hospitalApply.getId();
        Integer status = hospitalApply.getStatus();

        if (StringUtils.isBlank(id)) {
            throw new RuntimeException("请选择要修改的目标");
        }
        if (status == null) {
            throw new RuntimeException("缺少请求参数：status");
        }

        // 根据id获取被修改的认证信息详情
        MedicalHospitalApply apply = hospitalApplyDao.find(id);

        if (apply == null) {
            throw new RuntimeException("修改的目标不存在");
        }

        RLock lock = redissonUtil.getRedisson().getLock(
                MedicalHospitalApplyConst.LOCK_PREFIX + apply.getId());
        boolean getLock = false;

        try {

            if (getLock = lock.tryLock(0, 5, TimeUnit.SECONDS)) {

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
                // 前台传来的状态和数据库的状态一致 不予处理
                if (apply.getStatus().equals(status)) {
                    return hospitalId;
                }
                // 如果该条信息被删除 不能再修改
                if (apply.getDeleted()) {
                    throw new RuntimeException("该条认证已经被删除，用户已重新认证");
                }

                switch (status) {
                    // 当status = 0 即认证被拒
                    case 0:
                        apply.setRemark(hospitalApply.getRemark());
                        this.authenticationRejectHandle(apply);
                        break;
                    // 当status = 1 即认证通过
                    case 1:
                        hospitalId = this.authenticationPassHandle(apply);
                        break;
                    default:
                        break;
                }

                // 更新认证状态
                apply.setUpdateTime(new Date());
                apply.setStatus(status);
                hospitalApplyDao.update(apply);

            }

        } catch (InterruptedException e) {

            logger.error("-------------- redisson get lock interruptedException："
                    + e);

        } finally {

            if (!getLock) {
                return hospitalId;
            }

            lock.unlock();

            logger.info("--------------  redisson release lock");
        }
        return hospitalId;
    }

    /**
     * 根据id查询详情
     *
     * @param applyId
     */
    @Override
    public MedicalHospitalApply findById(String applyId) {

        return hospitalApplyDao.find(applyId);

    }

    /**
     * 处理认证通过逻辑
     */
    private String authenticationPassHandle(MedicalHospitalApply apply) {

        Date now = new Date();

        String hospitalId = UUID.randomUUID().toString().replace("-", "");

        // 判断用户是否已经是认证医师（医师 医馆只能认证一个）
        MedicalDoctorAccount doctorAccount = doctorAccountDao.findByAccountId(apply.getUserId());
        if (doctorAccount != null) {
            throw new RuntimeException("该用户已是医师，不能再进行认证医馆");
        }

        // 判断用户是否已经已拥有验证医馆
        MedicalHospitalAccount hospitalAccount = hospitalAccountDao.findByAccountId(apply.getUserId());
        if (hospitalAccountDao.findByAccountId(apply.getUserId()) != null
                && StringUtils.isNotBlank(hospitalAccount.getDoctorId())) {
            MedicalHospital hospital = hospitalDao.find(hospitalAccount
                    .getDoctorId());
            if (hospital != null && hospital.isAuthentication() == true) {
                throw new RuntimeException("该用户已拥有已认证医馆，不能再进行认证");
            }
        }

        // 新增医师与用户的对应关系：medical_hospital_account
        MedicalHospitalAccount newHospitalAccount = new MedicalHospitalAccount();
        newHospitalAccount.setId(UUID.randomUUID().toString().replace("-", ""));
        newHospitalAccount.setAccountId(apply.getUserId());
        newHospitalAccount.setDoctorId(hospitalId);
        newHospitalAccount.setCreateTime(now);
        hospitalAccountDao.save(newHospitalAccount);

        // 新增医馆信息：medical_hospital
        MedicalHospital hospital = new MedicalHospital();
        hospital.setId(hospitalId);
        hospital.setName(apply.getName());
        hospital.setCreateTime(now);
        hospital.setAuthentication(true);
        hospital.setDeleted(false);
        hospital.setStatus(true);
        String authenticationInformationId = UUID.randomUUID().toString()
                .replace("-", "");
        hospital.setAuthenticationId(authenticationInformationId);
        hospital.setSourceId(apply.getId());
        hospital.setClientType(apply.getClientType());
        hospitalDao.save(hospital);

        // 新增认证成功信息：medical_hospital_authentication
        MedicalHospitalAuthentication authenticationInformation = new MedicalHospitalAuthentication();
        authenticationInformation.setCompany(apply.getCompany());
        authenticationInformation.setId(authenticationInformationId);
        authenticationInformation.setBusinessLicenseNo(apply
                .getBusinessLicenseNo());
        authenticationInformation.setBusinessLicensePicture(apply
                .getBusinessLicensePicture());
        authenticationInformation.setLicenseForPharmaceuticalTrading(apply
                .getLicenseForPharmaceuticalTrading());
        authenticationInformation
                .setLicenseForPharmaceuticalTradingPicture(apply
                        .getLicenseForPharmaceuticalTradingPicture());

        authenticationInformation.setDeleted(false);
        authenticationInformation.setCreateTime(now);

        hospitalAuthenticationDao.save(authenticationInformation);

        // 设置oe_user表中的is_lecturer为1
        userDao.updateIsLecturerById(1, apply.getUserId());

        // 设置讲师权限
        OnlineUser user = onlineUserService.getOnlineUserByUserId(apply
                .getUserId());
        VhallUtil.changeUserPower(user.getVhallId(), "1", "0");

        // course_anchor` 表中新增一条信息
        CourseAnchor courseAnchor = new CourseAnchor();
        courseAnchor.setUserId(apply.getUserId());
        courseAnchor.setType(AnchorType.HOSPITAL.getCode());
        courseAnchor.setCreateTime(new Date());
        courseAnchor.setLiveDivide(liveDivide);
        courseAnchor.setOfflineDivide(offlineDivide);
        courseAnchor.setVodDivide(vodDivide);
        courseAnchor.setGiftDivide(giftDivide);
        courseAnchor.setDeleted(false);
        courseAnchor.setStatus(true);

        courseAnchor.setClientType(apply.getClientType());
        if (StringUtils.isNotBlank(user.getName())) {
            courseAnchor.setName(user.getName());
        }
        if (StringUtils.isNotBlank(user.getSmallHeadPhoto())) {
            courseAnchor.setProfilePhoto(user.getSmallHeadPhoto());
        }
        anchorDao.save(courseAnchor);
        sendApprovePassMessage(courseAnchor, apply);
        return hospitalId;
    }

    /**
     * 处理认证失败逻辑
     */
    private void authenticationRejectHandle(MedicalHospitalApply medicalHospitalApply) {
        sendApproveNotPassMessage(medicalHospitalApply);
    }

    /**
     * 医馆认证审核通过消息
     *
     * @param courseAnchor
     */
    private void sendApprovePassMessage(CourseAnchor courseAnchor, MedicalHospitalApply apply) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String type = "医馆";
        String dateStr = simpleDateFormat.format(new Date());
        String content = MessageFormat.format(APPROVE_PASS_MESSAGE, type, dateStr);
        Map<String, String> params = new HashMap<>(2);
        params.put("type", type);
        params.put("date", dateStr);

        Map<String, String> weixinParams = new HashMap<>(5);
        weixinParams.put("first", TextStyleUtil.clearStyle(content).replace("去看看>>", ""));
        weixinParams.put("keyword1", apply.getName());
        weixinParams.put("keyword2", "认证通过");
        weixinParams.put("keyword3", dateStr);
        weixinParams.put("remark", "");
        commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
                .buildWeb(content)
                .buildAppPush(TextStyleUtil.clearStyle(content))
                .buildWeixin(weixinAnchorApprovePassCode, weixinParams)
                .buildSms(smsAnchorApprovePassCode, params)
                .build(courseAnchor.getUserId(), RouteTypeEnum.ANCHOR_WORK_TABLE_PAGE, ManagerUserUtil.getId()));
    }

    /**
     * 医师认证审核不通过消息
     *
     * @param apply
     */
    private void sendApproveNotPassMessage(MedicalHospitalApply apply) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
        String type = "医馆";
        String reason = apply.getRemark();
        String content = MessageFormat.format(APPROVE_NOT_PASS_MESSAGE, type, reason);
        Map<String, String> params = new HashMap<>(2);
        params.put("type", type);

        Map<String, String> weixinParams = new HashMap<>(5);
        weixinParams.put("first", TextStyleUtil.clearStyle(content).replace("查看详情", ""));
        weixinParams.put("keyword1", apply.getName());
        weixinParams.put("keyword2", TimeUtil.getYearMonthDayHHmm(new Date()));
        weixinParams.put("keyword3", "认证未通过");
        weixinParams.put("remark", "");
        commonMessageService.saveMessage(new BaseMessage.Builder(MessageTypeEnum.SYSYTEM.getVal())
                .buildWeb(content)
                .buildAppPush(TextStyleUtil.clearStyle(content))
                .buildWeixin(weixinAnchorApproveNotPassCode, weixinParams)
                .buildSms(smsAnchorApproveNotPassCode, params)
                .build(apply.getUserId(), RouteTypeEnum.HOSPITAL_APPROVE_PAGE, ManagerUserUtil.getId()));
    }

}
