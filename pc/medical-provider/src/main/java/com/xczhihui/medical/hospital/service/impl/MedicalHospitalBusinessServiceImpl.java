package com.xczhihui.medical.hospital.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorApplyDepartment;
import com.xczhihui.bxg.online.common.domain.MedicalDoctorDepartment;
import com.xczhihui.medical.department.model.MedicalDepartment;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAuthenticationInformationMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorDepartmentMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorFieldMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorAuthenticationInformation;
import com.xczhihui.medical.doctor.model.MedicalDoctorField;
import com.xczhihui.medical.field.mapper.MedicalFieldMapper;
import com.xczhihui.medical.field.model.MedicalField;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalAccountMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalFieldMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalPictureMapper;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;
import com.xczhihui.medical.hospital.model.MedicalHospitalField;
import com.xczhihui.medical.hospital.model.MedicalHospitalPicture;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  医馆业务接口类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
@Service
public class MedicalHospitalBusinessServiceImpl extends ServiceImpl<MedicalHospitalMapper, MedicalHospital> implements IMedicalHospitalBusinessService {

    @Autowired
    private MedicalHospitalMapper medicalHospitalMapper;
    @Autowired
    private MedicalHospitalAccountMapper hospitalAccountMapper;
    @Autowired
    private MedicalDoctorMapper medicalDoctorMapper;
    @Autowired
    private MedicalDoctorFieldMapper doctorFieldMapper;
    @Autowired
    private MedicalDoctorAuthenticationInformationMapper doctorAuthenticationInformationMapper;
    @Autowired
    private MedicalHospitalPictureMapper hospitalPictureMapper;
    @Autowired
    private MedicalHospitalFieldMapper hospitalFieldMapper;
    @Autowired
    private MedicalFieldMapper fieldMapper;
    @Autowired
    private MedicalDoctorDepartmentMapper doctorDepartmentMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Page<MedicalHospitalVo> selectHospitalPage(Page<MedicalHospitalVo> page, String name, String field) {
        List<String> mhIds = medicalHospitalMapper.selectHospitalIdList(page, name, field);
        if(mhIds.size()>0){
            List<MedicalHospitalVo> medicalHospitals = medicalHospitalMapper.selectHospitalAndPictureList(mhIds);
            page.setRecords(medicalHospitals);
        }
        return page;
    }

    @Override
    public MedicalHospitalVo selectHospitalById(String id) {
        MedicalHospitalVo medicalHospitalVo = medicalHospitalMapper.selectHospitalById(id);
        return medicalHospitalVo;
    }

    @Override
    public List<MedicalHospitalVo> selectRecHospital() {
        return medicalHospitalMapper.selectRecHospital();
    }

    /**
     * 获取医馆的医师列表
     * @param page 分页封装
     * @param doctorName 医师名字
     * @param userId 医馆id
     * @author zhuwenbao
     */
    @Override
    public Page<MedicalDoctor> selectDoctorPage(Page<MedicalDoctor> page, String doctorName, String userId) {

        // 根据userId获取他的认证医馆信息
        MedicalHospitalAccount hospitalAccount = hospitalAccountMapper.getByUserId(userId);
        if(hospitalAccount == null){
            throw new RuntimeException("您没有认证医馆");
        }

        List<MedicalDoctor> medicalDoctorList =
                medicalHospitalMapper.selectDoctorList(page, doctorName, hospitalAccount.getDoctorId());
        if(CollectionUtils.isNotEmpty(medicalDoctorList)){
            for(MedicalDoctor doctor : medicalDoctorList){
                // 根据id获取医师头像
                MedicalDoctorAuthenticationInformation authenticationInformation =
                        doctorAuthenticationInformationMapper.selectById(doctor.getAuthenticationInformationId());
                if(authenticationInformation != null){
                    doctor.setHeadPortrait(authenticationInformation.getHeadPortrait());
                }
                // 获取医师的领域
                Map<String, Object> columnMap = new HashMap<>();
                columnMap.put("doctor_id", doctor.getId());
                List<MedicalDoctorField> doctorFields = doctorFieldMapper.selectByMap(columnMap);
                if(CollectionUtils.isNotEmpty(doctorFields)){
                    List<String> idList = new ArrayList<>();
                    for(MedicalDoctorField doctorField : doctorFields){
                        idList.add(doctorField.getFieldId());
                    }
                    List<MedicalField> medicalFields = fieldMapper.selectBatchIds(idList);
                    doctor.setFields(medicalFields);
                }
            }
        }
        return page.setRecords(medicalDoctorList);
    }

    @Override
    public List<MedicalFieldVO> getHotField() {
        return medicalHospitalMapper.getHotField();
    }

    /**
     * 添加医师
     * @author zhuwenbao
     */
    @Override
    public void addDoctor(MedicalDoctor medicalDoctor) {

        // 参数校验
        this.validate(medicalDoctor);

        // 获取用户的医馆
        MedicalHospitalAccount hospitalAccount =
                hospitalAccountMapper.getByUserId(medicalDoctor.getUserId());
        if(hospitalAccount == null){
            throw new RuntimeException("您尚为认证医馆，请认证后再添加");
        }
        // 判断医馆是否认证
        if(!medicalHospitalMapper.getAuthenticationById(hospitalAccount.getDoctorId())){
            throw new RuntimeException("医馆尚未认证，请认证后再添加");
        }

        String doctorId = UUID.randomUUID().toString().replace("-","");
        String doctorAuthenticationId = UUID.randomUUID().toString().replace("-","");
        Date now = new Date();

        // 保存医师信息
        medicalDoctor.setId(doctorId);
        medicalDoctor.setDeleted(false);
        medicalDoctor.setStatus(true);
        medicalDoctor.setCreatePerson(medicalDoctor.getUserId());
        medicalDoctor.setAuthenticationInformationId(doctorAuthenticationId);
        medicalDoctor.setCreateTime(now);
        medicalDoctorMapper.insertSelective(medicalDoctor);

        // 将医师的头像,职称证明添加到认证表上：medical_doctor_authentication_information
        MedicalDoctorAuthenticationInformation authenticationInformation =
                medicalDoctor.getMedicalDoctorAuthenticationInformation();
        authenticationInformation.setId(doctorAuthenticationId);
        authenticationInformation.setHeadPortrait(medicalDoctor.getHeadPortrait());
        authenticationInformation.setCreatePerson(medicalDoctor.getUserId());
        doctorAuthenticationInformationMapper.insert(authenticationInformation);

        // 保存医师的科室：medical_doctor_department
        // 将MedicalDoctorApplyDepartment数据格式转化成：MedicalDoctorDepartment
        List<MedicalDepartment> departments = medicalDoctor.getDepartments();

        if(CollectionUtils.isNotEmpty(departments)){
            departments.stream()
                    .forEach(department -> this.addMedicalDepartment(department, doctorId, now));
        }

        logger.info("user : {} add doctor successfully, doctorId : {}",
                medicalDoctor.getUserId(), doctorId);
    }

    private void addMedicalDepartment(MedicalDepartment department, String doctorId, Date createTime){
        MedicalDoctorDepartment doctorDepartment = new MedicalDoctorDepartment();
        doctorDepartment.setId(UUID.randomUUID().toString().replace("-",""));
        doctorDepartment.setDoctorId(doctorId);
        doctorDepartment.setDepartmentId(department.getId());
        doctorDepartment.setCreateTime(createTime);
        doctorDepartmentMapper.insert(doctorDepartment);
    }

    /**
     * 获取医疗领域（分页）
     * @param page 分页对象
     * @return 医疗领域列表
     */
    @Override
    public Page<MedicalFieldVO> getFieldsPage(Page page) {

        return page.setRecords(medicalHospitalMapper.getFieldsPage(page));
    }

    /**
     * 修改医馆信息
     * @author zhuwenbao
     */
    @Override
    public void update(MedicalHospital medicalHospital) {

        this.validate(medicalHospital);

        Date now = new Date();
        medicalHospital.setUpdateTime(now);

        if(CollectionUtils.isNotEmpty(medicalHospital.getMedicalHospitalPictures())){

            // 删除之前的医馆照片
            hospitalPictureMapper.updateDeletedByHospitalId(medicalHospital.getId(), true);

            List<MedicalHospitalPicture> hospitalPictures = medicalHospital.getMedicalHospitalPictures();
            hospitalPictures.stream()
                    .filter(hospitalPicture -> StringUtils.isNotBlank(hospitalPicture.getPicture()))
                    .forEach(hospitalPicture -> {
                        hospitalPicture.setId(UUID.randomUUID().toString().replace("-",""));
                        hospitalPicture.setHospitalId(medicalHospital.getId());
                        hospitalPicture.setDeleted(false);
                        hospitalPicture.setStatus(true);
                        hospitalPicture.setCreateTime(now);
                    });

            hospitalPictureMapper.insertBatch(hospitalPictures);
        }

        if(CollectionUtils.isNotEmpty(medicalHospital.getFields())){

            // 删除之前的医馆领域
            hospitalFieldMapper.updateDeletedByHospitalId(medicalHospital.getId(), true);

            // 新增-新的领域
            List<MedicalField> fields = medicalHospital.getFields();
            List<MedicalHospitalField> hospitalFields = new ArrayList<>();

            for (MedicalField field : fields){

                MedicalHospitalField hospitalField = new MedicalHospitalField();
                hospitalField.setId(UUID.randomUUID().toString().replace("-",""));
                hospitalField.setHospitalId(medicalHospital.getId());
                hospitalField.setFieldId(field.getId());
                hospitalField.setDeleted(false);
                hospitalField.setCreateTime(now);

                hospitalFields.add(hospitalField);
            }

            hospitalFieldMapper.insertBatch(hospitalFields);
        }

        medicalHospitalMapper.updateSelective(medicalHospital);
    }

    /**
     * 参数校验
     * @param medicalHospital 被校验的参数
     */
    private void validate(MedicalHospital medicalHospital) {

        if(medicalHospital == null || StringUtils.isBlank(medicalHospital.getId())){
            throw new RuntimeException("请选择要修改的医馆");
        }

        if(StringUtils.isBlank(medicalHospital.getHeadPortrait())){
            throw new RuntimeException("请上传医馆头像");
        }

        if(CollectionUtils.isEmpty(medicalHospital.getMedicalHospitalPictures())){
            throw new RuntimeException("请上传医馆图片");
        }

        if(CollectionUtils.isEmpty(medicalHospital.getFields())){
            throw new RuntimeException("请选择医疗领域");
        }

        if(StringUtils.isBlank(medicalHospital.getDescription())){
            throw new RuntimeException("请填写医馆介绍");
        }

        if(StringUtils.isBlank(medicalHospital.getContactor())){
            throw new RuntimeException("请填写医馆联系人");
        }

        if(StringUtils.isBlank(medicalHospital.getProvince())){
            throw new RuntimeException("请选择医馆所在省份");
        }

        if(StringUtils.isBlank(medicalHospital.getCity())){
            throw new RuntimeException("请填写医馆所在城市");
        }

    }

    /**
     * 参数校验
     * @param medicalDoctor 被校验的参数
     */
    private void validate(MedicalDoctor medicalDoctor) {

        if(StringUtils.isBlank(medicalDoctor.getName())){
            throw new RuntimeException("医师名字不能为空");
        }

        if(StringUtils.isBlank(medicalDoctor.getHeadPortrait())){
            throw new RuntimeException("医师头像不能为空");
        }

        if(StringUtils.isBlank(medicalDoctor.getTitle())){
            throw new RuntimeException("医师职称不能为空");
        }

        if(medicalDoctor.getMedicalDoctorAuthenticationInformation() == null){
            throw new RuntimeException("请上传职称证明");
        }else{
            if(StringUtils.isBlank(medicalDoctor.getMedicalDoctorAuthenticationInformation().getTitleProve())){
                throw new RuntimeException("请上传职称证明");
            }
        }

        if(StringUtils.isBlank(medicalDoctor.getFieldText())){
            throw new RuntimeException("擅长不能为空");
        }

        if(CollectionUtils.isEmpty(medicalDoctor.getDepartments())){
            throw new RuntimeException("请选择科室");
        }

        if(StringUtils.isBlank(medicalDoctor.getDescription())){
            throw new RuntimeException("医师介绍不能为空");
        }else{
            if(medicalDoctor.getDescription().length() > 500){
                throw new RuntimeException("医师介绍字数应在500字以内");
            }
        }

    }
}
