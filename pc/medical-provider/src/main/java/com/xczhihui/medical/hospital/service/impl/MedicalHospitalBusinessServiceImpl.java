package com.xczhihui.medical.hospital.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAuthenticationInformationMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorAuthenticationInformation;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.mapper.*;
import com.xczhihui.medical.hospital.model.*;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    private MedicalDoctorAuthenticationInformationMapper doctorAuthenticationInformationMapper;
    @Autowired
    private MedicalHospitalPictureMapper hospitalPictureMapper;
    @Autowired
    private MedicalHospitalFieldMapper hospitalFieldMapper;
    @Autowired
    private MedicalHospitalDoctorMapper hospitalDoctorMapper;

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
            }
        }
        return page.setRecords(medicalDoctorList);
    }

    /**
     * 根据用户id获取其医馆详情
     * @author zhuwenbao
     */
    @Override
    public MedicalHospitalVo selectHospitalByUserId(String uid) {

        // 根据用户id获取其医馆id
        MedicalHospitalAccount hospitalAccount = hospitalAccountMapper.getByUserId(uid);

        if(hospitalAccount == null){
            throw new RuntimeException("您尚未拥有医馆");
        }

        return medicalHospitalMapper.selectHospitalByIdAndStatus(hospitalAccount.getDoctorId(), null);
    }

    /**
     * 删除医馆里面的医师
     * @param doctorId 医师id
     * @author zhuwenbao
     */
    @Override
    public void deleteDoctor(String uid, String doctorId) {

        Optional doctorIdOption = Optional.ofNullable(doctorId);
        doctorIdOption.ifPresent(doctorIdStr -> {

            // 获取用户的医馆
            MedicalHospitalAccount hospitalAccount =
                    hospitalAccountMapper.getByUserId(uid);
            if(hospitalAccount == null || StringUtils.isBlank(hospitalAccount.getDoctorId())){
                throw new RuntimeException("您尚为认证医馆，请认证后再添加");
            }

            Map<String, Object> columnMap = new HashMap<>();
            columnMap.put("hospital_id", hospitalAccount.getDoctorId());
            columnMap.put("doctor_id", doctorId);
            hospitalDoctorMapper.deleteByMap(columnMap);

        });

    }

    @Override
    public List<MedicalFieldVO> getHotField() {
        return medicalHospitalMapper.getHotField();
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

        // 根据用户获取其医馆
        MedicalHospitalAccount hospitalAccount =
                hospitalAccountMapper.getByUserId(medicalHospital.getUpdatePerson());

        if(hospitalAccount == null){
            throw new RuntimeException("您尚未拥有医馆");
        }

        medicalHospital.setId(hospitalAccount.getDoctorId());
        Date now = new Date();
        medicalHospital.setUpdateTime(now);

        // 如果用户修改医馆照片
        if(CollectionUtils.isNotEmpty(medicalHospital.getPictures())){

            // 删除之前的医馆照片
            hospitalPictureMapper.updateDeletedByHospitalId(hospitalAccount.getDoctorId(), true);

            // 新增-新的医馆照片
            List<String> hospitalPictureUrlList = medicalHospital.getPictures();
            List<MedicalHospitalPicture> hospitalPictures = hospitalPictureUrlList.stream()
                    .filter(hospitalPictureUrl -> StringUtils.isNotBlank(hospitalPictureUrl))
                    .map(hospitalPictureUrl -> {
                        MedicalHospitalPicture hospitalPicture = new MedicalHospitalPicture();
                        hospitalPicture.setId(UUID.randomUUID().toString().replace("-",""));
                        hospitalPicture.setHospitalId(medicalHospital.getId());
                        hospitalPicture.setPicture(hospitalPictureUrl);
                        hospitalPicture.setDeleted(false);
                        hospitalPicture.setStatus(true);
                        hospitalPicture.setCreateTime(now);
                        return hospitalPicture;
                    }).collect(Collectors.toList());

            if(CollectionUtils.isNotEmpty(hospitalPictures)){
                hospitalPictureMapper.insertBatch(hospitalPictures);
            }
        }

        // 如果用户修改医馆领域
        if(CollectionUtils.isNotEmpty(medicalHospital.getFieldIds())){

            // 删除之前的医馆领域
            hospitalFieldMapper.updateDeletedByHospitalId(medicalHospital.getId(), true);

            // 新增-新的领域
            List<String> fieldIds = medicalHospital.getFieldIds();
            List<MedicalHospitalField> hospitalFields = new ArrayList<>();

            for (String fieldId : fieldIds){

                MedicalHospitalField hospitalField = new MedicalHospitalField();
                hospitalField.setId(UUID.randomUUID().toString().replace("-",""));
                hospitalField.setHospitalId(medicalHospital.getId());
                hospitalField.setFieldId(fieldId);
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

        if(medicalHospital == null){
            throw new RuntimeException("请选择要修改的医馆");
        }

        if(StringUtils.isBlank(medicalHospital.getHeadPortrait())){
            throw new RuntimeException("请上传医馆头像");
        }

        if(CollectionUtils.isEmpty(medicalHospital.getPictures())){
            throw new RuntimeException("请上传医馆图片");
        }

        if(CollectionUtils.isEmpty(medicalHospital.getFieldIds())){
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

//        if(StringUtils.isBlank(medicalHospital.getDetailedAddress())){
//            throw new RuntimeException("请填写医馆详细地址");
//        }

    }
}
