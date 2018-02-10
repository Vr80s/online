package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.xczhihui.medical.anchor.mapper.CourseAnchorMapper;
import com.xczhihui.medical.anchor.model.CourseAnchor;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.anchor.vo.CourseAnchorVO;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.enums.MedicalExceptionEnum;
import com.xczhihui.medical.exception.MedicalException;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalAccountMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalDoctorMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;
import com.xczhihui.medical.hospital.model.MedicalHospitalDoctor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 主播工作台资产业务接口实现层
 * @author zhuwenbao
 */
@Service
@Slf4j
public class AnchorInfoServiceImpl implements IAnchorInfoService{

    @Autowired
    private CourseAnchorMapper courseAnchorMapper;
    @Autowired
    private MedicalDoctorMapper doctorMapper;
    @Autowired
    private MedicalDoctorAccountMapper doctorAccountMapper;
    @Autowired
    private MedicalHospitalAccountMapper hospitalAccountMapper;
    @Autowired
    private MedicalHospitalMapper hospitalMapper;
    @Autowired
    private MedicalHospitalDoctorMapper hospitalDoctorMapper;

    /**
     * 获取主播详情
     */
    @Override
    public CourseAnchorVO detail(String userId) {

        // 保存返回结果
        CourseAnchorVO courseAnchorVO = new CourseAnchorVO();

        // 根据用户id获取主播信息
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_id", userId);
        List<CourseAnchor> courseAnchors = courseAnchorMapper.selectByMap(columnMap);

        if(CollectionUtils.isNotEmpty(courseAnchors)){

            CourseAnchor courseAnchor = courseAnchors.get(0);

            // 如果用户是医师 获取其医师信息
            if(courseAnchor.getType() == 1){
                courseAnchorVO = this.selectDoctorDetail(userId);
            }

            // 如果用户是医馆 获取其医馆信息
            if(courseAnchor.getType() == 2){
                courseAnchorVO = this.selectHospitalDetail(userId);
            }

            if(courseAnchorVO != null){
                courseAnchorVO.setName(courseAnchor.getName());
                courseAnchorVO.setProfilePhoto(courseAnchor.getProfilePhoto());
                courseAnchorVO.setVideo(courseAnchor.getVideo());
                courseAnchorVO.setDetail(courseAnchor.getDetail());
            }

        }

        return courseAnchorVO;

    }

    /**
     * 更新主播信息
     */
    @Override
    public void update(CourseAnchorVO target) {

        this.validate(target);

        CourseAnchor courseAnchor = new CourseAnchor();
        BeanUtils.copyProperties(target, courseAnchor);

        EntityWrapper<CourseAnchor> ew = new EntityWrapper();
        ew.where("user_id={0}",target.getUserId());
        courseAnchorMapper.update(courseAnchor, ew);

        // 根据用户id获取主播类型
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_id", target.getUserId());
        List<CourseAnchor> courseAnchors = courseAnchorMapper.selectByMap(columnMap);

        if(CollectionUtils.isNotEmpty(courseAnchors)){
            CourseAnchor anchor = courseAnchors.get(0);

            // 如果用户是医师 更新医师信息
            if(anchor.getType() == 1){
                this.updateDoctorDetail(target);
            }

            // 如果用户是医馆 更新其医馆信息
            if(anchor.getType() == 2){
                this.updateHospitalDetail(target);
            }
        }
    }

    private void updateHospitalDetail(CourseAnchorVO target) {

        MedicalHospitalAccount hospitalAccount = hospitalAccountMapper.getByUserId(target.getUserId());

        MedicalHospital hospital = new MedicalHospital();
        hospital.setId(hospitalAccount.getDoctorId());
        hospital.setProvince(target.getProvince());
        hospital.setCity(target.getCity());
        hospital.setUpdatePerson(target.getUserId());

        hospitalMapper.updateSelective(hospital);

    }

    private void updateDoctorDetail(CourseAnchorVO target) {

        // 根据用户id获取其医师id
        MedicalDoctorAccount doctorAccount = doctorAccountMapper.getByUserId(target.getUserId());

        // 更新用户：医师与医馆的关联关系
        // 先删除之前的关联关系
        EntityWrapper<MedicalHospitalDoctor> ew = new EntityWrapper();
        ew.where("doctor_id={0}", doctorAccount.getDoctorId());
        hospitalDoctorMapper.delete(ew);

        // 新增用户新的 医师与医馆关联关系
        MedicalHospitalDoctor hospitalDoctor = new MedicalHospitalDoctor();
        hospitalDoctor.setHospitalId(target.getHospitalId());
        hospitalDoctor.setDoctorId(doctorAccount.getDoctorId());
        hospitalDoctor.setCreateTime(new Date());
        hospitalDoctor.setId(UUID.randomUUID().toString().replace("-",""));
        hospitalDoctorMapper.insert(hospitalDoctor);

        // 更新用户的医师信息
        MedicalDoctor doctor = new MedicalDoctor();
        doctor.setProvince(target.getProvince());
        doctor.setCity(target.getCity());
        if(StringUtils.isNotBlank(target.getWorkTime())){
            doctor.setWorkTime(target.getWorkTime());
        }
        if(StringUtils.isNotBlank(target.getTel())){
            doctor.setTel(target.getTel());
        }
        if(StringUtils.isNotBlank(target.getDetailAddress())){
            doctor.setDetailedAddress(target.getDetailAddress());
        }
        doctor.setUpdatePerson(target.getUserId());
        EntityWrapper<MedicalDoctor> medicalDoctorEntityWrapper = new EntityWrapper();
        medicalDoctorEntityWrapper.where("id={0}", doctorAccount.getDoctorId());
        doctorMapper.update(doctor, medicalDoctorEntityWrapper);

    }

    /**
     * 参数校验
     */
    private void validate(CourseAnchorVO target) {

        if(target == null){
            throw new MedicalException(MedicalExceptionEnum.PARAM_NOT_EMPTY);
        }
        if(StringUtils.isBlank(target.getName())){
            throw new MedicalException(MedicalExceptionEnum.ANCHOR_NAME_EMPTY);
        }
        if(StringUtils.isBlank(target.getProfilePhoto())){
            throw new MedicalException(MedicalExceptionEnum.ANCHOR_PROFILEPHOTO_EMPTY);
        }
        if(StringUtils.isBlank(target.getDetail())){
            throw new MedicalException(MedicalExceptionEnum.ANCHOR_DETAIL_EMPTY);
        }
        if(StringUtils.isBlank(target.getHospitalId())){
            throw new MedicalException(MedicalExceptionEnum.ANCHOR_HOSPITALID_EMPTY);
        }
        if(StringUtils.isBlank(target.getProvince())){
            throw new MedicalException(MedicalExceptionEnum.ANCHOR_PROVINCE_EMPTY);
        }
        if(StringUtils.isBlank(target.getCity())){
            throw new MedicalException(MedicalExceptionEnum.ANCHOR_CITY_EMPTY);
        }

    }

    /**
     * 根据用户id获取其医馆信息
     */
    private CourseAnchorVO selectHospitalDetail(String userId) {

        CourseAnchorVO courseAnchorVO = new CourseAnchorVO();

        MedicalHospitalAccount hospitalAccount = hospitalAccountMapper.getByUserId(userId);

        // 获取医馆的信息
        MedicalHospital hospital = hospitalMapper.selectById(hospitalAccount.getDoctorId());
        if(hospital != null){
            courseAnchorVO.setHospitalName(hospital.getName());
            courseAnchorVO.setTel(hospital.getTel());
            courseAnchorVO.setProvince(hospital.getProvince());
            courseAnchorVO.setCity(hospital.getCity());
            courseAnchorVO.setDetailAddress(hospital.getDetailedAddress());
        }

        return courseAnchorVO;

    }

    /**
     * 根据用户id获取其医师详情
     */
    private CourseAnchorVO selectDoctorDetail(String userId) {

        CourseAnchorVO courseAnchorVO = new CourseAnchorVO();

        // 根据用户id获取其医师id
        MedicalDoctorAccount doctorAccount = doctorAccountMapper.getByUserId(userId);

        // 获取医师所在医馆
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("doctor_id", doctorAccount.getDoctorId());
        List<MedicalHospitalDoctor> hospitalDoctors = hospitalDoctorMapper.selectByMap(columnMap);
        if(CollectionUtils.isNotEmpty(hospitalDoctors)){

            // 获取医馆的信息
            MedicalHospitalDoctor hospitalDoctor = hospitalDoctors.get(0);
            MedicalHospital hospital = hospitalMapper.selectById(hospitalDoctor.getHospitalId());
            if(hospital != null){
                courseAnchorVO.setHospitalName(hospital.getName());
            }

        }

        // 获取医师的坐诊时间 预约电话 城市 地址
        MedicalDoctor doctor = doctorMapper.selectById(doctorAccount.getDoctorId());
        if(doctor != null){
            courseAnchorVO.setWorkTime(doctor.getWorkTime());
            courseAnchorVO.setTel(doctor.getTel());
            courseAnchorVO.setProvince(doctor.getProvince());
            courseAnchorVO.setCity(doctor.getCity());
            courseAnchorVO.setDetailAddress(doctor.getDetailedAddress());
        }

        return courseAnchorVO;

    }
}
