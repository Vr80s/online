package com.xczhihui.medical.service.impl;

import com.xczhihui.anchor.dao.AnchorDao;
import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.CourseAnchor;
import com.xczhihui.bxg.online.common.consts.MedicalDoctorApplyConst;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.bxg.common.util.enums.AnchorType;
import com.xczhihui.bxg.online.common.utils.RedissonUtil;
import com.xczhihui.medical.dao.*;
import com.xczhihui.medical.service.DoctorApplyService;
import com.xczhihui.user.dao.UserDao;
import com.xczhihui.user.service.OnlineUserService;
import com.xczhihui.utils.RandomUtil;
import com.xczhihui.vhall.VhallUtil;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 医师入驻申请服务实现层
 * @author zhuwenbao
 */
@Service
public class DoctorApplyServiceImpl implements DoctorApplyService {

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
    private RedissonUtil redissonUtil;

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
    public Page<MedicalDoctorApply> list(MedicalDoctorApply searchVo, int currentPage, int pageSize) {

        return doctorApplyDao.list(searchVo, currentPage, pageSize);
    }

    /**
     * 更新医师入驻申请状态
     * @param doctorApply 更新的数据封装类
     */
    @Override
    public void updateStatus(MedicalDoctorApply doctorApply) {
        if(doctorApply == null){
            return;
        }
        if(StringUtils.isBlank(doctorApply.getId())){
            throw new RuntimeException("请选择要修改的目标");
        }
        if(doctorApply.getStatus() == null){
            throw new RuntimeException("缺少请求参数：status");
        }

        String id = doctorApply.getId();
        Integer status = doctorApply.getStatus();

        // 根据id获取被修改的认证信息详情
        MedicalDoctorApply apply = doctorApplyDao.find(id);

        if(apply == null){
            throw new RuntimeException("修改的目标不存在");
        }

        RLock lock = redissonUtil.getRedisson().getLock(MedicalDoctorApplyConst.LOCK_PREFIX + apply.getId());
        boolean getLock = false;

        try {

            if(getLock = lock.tryLock(3,10, TimeUnit.SECONDS)){

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

                // 如果该条信息被删除 不能再修改
                if(apply.getDeleted()){
                    throw new RuntimeException("该条认证已经被删除，用户已重新认证");
                }

                switch (status){
                    // 当status = 1 即认证通过
                    case 1:
                        this.authenticationPassHandle(apply);
                        break;
                    // 当status = 0 即认证被拒
                    case 0:
                        this.authenticationRejectHandle();
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

            logger.error("-------------- redisson get lock interruptedException：" + e);

        }finally {

            if(!getLock){
                return;
            }

            lock.unlock();

            logger.info("--------------  redisson release lock");
        }

    }

    @Override
    public MedicalDoctorApply findById(String id) {

        MedicalDoctorApply medicalDoctorApply =  doctorApplyDao.find(id);
        if(medicalDoctorApply == null ){
            throw new RuntimeException("该条记录不存在");
        }

        List<MedicalDepartment> departments = new ArrayList<>();

        List<MedicalDoctorApplyDepartment> applyDepartments = doctorApplyDepartmentDao.findByApplyId(medicalDoctorApply.getId());
        if(applyDepartments != null && !applyDepartments.isEmpty()){
            for (MedicalDoctorApplyDepartment applyDepartment : applyDepartments){
                MedicalDepartment department = departmentDao.findById(applyDepartment.getDepartmentId());
                if(department != null){
                    departments.add(department);
                }
            }
        }

        if(departments != null && !departments.isEmpty()){
            medicalDoctorApply.setDepartments(departments);
        }

        return medicalDoctorApply;
    }

    /**
     * 兼容之前主播没有进行医师认证所缺少的数据
     */
    @Override
    public void afterApply(String userId) {

        // 判断之前的主播是否进行过医师认证
        String medicalDoctorApplyId = doctorApplyDao.findByHQLOne("select id from medical_doctor_apply where user_id = ?", userId);

        String authenticationInformationId = UUID.randomUUID().toString().replace("-","");
        String doctorId = UUID.randomUUID().toString().replace("-","");
        Date now = new Date();

        // 若没有
        if(medicalDoctorApplyId == null){

            // 新增一条医师认证申请通过的消息 ： medical_doctor_apply
            String doctorApplyId = UUID.randomUUID().toString().replace("-","");
            this.addMedicalDoctorApply(doctorApplyId, userId, now);

            // 新增一条医师申请时对应的科室信息:medical_doctor_apply_department
            // 同时新增一条认证医师与科室的对应关系：medical_doctor_department
            this.addMedicalDoctorApplyDepartment(doctorApplyId, doctorId, now);

            // 新增一条认证成功信息：medical_doctor_authentication_information
            this.addMedicalDoctorAuthenticationInformation(authenticationInformationId, null, 1, now);

        }

        // 若该主播和认证医师没有关联
        MedicalDoctorAccount doctorAccount = doctorAccountDao.findByAccountId(userId);
        if(doctorAccount == null){

            // 新增医师信息：medical_doctor
            this.addMedicalDoctor(doctorId, authenticationInformationId, null,1, now);

            // 新增一条主播和认证医师的关联关系 medical_doctor_account
            this.addMedicalDoctorAccount(userId, doctorId, now);

        }else{

            logger.error("用户id：{} 已经存在已认证医师：{}" , doctorAccount.getAccountId() , doctorAccount.getDoctorId());

        }

        // 若该用户没有主播信息
        if(anchorDao.findByHQLOne("select id from CourseAnchor where userId = ?", userId) == null){
            this.addCourseAnchor(userId, now);
        }
    }

    @Override
    public void afterApplyAll() {
        List<Map<String, Object>> userIds=onlineUserService.getAllUserLecturer();
        for(Map<String,Object > userId:userIds){
            afterApply((String) userId.get("id"));
        }
    }

    /**
     * 处理认证通过逻辑
     */
    private void authenticationPassHandle(MedicalDoctorApply apply) {

        // 判断用户是否已经认证医馆（医师 医馆只能认证一个）
        MedicalHospitalAccount hospitalAccount = hospitalAccountDao.findByAccountId(apply.getUserId());
        if(hospitalAccountDao.findByAccountId(apply.getUserId()) != null &&
                StringUtils.isNotBlank(hospitalAccount.getDoctorId())){
            MedicalHospital hospital = hospitalDao.find(hospitalAccount.getDoctorId());
            if(hospital != null && hospital.isAuthentication() == true){
                throw new RuntimeException("该用户已拥有已认证医馆，不能再进行认证医师");
            }
        }

        MedicalDoctorAccount doctorAccount = doctorAccountDao.findByAccountId(apply.getUserId());
        // 判断用户是否已经是医师 如果是，则表示其重新认证
        if(doctorAccount != null){

            this.applyAgain(apply, doctorAccount.getDoctorId());

            return;
        }

        Date now = new Date();
        String doctorId = UUID.randomUUID().toString().replace("-","");

        // 新增医师与用户的对应关系：medical_doctor_account
        this.addMedicalDoctorAccount(apply.getUserId(), doctorId, now);

        // 新增医师信息：medical_doctor
        String authenticationInformationId = UUID.randomUUID().toString().replace("-","");
        this.addMedicalDoctor(doctorId, authenticationInformationId, apply, 2, now);

        // 新增医师与科室的对应关系：medical_doctor_department
        List<MedicalDoctorApplyDepartment> medicalDoctorApplyDepartments =
                doctorApplyDepartmentDao.findByApplyId(apply.getId());

        // 将MedicalDoctorApplyDepartment数据格式转化成：MedicalDoctorDepartment
        if(medicalDoctorApplyDepartments != null && !medicalDoctorApplyDepartments.isEmpty()){
            medicalDoctorApplyDepartments
                    .stream()
                    .forEach(department -> this.addMedicalDepartment(department, doctorId, now));
        }

        // 新增认证成功信息：medical_doctor_authentication_information
        this.addMedicalDoctorAuthenticationInformation(authenticationInformationId, apply, 2, now);

        // 设置oe_user表中的is_lecturer为1
        userDao.updateIsLecturerById(1, apply.getUserId());

        //设置讲师权限
        OnlineUser user = onlineUserService.getOnlineUserByUserId(apply.getUserId());
        VhallUtil.changeUserPower(user.getVhallId(),  "1", "0");

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
        if(StringUtils.isNotBlank(user.getName())){
            courseAnchor.setName(user.getName());
        }
        if(StringUtils.isNotBlank(user.getSmallHeadPhoto())){
            courseAnchor.setProfilePhoto(user.getSmallHeadPhoto());
        }
        anchorDao.save(courseAnchor);
    }

    /**
     * 重新认证
     */
    private void applyAgain(MedicalDoctorApply apply, String doctorId) {

        Date now = new Date();

        // 获取用户的医师信息
        MedicalDoctor doctor = doctorDao.find(doctorId);

        MedicalDoctorAuthenticationInformation authenticationInformation =
                new MedicalDoctorAuthenticationInformation();

        authenticationInformation.setId(doctor.getAuthenticationInformationId());
        authenticationInformation.setUpdatePerson(apply.getUserId());
        authenticationInformation.setUpdateTime(now);
        authenticationInformation.setCardNegative(apply.getCardNegative());
        authenticationInformation.setCardPositive(apply.getCardPositive());
        authenticationInformation.setQualificationCertificate(apply.getQualificationCertificate());
        authenticationInformation.setProfessionalCertificate(apply.getProfessionalCertificate());
        if(StringUtils.isNotBlank(apply.getTitleProve())){
            authenticationInformation.setTitleProve(apply.getTitleProve());
        }
        if(StringUtils.isNotBlank(apply.getHeadPortrait())){
            authenticationInformation.setHeadPortrait(apply.getHeadPortrait());
        }

        // 更新用户的医师认证信息
        doctorAuthenticationInformationDao.update(authenticationInformation);

        // 更新医师信息
        doctor.setName(apply.getName());
        doctor.setCardNum(apply.getCardNum());
        if(StringUtils.isNotBlank(apply.getTitle())){
            doctor.setTitle(apply.getTitle());
        }
        if(StringUtils.isNotBlank(apply.getField())){
            doctor.setFieldText(apply.getField());
        }
        if(StringUtils.isNotBlank(apply.getDescription())){
            doctor.setDescription(apply.getDescription());
        }
        if(StringUtils.isNotBlank(apply.getProvince())){
            doctor.setProvince(apply.getProvince());
        }
        if(StringUtils.isNotBlank(apply.getCity())){
            doctor.setCity(apply.getCity());
        }
        if(StringUtils.isNotBlank(apply.getDetailedAddress())){
            doctor.setDetailedAddress(apply.getDetailedAddress());
        }
        doctor.setSourceId(apply.getId());
        doctorDao.update(doctor);

        // 新增医师与科室的对应关系：medical_doctor_department
        List<MedicalDoctorApplyDepartment> medicalDoctorApplyDepartments =
                doctorApplyDepartmentDao.findByApplyId(apply.getId());

        // 将MedicalDoctorApplyDepartment数据格式转化成：MedicalDoctorDepartment
        if(medicalDoctorApplyDepartments != null && !medicalDoctorApplyDepartments.isEmpty()){
            medicalDoctorApplyDepartments
                    .stream()
                    .forEach(department -> this.addMedicalDepartment(department, doctorId, now));
        }

    }

    private void addMedicalDepartment(MedicalDoctorApplyDepartment department, String doctorId, Date createTime){
        MedicalDoctorDepartment doctorDepartment = new MedicalDoctorDepartment();
        doctorDepartment.setId(UUID.randomUUID().toString().replace("-",""));
        doctorDepartment.setDoctorId(doctorId);
        doctorDepartment.setDepartmentId(department.getDepartmentId());
        doctorDepartment.setCreateTime(createTime);
        doctorDepartmentDao.save(doctorDepartment);
    }

    /**
     * 处理认证失败逻辑
     */
    private void authenticationRejectHandle(){

    }

    /**
     * 新增医师认证申请信息（往medical_doctor_apply表插入一条数据）
     * @param doctorApplyId medical_doctor_apply表主键
     * @param userId    用户id
     * @param createTime 创建时间
     */
    private void addMedicalDoctorApply(String doctorApplyId, String userId, Date createTime) {
        MedicalDoctorApply doctorApply = new MedicalDoctorApply();
        doctorApply.setId(doctorApplyId);
        doctorApply.setUserId(userId);
        doctorApply.setName(userId);
        doctorApply.setCardNum(RandomUtil.getCharAndNumr(18));
        String picture = "http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg";
        doctorApply.setHeadPortrait(picture);
        doctorApply.setCardNegative(picture);
        doctorApply.setCardPositive(picture);
        doctorApply.setProfessionalCertificate(picture);
        doctorApply.setTitleProve(picture);
        doctorApply.setQualificationCertificate(picture);
        doctorApply.setTitle("主治医师");
        doctorApply.setField("妇科疾病");
        doctorApply.setDescription("你是什么样的人，就得什么样的病");
        doctorApply.setHospital("仟草堂");
        doctorApply.setStatus(1);
        doctorApply.setDeleted(false);
        doctorApply.setCreateTime(createTime);
        doctorApply.setUpdateTime(createTime);
        doctorApply.setProvince("海南");
        doctorApply.setCity("海口");
        doctorApply.setDetailedAddress("演丰熊猫基地");
        doctorApply.setRemark("系统生成,仅供测试");

        doctorApplyDao.save(doctorApply);
    }

    /**
     * 新增一条认证成功信息(往medical_doctor_authentication_information表插入一条信息）
     * @param authenticationInformationId medical_doctor_authentication_information表主键
     * @param apply  认证信息的封装
     * @param type 新增数据的类型（1：系统生成 2:管理员认证）
     * @param createTime 创建时间
     */
    private void addMedicalDoctorAuthenticationInformation(String authenticationInformationId,
                                                           MedicalDoctorApply apply,
                                                           int type, Date createTime) {
        MedicalDoctorAuthenticationInformation authenticationInformation =
                new MedicalDoctorAuthenticationInformation();

        if(type == 1){
            authenticationInformation.setId(authenticationInformationId);
            String picture = "http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg";
            authenticationInformation.setHeadPortrait(picture);
            authenticationInformation.setCardNegative(picture);
            authenticationInformation.setCardPositive(picture);
            authenticationInformation.setProfessionalCertificate(picture);
            authenticationInformation.setTitleProve(picture);
            authenticationInformation.setQualificationCertificate(picture);
            authenticationInformation.setDeleted(false);
            authenticationInformation.setStatus(true);
            authenticationInformation.setCreateTime(createTime);
            authenticationInformation.setRemark("系统生成,仅供测试");
            doctorAuthenticationInformationDao.save(authenticationInformation);
        }
        if(type == 2){
            authenticationInformation.setId(authenticationInformationId);
            if(StringUtils.isNotBlank(apply.getCardPositive())){
                authenticationInformation.setCardPositive(apply.getCardPositive());
            }
            if(StringUtils.isNotBlank(apply.getCardNegative())){
                authenticationInformation.setCardNegative(apply.getCardNegative());
            }
            if(StringUtils.isNotBlank(apply.getQualificationCertificate())){
                authenticationInformation.setQualificationCertificate(apply.getQualificationCertificate());
            }
            if(StringUtils.isNotBlank(apply.getTitleProve())){
                authenticationInformation.setTitleProve(apply.getTitleProve());
            }
            if(StringUtils.isNotBlank(apply.getProfessionalCertificate())){
                authenticationInformation.setProfessionalCertificate(apply.getProfessionalCertificate());
            }
            if(StringUtils.isNotBlank(apply.getHeadPortrait())){
                authenticationInformation.setHeadPortrait(apply.getHeadPortrait());
            }

            authenticationInformation.setDeleted(false);
            authenticationInformation.setStatus(true);
            authenticationInformation.setCreateTime(createTime);
            doctorAuthenticationInformationDao.save(authenticationInformation);
        }
    }

    /**
     * 新增医师信息(往medical_doctor表插入一条信息）
     * @param doctorId medical_doctor表的主键
     * @param authenticationInformationId medical_doctor_authentication_information表的主键
     * @param apply 用户提交的认证信息封装
     * @param type 新增类型（1：系统生成 2:管理员认证）
     * @param createTime 创建时间
     */
    private void addMedicalDoctor(String doctorId, String authenticationInformationId,
                                  MedicalDoctorApply apply, int type, Date createTime) {

        MedicalDoctor doctor = new MedicalDoctor();

        if(type == 1){
            doctor.setId(doctorId);
            doctor.setAuthenticationInformationId(authenticationInformationId);
            doctor.setName(doctorId);
            doctor.setTitle("主治医师");
            doctor.setDescription("你是什么样的人，就得什么样的病");
            doctor.setCreateTime(createTime);
            doctor.setRemark("系统生成,仅供测试");
            doctor.setDeleted(false);
            doctor.setType("2");
            doctor.setProvince("北京省");
            doctor.setCity("北京市");
            doctor.setWorkTime("周一下午、周四上午");
            doctor.setStatus(true);
            doctor.setCardNum("7758258");
            doctorDao.save(doctor);
        }
        if(type == 2){
            doctor.setId(doctorId);
            doctor.setAuthenticationInformationId(authenticationInformationId);
            doctor.setName(apply.getName());
            doctor.setCardNum(apply.getCardNum());
            if(StringUtils.isNotBlank(apply.getTitle())){
                doctor.setTitle(apply.getTitle());
            }
            if(StringUtils.isNotBlank(apply.getDescription())){
                doctor.setDescription(apply.getDescription());
            }
            if(StringUtils.isNotBlank(apply.getProvince())){
                doctor.setProvince(apply.getProvince());
            }
            if(StringUtils.isNotBlank(apply.getCity())){
                doctor.setCity(apply.getCity());
            }
            if(StringUtils.isNotBlank(apply.getDetailedAddress())){
                doctor.setDetailedAddress(apply.getDetailedAddress());
            }
            if(StringUtils.isNotBlank(apply.getField())){
                doctor.setFieldText(apply.getField());
            }
            doctor.setStatus(true);
            doctor.setCreateTime(createTime);
            doctor.setSourceId(apply.getId());
            doctorDao.save(doctor);
        }
    }

    /**
     * 新增一条用户和认证医师的关联关系 medical_doctor_account
     * @param userId 用户id
     * @param doctorId 医师id
     * @param createTime 创建时间
     */
    private void addMedicalDoctorAccount(String userId, String doctorId, Date createTime) {
        MedicalDoctorAccount doctorAccount =
                new MedicalDoctorAccount();
        doctorAccount.setId(UUID.randomUUID().toString().replace("-",""));
        doctorAccount.setAccountId(userId);
        doctorAccount.setDoctorId(doctorId);
        doctorAccount.setCreateTime(createTime);
        doctorAccountDao.save(doctorAccount);
    }

    /**
     * 新增一条主播信息
     * @param userId 用户id
     * @param createTime 创建时间
     */
    private void addCourseAnchor(String userId, Date createTime) {

        CourseAnchor courseAnchor = new CourseAnchor();
        courseAnchor.setUserId(userId);
        courseAnchor.setVideo("点击右上角的订阅，今年就会长长18cm");
        courseAnchor.setDetail("我于杀戮中绽放，亦如黎明中的花朵");
        courseAnchor.setType(AnchorType.DOCTOR.getCode());
        courseAnchor.setDeleted(false);
        courseAnchor.setStatus(true);
        courseAnchor.setCreateTime(createTime);
        courseAnchor.setRemark("系统生成，仅供测试");
        courseAnchor.setVodDivide(vodDivide);
        courseAnchor.setLiveDivide(liveDivide);
        courseAnchor.setOfflineDivide(offlineDivide);
        courseAnchor.setGiftDivide(giftDivide);
        anchorDao.save(courseAnchor);

    }

    /**
     * 新增一条医师申请时对应的科室信息
     * @param doctorApplyId medical_doctor_apply表id
     * @param doctorId 医师id
     * @param createTime 创建时间
     */
    private void addMedicalDoctorApplyDepartment(String doctorApplyId, String doctorId, Date createTime) {
        String departmentId = departmentDao.findByHQLOne("select id from MedicalDepartment where createTime = (select max(createTime) from MedicalDepartment)");
        if(StringUtils.isNotBlank(departmentId)){
            MedicalDoctorApplyDepartment applyDepartment = new MedicalDoctorApplyDepartment();
            applyDepartment.setId(UUID.randomUUID().toString().replace("-",""));
            applyDepartment.setCreateTime(createTime);
            applyDepartment.setDepartmentId(departmentId);
            applyDepartment.setDoctorApplyId(doctorApplyId);
            doctorApplyDepartmentDao.save(applyDepartment);

            MedicalDoctorDepartment doctorDepartment = new MedicalDoctorDepartment();
            doctorDepartment.setId(UUID.randomUUID().toString().replace("-",""));
            doctorDepartment.setDoctorId(doctorId);
            doctorDepartment.setDepartmentId(departmentId);
            doctorDepartmentDao.save(doctorDepartment);

        }else{
            throw new RuntimeException("medical_department表没有数据");
        }
    }
}
