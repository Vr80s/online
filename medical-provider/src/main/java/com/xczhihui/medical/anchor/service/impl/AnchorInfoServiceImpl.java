package com.xczhihui.medical.anchor.service.impl;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.xczhihui.common.support.cc.util.CCUtils;
import com.xczhihui.common.support.service.impl.RedisCacheService;
import com.xczhihui.common.util.XzStringUtils;
import com.xczhihui.common.util.enums.AnchorPermissionType;
import com.xczhihui.common.util.enums.AnchorType;
import com.xczhihui.common.util.redis.key.RedisCacheKey;
import com.xczhihui.medical.anchor.enums.AuchorTypeEnum;
import com.xczhihui.medical.anchor.mapper.CourseAnchorMapper;
import com.xczhihui.medical.anchor.mapper.CourseApplyResourceMapper;
import com.xczhihui.medical.anchor.mapper.UserDocumentMapper;
import com.xczhihui.medical.anchor.model.CourseAnchor;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.model.UserDocument;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.anchor.vo.CourseAnchorVO;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAuthenticationInformationService;
import com.xczhihui.medical.exception.AnchorException;
import com.xczhihui.medical.exception.MedicalException;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalAccountMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalDoctorMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;
import com.xczhihui.medical.hospital.model.MedicalHospitalDoctor;
import com.xczhihui.medical.hospital.service.IMedicalHospitalAuthenticationService;

import lombok.extern.slf4j.Slf4j;

/**
 * 主播工作台资产业务接口实现层
 *
 * @author zhuwenbao
 */
@Service
@Slf4j
public class AnchorInfoServiceImpl implements IAnchorInfoService {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

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
    @Autowired
    private IMedicalDoctorAuthenticationInformationService doctorAuthenticationInformationService;
    @Autowired
    private IMedicalHospitalAuthenticationService hospitalAuthenticationService;
    @Autowired
    private CourseApplyResourceMapper courseApplyResourceMapper;
    @Autowired
    private RedisCacheService cacheService;
    @Autowired
    private UserDocumentMapper userDocumentMapper;

    @Autowired
    private CCUtils ccUtils;

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

        if (CollectionUtils.isNotEmpty(courseAnchors)) {

            CourseAnchor courseAnchor = courseAnchors.get(0);

            // 如果用户是医师 获取其医师信息
            if (courseAnchor.getType() == AnchorType.DOCTOR.getCode()) {
                courseAnchorVO = this.selectDoctorDetail(userId);
            }

            // 如果用户是医馆 获取其医馆信息
            if (courseAnchor.getType() == AnchorType.HOSPITAL.getCode()) {
                courseAnchorVO = this.selectHospitalDetail(userId);
            }

            if (courseAnchorVO != null) {
                courseAnchorVO.setName(courseAnchor.getName());
                courseAnchorVO.setProfilePhoto(courseAnchor.getProfilePhoto());
                courseAnchorVO.setVideo(this.processVideoStr(courseAnchor.getResourceId()));
                courseAnchorVO.setDetail(courseAnchor.getDetail());
                courseAnchorVO.setResourceId(courseAnchor.getResourceId());
            }

        }
        courseAnchorVO.setWt(courseAnchorVO.getWorkTime());
        //过滤下时间
        courseAnchorVO.setWorkTime(XzStringUtils.workTimeScreen(courseAnchorVO.getWorkTime()));
        return courseAnchorVO;

    }

    /**
     * 更新主播信息
     */
    @Override
    public void update(CourseAnchorVO target) {

        this.validate(target);

        // 根据用户id获取主播类型
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_id", target.getUserId());
        List<CourseAnchor> courseAnchors = courseAnchorMapper.selectByMap(columnMap);

        if (CollectionUtils.isNotEmpty(courseAnchors)) {

            CourseAnchor anchor = courseAnchors.get(0);

            // 更新用户的主播信息
            CourseAnchor courseAnchor = new CourseAnchor();
            BeanUtils.copyProperties(target, courseAnchor);
            courseAnchor.setId(anchor.getId());

            // 如果不选择精彩致辞，则表示取消之前的精彩致辞(这是不是删除用户的精彩致辞)
            if (target.getResourceId() == null) {
                courseAnchor.setResourceId(null);
                courseAnchor.setVideo(null);
            } else {
                CourseApplyResource courseApplyResource = courseApplyResourceMapper.selectById(target.getResourceId());
                courseAnchor.setVideo(courseApplyResource.getResource());
            }

            courseAnchorMapper.updateById(courseAnchor);

            // 如果用户是医师 更新医师信息
            if (anchor.getType() == AnchorType.DOCTOR.getCode()) {
                this.updateDoctorDetail(target);
            }

            // 如果用户是医馆 更新其医馆信息
            if (anchor.getType() == AnchorType.HOSPITAL.getCode()) {
                this.updateHospitalDetail(target);
            }
        }
    }

    /**
     * 获取主播的认证信息
     */
    @Override
    public Object authInfo(String userId) {

        CourseAnchor anchor = new CourseAnchor();
        anchor.setUserId(userId);
        anchor = courseAnchorMapper.selectOne(anchor);
        Optional<CourseAnchor> anchorOptional = Optional.ofNullable(anchor);
        return anchorOptional
                .map(option -> option.getType())
                .map(option -> this.selectAuthInfo(option, userId))
                .orElse(null);
    }

    /**
     * Description：校验主播权限
     * creed: Talk is cheap,show me the code
     *
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/3/5 0005 上午 9:21
     **/
    @Override
    public void validateAnchorPermission(String userId) {
        if (userId == null) {
            throw new AnchorException("用户id不为空");
        }

        CourseAnchor ca = getCourseAnchor4Validate(userId);
        if (ca == null) {
            throw new AnchorException("不具备主播权限或主播权限被禁用");
        }
    }

    @Override
    public CourseAnchor getCourseAnchor4Validate(String userId){
        CourseAnchor courseAnchor = new CourseAnchor();
        courseAnchor.setUserId(userId);
        courseAnchor.setStatus(true);
        String key = RedisCacheKey.getAnchorPermissionValidateCacheKey(userId);
        CourseAnchor ca = cacheService.get(key);
        if (ca == null) {
            ca = courseAnchorMapper.selectOne(courseAnchor);
            if (ca != null) {
                //缓存数据1分钟
                cacheService.set(key, ca, 60);
            }
        }
        return ca;
    }

    private Object selectAuthInfo(Integer type, String userId) {

        // 如果用户是医师 获取其医师认证信息
        if (type.equals(AuchorTypeEnum.DOCTOR.getCode())) {
            return doctorAuthenticationInformationService.selectDoctorAuthenticationVO(userId);
        }

        // 如果用户是医馆 获取其医馆认证信息
        if (type.equals(AuchorTypeEnum.HOSPITAL.getCode())) {
            return hospitalAuthenticationService.selectHospitalAuthentication(userId);
        }

        return null;

    }

    private void updateHospitalDetail(CourseAnchorVO target) {
        if (StringUtils.isNotBlank(target.getDetailAddress()) && target.getDetailAddress().length() > 100) {
            throw new AnchorException("详细地址长度不可超过100字");
        }
        MedicalHospitalAccount hospitalAccount = hospitalAccountMapper.getByUserId(target.getUserId());

        MedicalHospital hospital = new MedicalHospital();
        hospital.setId(hospitalAccount.getDoctorId());
        hospital.setProvince(target.getProvince());
        hospital.setCity(target.getCity());
        hospital.setUpdatePerson(target.getUserId());
        hospital.setTel(target.getTel());
        hospital.setDetailedAddress(target.getDetailAddress());

        hospitalMapper.updateSelective(hospital);

    }

    private void updateDoctorDetail(CourseAnchorVO target) {

//        if (StringUtils.isBlank(target.getHospitalId())) {
//            throw new AnchorException("请选择医馆");
//        }

        // 根据用户id获取其医师id
        MedicalDoctorAccount doctorAccount = doctorAccountMapper.getByUserId(target.getUserId());

        // 更新用户：医师与医馆的关联关系
        // 先删除之前的关联关系
        EntityWrapper<MedicalHospitalDoctor> ew = new EntityWrapper();
        ew.where("doctor_id={0}", doctorAccount.getDoctorId());
        hospitalDoctorMapper.delete(ew);

        if (StringUtils.isNotBlank(target.getHospitalId())) {
            // 新增用户新的 医师与医馆关联关系
            MedicalHospitalDoctor hospitalDoctor = new MedicalHospitalDoctor();
            hospitalDoctor.setHospitalId(target.getHospitalId());
            hospitalDoctor.setDoctorId(doctorAccount.getDoctorId());
            hospitalDoctor.setCreateTime(new Date());
            hospitalDoctor.setId(UUID.randomUUID().toString().replace("-", ""));
            hospitalDoctorMapper.insert(hospitalDoctor);
        }

        // 更新用户的医师信息
        MedicalDoctor doctor = new MedicalDoctor();
        doctor.setWorkTime(target.getWorkTime());
        doctor.setProvince(target.getProvince());
        doctor.setCity(target.getCity());
        doctor.setDetailedAddress(target.getDetailAddress());
        doctor.setUpdatePerson(target.getUserId());
        EntityWrapper<MedicalDoctor> medicalDoctorEntityWrapper = new EntityWrapper();
        medicalDoctorEntityWrapper.where("id={0}", doctorAccount.getDoctorId());
        doctorMapper.update(doctor, medicalDoctorEntityWrapper);

    }

    /**
     * 参数校验
     */
    private void validate(CourseAnchorVO target) {

        if (target == null) {
            throw new AnchorException("参数不能为空");
        }
        if (StringUtils.isBlank(target.getName())) {
            throw new AnchorException("主播昵称不能为空");
        }
        if (StringUtils.isBlank(target.getProfilePhoto())) {
            throw new AnchorException("主播头像不能为空");
        }
        if (StringUtils.isBlank(target.getDetail())) {
            throw new AnchorException("主播个人介绍不能为空");
        }
//        if(StringUtils.isBlank(target.getProvince())){
//            throw new AnchorException("主播省份不能为空");
//        }
//        if(StringUtils.isBlank(target.getCity())){
//            throw new AnchorException("主播省份不能为空");
//        }

    }

    /**
     * 根据用户id获取其医馆信息
     */
    private CourseAnchorVO selectHospitalDetail(String userId) {

        CourseAnchorVO courseAnchorVO = new CourseAnchorVO();

        MedicalHospitalAccount hospitalAccount = hospitalAccountMapper.getByUserId(userId);

        // 获取医馆的信息
        MedicalHospital hospital = hospitalMapper.selectById(hospitalAccount.getDoctorId());
        if (hospital != null) {
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
        MedicalHospital hospital;
        if (CollectionUtils.isNotEmpty(hospitalDoctors)) {

            // 获取医馆的信息
            MedicalHospitalDoctor hospitalDoctor = hospitalDoctors.get(0);
            hospital = hospitalMapper.selectById(hospitalDoctor.getHospitalId());
            if (hospital != null) {
                courseAnchorVO.setHospitalName(hospital.getName());
                courseAnchorVO.setHospitalId(hospital.getId());
                // 医师的预约电话是医馆的预约电话
                courseAnchorVO.setTel(hospital.getTel());
            }

        }

        // 获取医师的坐诊时间 预约电话 城市 地址
        MedicalDoctor doctor = doctorMapper.selectById(doctorAccount.getDoctorId());
        if (doctor != null) {
            courseAnchorVO.setWorkTime(doctor.getWorkTime());
        }
        courseAnchorVO.setProvince(doctor.getProvince());
        courseAnchorVO.setCity(doctor.getCity());
        courseAnchorVO.setDetailAddress(doctor.getDetailedAddress());
        return courseAnchorVO;

    }

    /**
     * 加工主播精彩致辞
     */
    private String processVideoStr(Integer resourceId) {

        // resourceId为0时,表示用户之前取消了精彩致辞
        if (resourceId == null || resourceId == 0) {
            return null;
        }

        CourseApplyResource resource = courseApplyResourceMapper.selectById(resourceId);

        if (resource != null) {
            String courseResource = resource.getResource();
            String playCode = ccUtils.getPlayCode(courseResource, "");
            return playCode;
        }

        return null;
    }

    @Override
    public Integer anchorPermissionStatus(String userId) {
        if (userId == null) {
            throw new AnchorException("用户id不为空");
        }
        CourseAnchor courseAnchor = new CourseAnchor();
        courseAnchor.setUserId(userId);
        CourseAnchor ca = courseAnchorMapper.selectOne(courseAnchor);
        if (ca == null) {
            return AnchorPermissionType.NO_PERMISSION.getCode();
        } else if (ca != null && !ca.getStatus()) { //被禁用
            return AnchorPermissionType.PERMISSION_DISABLE.getCode();
        }
        return ca.getType();
    }

    @Override
    public Map<String, Object> anchorPermissionStatusByDoctorId(String doctorId) {

        Map<String, Object> map = new HashMap<String, Object>();
        Integer falg = 0;
        //是否存在此账户
        MedicalDoctorAccount doctorAccount = doctorAccountMapper.getByDoctorId(doctorId);
        if (doctorAccount == null) {
            falg = AnchorPermissionType.NO_PERMISSION.getCode();
            map.put("userId", null);
        } else {
            falg = this.anchorPermissionStatus(doctorAccount.getAccountId());
            map.put("userId", doctorAccount.getAccountId());
        }
        map.put("status", falg);

        return map;
    }

    @Override
    public void addDocument(String userId, String documentId, String documentName) {
        UserDocument userDocument = new UserDocument();
        userDocument.setCreateTime(new Date());
        userDocument.setDocumentId(documentId);
        userDocument.setUserId(userId);
        userDocument.setDeleted(false);
        userDocument.setTransStatus(0);
        userDocument.setDocumentName(documentName);
        userDocumentMapper.insert(userDocument);
    }

    @Override
    public void deleteDocument(String documentId) {
        UserDocument userDocument = userDocumentMapper.selectById(documentId);
        if (userDocument == null || userDocument.getDeleted() != null || userDocument.getDeleted()) {
            throw new MedicalException("文档不存在");
        }
        userDocument.setDeleted(true);
        userDocumentMapper.updateById(userDocument);
    }

    @Override
    public List<UserDocument> listDocument(String userId) {
        return userDocumentMapper.listByUserId(userId);
    }

    @Override
    public void updateDocumentStatus(String documentId, Integer status) {
        userDocumentMapper.updateStatusByDocumentId(documentId, status);
    }
}
