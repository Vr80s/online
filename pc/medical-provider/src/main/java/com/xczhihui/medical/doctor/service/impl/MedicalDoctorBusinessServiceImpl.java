package com.xczhihui.medical.doctor.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAccountMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorAuthenticationInformationMapper;
import com.xczhihui.medical.doctor.mapper.MedicalDoctorMapper;
import com.xczhihui.medical.doctor.mapper.OeBxsArticleMapper;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.model.MedicalDoctorAccount;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.MedicalDoctorAuthenticationInformationVO;
import com.xczhihui.medical.doctor.service.IMedicalDoctorBusinessService;
import com.xczhihui.medical.doctor.vo.MedicalWritingsVO;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalAccountMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalDoctorMapper;
import com.xczhihui.medical.hospital.mapper.MedicalHospitalMapper;
import com.xczhihui.medical.hospital.model.MedicalHospital;
import com.xczhihui.medical.hospital.model.MedicalHospitalAccount;
import com.xczhihui.medical.hospital.model.MedicalHospitalDoctor;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;
import com.xczhihui.medical.hospital.service.IMedicalHospitalBusinessService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * ClassName: MedicalDoctorBusinessServiceImpl.java <br>
 * Description:医师业务接口类 <br>
 * Create by: name：yuxin <br>email: yuruixin@ixincheng.com <br>
 * Create Time: 下午 11:24 2017/12/9 0009<br>
 */
@Service
public class MedicalDoctorBusinessServiceImpl implements IMedicalDoctorBusinessService{

    @Autowired
    private MedicalDoctorMapper medicalDoctorMapper;
    @Autowired
    private IMedicalHospitalBusinessService iMedicalHospitalBusinessService;
    @Autowired
    private MedicalDoctorAuthenticationInformationMapper medicalDoctorAuthenticationInformationMapper;
    @Autowired
    private OeBxsArticleMapper oeBxsArticleMapper;

    @Autowired
    private MedicalHospitalMapper medicalHospitalMapper;
    @Autowired
    private MedicalDoctorAccountMapper medicalDoctorAccountMapper;
    @Autowired
    private MedicalHospitalAccountMapper hospitalAccountMapper;
    @Autowired
    private MedicalHospitalDoctorMapper hospitalDoctorMapper;
    @Autowired
    private ThreadPoolTaskExecutor commonThreadPoolTaskExecutor;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${specialColumn}")
    private String specialColumn;
    @Value("${doctorReport}")
    private String doctorReport;

    @Override
    public Page<MedicalDoctorVO> selectDoctorPage(Page<MedicalDoctorVO> page, Integer type, String hospitalId, String name, String field) {
        List<MedicalDoctorVO> records = medicalDoctorMapper.selectDoctorList(page, type, hospitalId, name, field);
        if(field!=null){
            for (int i = 0; i < records.size(); i++) {
                List<MedicalFieldVO> medicalFields = medicalDoctorMapper.selectMedicalFieldsByDoctorId(records.get(i).getId());
                records.get(i).setFields(medicalFields);
            }
        }
        page.setRecords(records);
        return page;
    }

    @Override
    public MedicalDoctorVO selectDoctorById(String id) {
        MedicalDoctorVO medicalDoctorVO = medicalDoctorMapper.selectDoctorById(id);
        List<MedicalFieldVO> medicalFields = medicalDoctorMapper.selectMedicalFieldsByDoctorId(medicalDoctorVO.getId());
        medicalDoctorVO.setFields(medicalFields);
        if(medicalDoctorVO.getHospitalId() != null){
            MedicalHospitalVo medicalHospital = iMedicalHospitalBusinessService.selectHospitalById(medicalDoctorVO.getHospitalId());
            medicalDoctorVO.setMedicalHospital(medicalHospital);
        }
        if(medicalDoctorVO.getAuthenticationInformationId()!=null){
            MedicalDoctorAuthenticationInformationVO medicalDoctorAuthenticationInformation = medicalDoctorAuthenticationInformationMapper.selectByDoctorId(medicalDoctorVO.getAuthenticationInformationId());
            medicalDoctorVO.setMedicalDoctorAuthenticationInformation(medicalDoctorAuthenticationInformation);
        }
        return medicalDoctorVO;
    }

    @Override
    public List<MedicalFieldVO> getHotField() {
        return medicalDoctorMapper.getHotField();
    }

    @Override
    public List<MedicalDoctorVO> selectRecDoctor() {
        return medicalDoctorMapper.selectRecDoctor();
    }

    @Override
    public List<OeBxsArticleVO> getNewsReports(String doctorId) {
        return oeBxsArticleMapper.getNewsReports(doctorId);
    }

    @Override
    public OeBxsArticleVO getNewsReportByArticleId(String articleId) {
        return oeBxsArticleMapper.getNewsReportByArticleId(articleId);
    }

    @Override
    public Page<OeBxsArticleVO> getSpecialColumns(Page<OeBxsArticleVO> page, String doctorId) {
        List<OeBxsArticleVO> specialColumns = oeBxsArticleMapper.getSpecialColumns(page, doctorId);
        page.setRecords(specialColumns);
        return page;
    }

    @Override
    public OeBxsArticleVO getSpecialColumnDetailsById(String articleId) {
        return oeBxsArticleMapper.getSpecialColumnDetailsById(articleId);
    }

    @Override
    public List<MedicalWritingsVO> getWritingsByDoctorId(String doctorId) {
        return medicalDoctorMapper.getWritingsByDoctorId(doctorId);
    }

    @Override
    public MedicalWritingsVO getWritingsDetailsById(String writingsId) {
        return medicalDoctorMapper.getWritingsDetailsById(writingsId);
    }

    @Override
    public List<MedicalWritingsVO> getRecentlyWritings() {
        return medicalDoctorMapper.getRecentlyWritings();
    }

    @Override
    public List<OeBxsArticleVO> getRecentlyNewsReports() {
        return oeBxsArticleMapper.getRecentlyNewsReports(doctorReport);
    }

    @Override
    public Page<OeBxsArticleVO> getNewsReportsByPage(Page<OeBxsArticleVO> page, String doctorId) {
        List<OeBxsArticleVO> records = oeBxsArticleMapper.getNewsReportsByPage(page,doctorId,doctorReport);
        page.setRecords(records);
        return page;
    }

    @Override
    public List<OeBxsArticleVO> getHotSpecialColumn() {
        return oeBxsArticleMapper.getHotSpecialColumn(specialColumn);
    }

    @Override
    public List<MedicalDoctorVO> getHotSpecialColumnAuthor() {
        return medicalDoctorMapper.getHotSpecialColumnAuthor(specialColumn);
    }

    @Override
    public Page<MedicalWritingsVO> getWritingsByPage(Page<MedicalWritingsVO> page) {
        List<MedicalWritingsVO> writings = medicalDoctorMapper.getWritingsByPage(page);
        page.setRecords(writings);
        return page;
    }

    /**
     * 加入医馆
     * @param medicalDoctor 加入医馆提交的信息
     */
    @Override
    public void joinHospital(MedicalDoctor medicalDoctor) {

        // 判断用户是否为医师
        MedicalDoctorAccount medicalDoctorAccount = medicalDoctorAccountMapper.getByUserId(medicalDoctor.getUserId());
        if(medicalDoctorAccount == null){
            throw new RuntimeException("您不是医师，不能加入医馆");
        }

        String hospitalId;

        // 如果医馆id为空 则表示用户手动填写医馆信息
        if(StringUtils.isBlank(medicalDoctor.getHospitalId())){

            hospitalId = UUID.randomUUID().toString().replace("-","");

            // 参数校验
            this.validate(medicalDoctor);

            // 保存医馆信息
            MedicalHospital medicalHospital = new MedicalHospital();

            // 该医馆没有被校验 只是用来数据展示
            medicalHospital.setId(hospitalId);
            medicalHospital.setName(medicalDoctor.getName());
            medicalHospital.setAuthentication(false);
            medicalHospital.setProvince(medicalDoctor.getProvince());
            medicalHospital.setCity(medicalDoctor.getCity());
            medicalHospital.setDeleted(false);
            medicalHospital.setStatus(false);
            medicalHospital.setCreateTime(new Date());
            medicalHospital.setFrontImg(medicalDoctor.getHeadPortrait());
            medicalHospitalMapper.insert(medicalHospital);

        }else{

            hospitalId = medicalDoctor.getHospitalId();

        }

        commonThreadPoolTaskExecutor.submit(() -> {

            // 添加医馆和用户的对应关系 ：medical_hospital_account
            MedicalHospitalAccount hospitalAccount = new MedicalHospitalAccount();
            hospitalAccount.setId(UUID.randomUUID().toString().replace("-",""));
            hospitalAccount.setDoctorId(hospitalId);
            hospitalAccount.setAccountId(medicalDoctor.getUserId());
            hospitalAccountMapper.insert(hospitalAccount);

            // 添加医馆和医师的对应关系：medical_hospital_doctor
            MedicalHospitalDoctor hospitalDoctor = new MedicalHospitalDoctor();
            hospitalDoctor.setId(UUID.randomUUID().toString().replace("-",""));
            hospitalDoctor.setDoctorId(medicalDoctorAccount.getDoctorId());
            hospitalDoctor.setHospitalId(hospitalId);
            hospitalDoctorMapper.insert(hospitalDoctor);

            logger.info("hospitalId：{} and accountId：{}，doctorId：{} build relation Successfully",hospitalId,medicalDoctor.getUserId(),medicalDoctorAccount.getDoctorId());

            return null;
        });

    }

    /**
     * 获取医师的坐诊时间
     * @author zhuwenbao
     */
    @Override
    public String getWorkTimeById(String userId, Integer type) {

        if(type == null){
            type = 1;
        }

        MedicalDoctorAccount doctorAccount =
                medicalDoctorAccountMapper.getByUserId(userId);

        if(doctorAccount != null){
            StringBuffer sb = new StringBuffer();
            String workTime = medicalDoctorMapper.getWorkTimeById(doctorAccount.getDoctorId());
            if(type == 1){
                if(StringUtils.isNotBlank(workTime)){
                    if(workTime.contains("一")){
                        sb.append("一");
                    }
                    if(workTime.contains("二")){
                        sb.append("，二");
                    }
                    if(workTime.contains("三")){
                        sb.append("，三");
                    }
                    if(workTime.contains("四")){
                        sb.append("，四");
                    }
                    if(workTime.contains("五")){
                        sb.append("，五");
                    }
                    if(workTime.contains("六")){
                        sb.append("，六");
                    }
                    if(workTime.contains("日")){
                        sb.append("，日");
                    }
                }
                return sb.toString();
            }
        }

        return null;
    }

    /**
     * 参数校验
     * @param medicalDoctor 被校验的参数
     */
    private void validate(MedicalDoctor medicalDoctor) {

        if(StringUtils.isBlank(medicalDoctor.getName())){
            throw new RuntimeException("医馆名字不能为空");
        }

        if(StringUtils.isBlank(medicalDoctor.getProvince())){
            throw new RuntimeException("请选择医馆所在省份");
        }

        if(StringUtils.isBlank(medicalDoctor.getCity())){
            throw new RuntimeException("请选择医馆所在城市");
        }

        if(StringUtils.isBlank(medicalDoctor.getTel())){
            throw new RuntimeException("请填写医馆联系电话");
        }

        if(StringUtils.isBlank(medicalDoctor.getWorkTime())){
            throw new RuntimeException("请选择坐诊时间");
        }

        if(StringUtils.isBlank(medicalDoctor.getHeadPortrait())){
            throw new RuntimeException("请上传封面图");
        }

    }
}
