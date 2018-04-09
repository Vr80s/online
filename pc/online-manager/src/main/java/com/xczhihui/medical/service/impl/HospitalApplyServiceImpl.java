package com.xczhihui.medical.service.impl;

import com.xczhihui.anchor.dao.AnchorDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.CourseAnchor;
import com.xczhihui.bxg.online.common.consts.MedicalHospitalApplyConst;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.bxg.common.util.enums.AnchorType;
import com.xczhihui.bxg.online.common.utils.RedissonUtil;
import com.xczhihui.medical.dao.*;
import com.xczhihui.medical.service.HospitalApplyService;
import com.xczhihui.user.dao.UserDao;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.vhall.VhallUtil;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 医师入驻申请服务实现层
 * @author zhuwenbao
 */
@Service
public class HospitalApplyServiceImpl implements HospitalApplyService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

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

    @Value("${course.anchor.vod_divide}")
    private BigDecimal vodDivide;

    @Value("${course.anchor.live_divide}")
    private BigDecimal liveDivide;

    @Value("${course.anchor.offline_divide}")
    private BigDecimal offlineDivide;

    @Value("${course.anchor.gift_divide}")
    private BigDecimal giftDivide;

    /**
     * 获取医师入驻申请列表
     * @param searchVo 查询条件
     * @param currentPage 当前页
     * @param pageSize 每页显示的列数
     * @return 医师入驻申请分页列表
     */
    @Override
    public Page<MedicalHospitalApply> list(MedicalHospitalApply searchVo, int currentPage, int pageSize) {

        return hospitalApplyDao.list(searchVo, currentPage, pageSize);
    }

    /**
     * 更新医师入驻申请状态
     * @param hospitalApply 更新的数据封装类
     */
    @Override
    public void updateStatus(MedicalHospitalApply hospitalApply) {
        if(hospitalApply == null){
            return;
        }

        String id = hospitalApply.getId();
        Integer status = hospitalApply.getStatus();

        if(StringUtils.isBlank(id)){
            throw new RuntimeException("请选择要修改的目标");
        }
        if(status == null){
            throw new RuntimeException("缺少请求参数：status");
        }

        // 根据id获取被修改的认证信息详情
        MedicalHospitalApply apply = hospitalApplyDao.find(id);

        if(apply == null){
            throw new RuntimeException("修改的目标不存在");
        }

        RLock lock = redissonUtil.getRedisson().getLock(MedicalHospitalApplyConst.LOCK_PREFIX + apply.getId());
        boolean getLock = false;

        try {

            if(getLock = lock.tryLock(0,5, TimeUnit.SECONDS)){

                if(apply == null){
                    throw new RuntimeException("操作失败：该条信息不存在");
                }
                // 如果该条记录已经被通过或者被拒绝 不能再修改
                if(apply.getStatus().equals(0)){
                    throw new RuntimeException("该条认证已经被拒，不能再修改");
                }
                if(apply.getStatus().equals(1)){
                    throw new RuntimeException("该条认证已经认证成功，不能再修改");
                }
                // 前台传来的状态和数据库的状态一致 不予处理
                if(apply.getStatus().equals(status)){
                    return;
                }
                // 如果该条信息被删除 不能再修改
                if(apply.getDeleted()){
                    throw new RuntimeException("该条认证已经被删除，用户已重新认证");
                }

                switch (status){
                    // 当status = 0 即认证被拒
                    case 0:
                        this.authenticationRejectHandle();
                        break;
                    // 当status = 1 即认证通过
                    case 1:
                        this.authenticationPassHandle(apply);
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

            logger.error("-------------- redisson get lock interruptedException：" + e);

        }finally {

            if(!getLock){
                return;
            }

            lock.unlock();

            logger.info("--------------  redisson release lock");
        }
    }

    /**
     * 根据id查询详情
     * @param applyId
     */
    @Override
    public MedicalHospitalApply findById(String applyId) {

        return hospitalApplyDao.find(applyId);

    }

    /**
     * 处理认证通过逻辑
     */
    private void authenticationPassHandle(MedicalHospitalApply apply) {

        Date now = new Date();

        String hospitalId = UUID.randomUUID().toString().replace("-","");

        // 判断用户是否已经是认证医师（医师 医馆只能认证一个）
        MedicalDoctorAccount doctorAccount = doctorAccountDao.findByAccountId(apply.getUserId());
        if(doctorAccount != null){
            throw new RuntimeException("该用户已是医师，不能再进行认证医馆");
        }

        // 判断用户是否已经已拥有验证医馆
        MedicalHospitalAccount hospitalAccount = hospitalAccountDao.findByAccountId(apply.getUserId());
        if(hospitalAccountDao.findByAccountId(apply.getUserId()) != null &&
                StringUtils.isNotBlank(hospitalAccount.getDoctorId())){
            MedicalHospital hospital = hospitalDao.find(hospitalAccount.getDoctorId());
            if(hospital != null && hospital.isAuthentication() == true){
                throw new RuntimeException("该用户已拥有已认证医馆，不能再进行认证");
            }
        }

        // 新增医师与用户的对应关系：medical_hospital_account
        MedicalHospitalAccount newHospitalAccount =
                new MedicalHospitalAccount();
        newHospitalAccount.setId(UUID.randomUUID().toString().replace("-",""));
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
        hospital.setStatus(false);
        String authenticationInformationId = UUID.randomUUID().toString().replace("-","");
        hospital.setAuthenticationId(authenticationInformationId);
        hospital.setSourceId(apply.getId());
        hospitalDao.save(hospital);

        // 新增认证成功信息：medical_hospital_authentication
        MedicalHospitalAuthentication authenticationInformation =
                new MedicalHospitalAuthentication();
        authenticationInformation.setCompany(apply.getCompany());
        authenticationInformation.setId(authenticationInformationId);
        authenticationInformation.setBusinessLicenseNo(apply.getBusinessLicenseNo());
        authenticationInformation.setBusinessLicensePicture(apply.getBusinessLicensePicture());
        authenticationInformation.setLicenseForPharmaceuticalTrading(apply.getLicenseForPharmaceuticalTrading());
        authenticationInformation.setLicenseForPharmaceuticalTradingPicture(apply.getLicenseForPharmaceuticalTradingPicture());

        authenticationInformation.setDeleted(false);
        authenticationInformation.setCreateTime(now);
        hospitalAuthenticationDao.save(authenticationInformation);

        // 设置oe_user表中的is_lecturer为1
        userDao.updateIsLecturerById(1, apply.getUserId());

        //设置讲师权限
        OnlineUser user = onlineUserService.getOnlineUserByUserId(apply.getUserId());
        VhallUtil.changeUserPower(user.getVhallId(),  "1", "0");

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
        if(StringUtils.isNotBlank(user.getName())){
            courseAnchor.setName(user.getName());
        }
        if(StringUtils.isNotBlank(user.getSmallHeadPhoto())){
            courseAnchor.setProfilePhoto(user.getSmallHeadPhoto());
        }
        anchorDao.save(courseAnchor);
    }

    /**
     * 处理认证失败逻辑
     */
    private void authenticationRejectHandle(){

    }
}
