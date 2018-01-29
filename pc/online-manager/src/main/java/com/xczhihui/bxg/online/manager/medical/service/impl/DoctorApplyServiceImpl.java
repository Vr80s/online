package com.xczhihui.bxg.online.manager.medical.service.impl;

import com.xczhihui.bxg.common.util.bean.Page;
import com.xczhihui.bxg.online.api.po.CourseAnchor;
import com.xczhihui.bxg.online.common.domain.*;
import com.xczhihui.bxg.online.manager.anchor.dao.AnchorDao;
import com.xczhihui.bxg.online.manager.medical.dao.*;
import com.xczhihui.bxg.online.manager.medical.service.DoctorApplyService;
import com.xczhihui.bxg.online.manager.user.dao.UserDao;
import com.xczhihui.bxg.online.manager.utils.RandomUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 医师入驻申请服务实现层
 * @author zhuwenbao
 */
@Service
public class DoctorApplyServiceImpl implements DoctorApplyService {

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
            throw new RuntimeException("操作失败：该条信息不存在");
        }

        // 如果该条记录已经被通过或者被拒绝 不能再修改
        if(apply.getStatus().equals("0")){
            throw new RuntimeException("该条认证已经被拒，不能再修改");
        }
        if(apply.getStatus().equals("1")){
            throw new RuntimeException("该条认证已经认证成功，不能再修改");
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

    @Override
    public MedicalDoctorApply findById(String id) {

        MedicalDoctorApply medicalDoctorApply =  doctorApplyDao.find(id);

        if(medicalDoctorApply == null ){
            throw new RuntimeException("该条记录不存在");
        }

        List<MedicalDepartment> departments = new ArrayList<>();

        List<MedicalDoctorApplyDepartment> applyDepartments =
            doctorApplyDepartmentDao.findByApplyId(medicalDoctorApply.getId());

        if(applyDepartments != null && !applyDepartments.isEmpty()){
            for (MedicalDoctorApplyDepartment applyDepartment : applyDepartments){
                MedicalDepartment department =
                        departmentDao.findById(applyDepartment.getDepartmentId());
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

        // 判断之前的主播是否没有进行过医师认证
        Integer medicalDoctorApplyId =
            doctorApplyDao.findByHQLOne("select id from medical_doctor_apply where user_id = ?", userId);

        String authenticationInformationId = UUID.randomUUID().toString().replace("-","");
        Date now = new Date();

        // 若没有
        if(medicalDoctorApplyId == null){

            // 新增一条医师认证申请通过的消息 ： medical_doctor_apply
            String doctorApplyId = UUID.randomUUID().toString().replace("-","");
            this.addMedicalDoctorApply(doctorApplyId, userId, now);

            // 新增一条认证成功信息：medical_doctor_authentication_information
            this.addMedicalDoctorAuthenticationInformation(authenticationInformationId, null, 1, now);

        }

        // 若该主播和认证医师没有关联
        if(doctorAccountDao.findByAccountId(userId) == null){

            // 新增医师信息：medical_doctor
            String doctorId = UUID.randomUUID().toString().replace("-","");
            this.addMedicalDoctor(doctorId, authenticationInformationId, null,1, now);

            // 新增一条主播和认证医师的关联关系 medical_doctor_account
            this.addMedicalDoctorAccount(userId, doctorId, now);
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

        Date now = new Date();

        String doctorId = UUID.randomUUID().toString().replace("-","");

        // 新增医师与用户的对应关系：medical_doctor_account
        // 判断用户是否已经是医师
        if(doctorAccountDao.findByAccountId(apply.getUserId()) != null){
            throw new RuntimeException("该用户已是医师，不能在进行认证");
        }

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

        // course_anchor` 表中新增一条信息
        CourseAnchor courseAnchor = new CourseAnchor();
        courseAnchor.setUserId(apply.getUserId());
        courseAnchor.setType(1);
        courseAnchor.setCreateTime(new Date());
        courseAnchor.setLiveDivide(liveDivide);
        courseAnchor.setOfflineDivide(offlineDivide);
        courseAnchor.setVodDivide(vodDivide);
        courseAnchor.setGiftDivide(giftDivide);
        courseAnchor.setDeleted(false);
        courseAnchor.setStatus(true);
        anchorDao.save(courseAnchor);
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
        doctorApply.setHeadPortrait("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
        doctorApply.setCardNegative("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
        doctorApply.setCardPositive("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
        doctorApply.setProfessionalCertificate("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
        doctorApply.setTitleProve("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
        doctorApply.setQualificationCertificate("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
        doctorApply.setTitle("主治医师");
        doctorApply.setField("妇科疾病");
        doctorApply.setDescription("你是什么样的人，就得什么样的病");
        doctorApply.setHospital("仟草堂");
        doctorApply.setStatus(1);
        doctorApply.setDeleted(false);
        doctorApply.setCreateTime(createTime);
        doctorApply.setUpdateTime(createTime);
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
            authenticationInformation.setHeadPortrait("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
            authenticationInformation.setCardNegative("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
            authenticationInformation.setCardPositive("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
            authenticationInformation.setProfessionalCertificate("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
            authenticationInformation.setTitleProve("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
            authenticationInformation.setQualificationCertificate("http://test-www.ixincheng.com:38080/data/picture/online/2017/12/16/00/50d34185ef86451e9d13651b4591031e.jpg");
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
            doctorDao.save(doctor);
        }
        if(type == 2){
            doctor.setId(doctorId);
            doctor.setAuthenticationInformationId(authenticationInformationId);
            doctor.setName(apply.getName());
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
            doctor.setCreateTime(createTime);
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
}
