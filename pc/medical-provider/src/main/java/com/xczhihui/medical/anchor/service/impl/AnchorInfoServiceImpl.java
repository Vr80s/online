package com.xczhihui.medical.anchor.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.xczhihui.bxg.common.support.service.impl.RedisCacheService;
import com.xczhihui.bxg.online.common.utils.OnlineConfig;
import com.xczhihui.medical.anchor.enums.AuchorTypeEnum;
import com.xczhihui.medical.anchor.mapper.CourseAnchorMapper;
import com.xczhihui.medical.anchor.mapper.CourseApplyResourceMapper;
import com.xczhihui.medical.anchor.model.CourseAnchor;
import com.xczhihui.medical.anchor.model.CourseApplyResource;
import com.xczhihui.medical.anchor.service.IAnchorInfoService;
import com.xczhihui.medical.anchor.vo.CourseAnchorVO;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.service.IMedicalDoctorAuthenticationInformationService;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalAccountMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalDoctorMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;
import com.xczhihui.medical.hospital.model.MedicalHospitalDoctor;
import com.xczhihui.medical.hospital.service.IMedicalHospitalAuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

/**
 * 主播工作台资产业务接口实现层
 * @author zhuwenbao
 */
@Service
@Slf4j
public class AnchorInfoServiceImpl implements IAnchorInfoService{

    public static String VALIDATE_ANCHOR_PERMISSION_CACHE="v_a_p_c";

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
                courseAnchorVO.setVideo(this.processVideoStr(courseAnchor.getResourceId()));
                courseAnchorVO.setDetail(courseAnchor.getDetail());
                courseAnchorVO.setResourceId(courseAnchor.getResourceId());
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

        // 根据用户id获取主播类型
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("user_id", target.getUserId());
        List<CourseAnchor> courseAnchors = courseAnchorMapper.selectByMap(columnMap);

        if(CollectionUtils.isNotEmpty(courseAnchors)){

            CourseAnchor anchor = courseAnchors.get(0);

            // 更新用户的主播信息
            CourseAnchor courseAnchor = new CourseAnchor();
            BeanUtils.copyProperties(target, courseAnchor);
            courseAnchor.setId(anchor.getId());
//            EntityWrapper<CourseAnchor> ew = new EntityWrapper();
//            ew.where("user_id={0}",target.getUserId());
//            courseAnchorMapper.update(courseAnchor, ew);

            // 如果不选择精彩致辞，则表示取消之前的精彩致辞(这是不是删除用户的精彩致辞)
            if (target.getResourceId() == null){
                courseAnchor.setResourceId(0);
            }

            courseAnchorMapper.updateById(courseAnchor);

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
                .map(option -> this.selectAuthInfo(option,userId))
                .orElse(null);
    }

    /**
     * Description：校验主播权限
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 2018/3/5 0005 上午 9:21
     **/
    @Override
    public void validateAnchorPermission(String userId) {
        if(userId==null){
            throw new RuntimeException("用户id不为空");
        }
        CourseAnchor courseAnchor = new CourseAnchor();
        courseAnchor.setUserId(userId);
        courseAnchor.setStatus(true);
        String key = VALIDATE_ANCHOR_PERMISSION_CACHE + userId;
        CourseAnchor ca = cacheService.get(key);
        if(ca == null){
            ca = courseAnchorMapper.selectOne(courseAnchor);
            if(ca == null) {
                throw new RuntimeException("不具备主播权限或主播权限被禁用");
            }else{
                //缓存礼物数据10分钟
                cacheService.set(key,ca,60);
            }
        }else{
            logger.info("{}具备主播权限，取到缓存数据",key);
        }
    }

    private Object selectAuthInfo(Integer type, String userId) {

        // 如果用户是医师 获取其医师认证信息
        if(type.equals(AuchorTypeEnum.DOCTOR.getCode())){
            return doctorAuthenticationInformationService.selectDoctorAuthenticationVO(userId);
        }

        // 如果用户是医馆 获取其医馆认证信息
        if(type.equals(AuchorTypeEnum.HOSPITAL.getCode())){
            return hospitalAuthenticationService.selectHospitalAuthentication(userId);
        }

        return null;

    }

    private void updateHospitalDetail(CourseAnchorVO target) {

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

        if(StringUtils.isBlank(target.getHospitalId())){
            throw new RuntimeException("请选择医馆");
        }

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
            throw new RuntimeException("参数不能为空");
        }
        if(StringUtils.isBlank(target.getName())){
            throw new RuntimeException("主播昵称不能为空");
        }
        if(StringUtils.isBlank(target.getProfilePhoto())){
            throw new RuntimeException("主播头像不能为空");
        }
        if(StringUtils.isBlank(target.getDetail())){
            throw new RuntimeException("主播个人介绍不能为空");
        }
        if(StringUtils.isBlank(target.getProvince())){
            throw new RuntimeException("主播省份不能为空");
        }
        if(StringUtils.isBlank(target.getCity())){
            throw new RuntimeException("主播省份不能为空");
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
        MedicalHospital hospital = new MedicalHospital();
        if(CollectionUtils.isNotEmpty(hospitalDoctors)){

            // 获取医馆的信息
            MedicalHospitalDoctor hospitalDoctor = hospitalDoctors.get(0);
            hospital = hospitalMapper.selectById(hospitalDoctor.getHospitalId());
            if(hospital != null){
                courseAnchorVO.setHospitalName(hospital.getName());
            }

        }

        // 获取医师的坐诊时间 预约电话 城市 地址
        MedicalDoctor doctor = doctorMapper.selectById(doctorAccount.getDoctorId());
        if(doctor != null){
            courseAnchorVO.setWorkTime(doctor.getWorkTime());
            courseAnchorVO.setProvince(doctor.getProvince());
            courseAnchorVO.setCity(doctor.getCity());
            courseAnchorVO.setDetailAddress(doctor.getDetailedAddress());

            // 医师的预约电话是医馆的预约电话
            courseAnchorVO.setTel(hospital.getTel());
        }

        return courseAnchorVO;

    }

    /**
     * 加工主播精彩致辞
     */
    private String processVideoStr(Integer resourceId){

        // resourceId为0时,表示用户之前取消了精彩致辞
        if(resourceId == null || resourceId == 0){
            return null;
        }

        CourseApplyResource resource = courseApplyResourceMapper.selectById(resourceId);
        if(resource != null){
            String courseResource = resource.getResource();

            String src = "https://p.bokecc.com/flash/single/" + OnlineConfig.CC_USER_ID+"_" + courseResource
                    + "_false_" + OnlineConfig.CC_PLAYER_ID + "_1" + "/player.swf";
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String playCode = "";
            playCode+="<object classid=\"clsid:D27CDB6E-AE6D-11cf-96B8-444553540000\" ";
            playCode+="		codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,0,0\" ";
            playCode+="		width=\"600\" ";
            playCode+="		height=\"490\" ";
            playCode+="		id=\""+uuid+"\">";
            playCode+="		<param name=\"movie\" value=\""+src+"\" />";
            playCode+="		<param name=\"allowFullScreen\" value=\"true\" />";
            playCode+="		<param name=\"allowScriptAccess\" value=\"always\" />";
            playCode+="		<param value=\"transparent\" name=\"wmode\" />";
            playCode+="		<embed src=\""+src+"\" ";
            playCode+="			width=\"600\" height=\"490\" name=\""+uuid+"\" allowFullScreen=\"true\" ";
            playCode+="			wmode=\"transparent\" allowScriptAccess=\"always\" pluginspage=\"http://www.macromedia.com/go/getflashplayer\" ";
            playCode+="			type=\"application/x-shockwave-flash\"/> ";
            playCode+="	</object>";

            return playCode;
        }

        return null;
    }


}
