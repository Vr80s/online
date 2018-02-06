package com.xczhihui.medical.doctor.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.model.MedicalDoctor;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.MedicalWritingsVO;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.field.vo.MedicalFieldVO;
import com.xczhihui.medical.hospital.vo.MedicalHospitalVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuxin
 * @since 2017-12-09
 */
public interface IMedicalDoctorBusinessService {

    /**
     * Description：获取医师分页信息
     * creed: Talk is cheap,show me the code
     * @author name：yuxin <br>email: yuruixin@ixincheng.com
     * @Date: 下午 2:02 2017/12/10 0010
     **/
    public Page<MedicalDoctorVO> selectDoctorPage(Page<MedicalDoctorVO> page, Integer type, String hospitalId, String name, String field);

    public MedicalDoctorVO selectDoctorById(String id);

    List<MedicalFieldVO> getHotField();

    List<MedicalDoctorVO> selectRecDoctor();

    List<OeBxsArticleVO> getNewsReports(String doctorId);

    OeBxsArticleVO  getNewsReportByArticleId(String doctorId);

    Page<OeBxsArticleVO> getSpecialColumns(Page<OeBxsArticleVO> page, String doctorId);

    OeBxsArticleVO getSpecialColumnDetailsById(String articleId);

    List<MedicalWritingsVO> getWritingsByDoctorId(String doctorId);

    MedicalWritingsVO getWritingsDetailsById(String writingsId);

    List<MedicalWritingsVO> getRecentlyWritings();

    List<OeBxsArticleVO> getRecentlyNewsReports();

    Page<OeBxsArticleVO> getNewsReportsByPage(Page<OeBxsArticleVO> page, String doctorId);

    List<OeBxsArticleVO> getHotSpecialColumn();

    List<MedicalDoctorVO> getHotSpecialColumnAuthor();

    Page<MedicalWritingsVO> getWritingsByPage(Page<MedicalWritingsVO> page);

    /**
     * 加入医馆
     * @author zhuwenbao
     * @param medicalDoctor 加入医馆提交的信息
     */
    void joinHospital(MedicalDoctor medicalDoctor);

    /**
     * 获取医师的坐诊时间
     * @param userId 医师id
     * @param type 坐诊时间的类型
     * @author zhuwenbao
     */
    String getWorkTimeById(String userId, Integer type);

    /**
     * 修改医师信息
     * @param uid 修改人id
     * @param doctor 修改的内容
     * @author zhuwenbao
     */
    void update(String uid, MedicalDoctor doctor);

    /**
     * 根据doctorId获取医师详情
     * @author zhuwenbao
     */
    MedicalDoctor selectDoctorByIdV2(String doctorId);

    /**
     * 添加医师
     * @author zhuwenbao
     */
    void add(MedicalDoctor medicalDoctor);

    /**
     * 根据用户id获取其所在的医馆信息
     */
    MedicalHospitalVo getHospital(String uid);
}
