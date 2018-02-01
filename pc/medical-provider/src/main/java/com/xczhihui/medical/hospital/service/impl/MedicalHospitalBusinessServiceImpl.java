package com.xczhihui.medical.hospital.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAuthenticationInformationMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorAuthenticationInformation;
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
    private MedicalDoctorAuthenticationInformationMapper doctorAuthenticationInformationMapper;
    @Autowired
    private MedicalHospitalPictureMapper hospitalPictureMapper;
    @Autowired
    private MedicalHospitalFieldMapper hospitalFieldMapper;

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
}
