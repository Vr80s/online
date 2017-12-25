package com.xczhihui.medical.doctor.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVO;
import com.xczhihui.medical.doctor.vo.MedicalWritingsVO;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVO;
import com.xczhihui.medical.field.vo.MedicalFieldVO;

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
}
