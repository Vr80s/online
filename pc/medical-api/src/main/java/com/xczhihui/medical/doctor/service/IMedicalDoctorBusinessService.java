package com.xczhihui.medical.doctor.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.xczhihui.medical.doctor.vo.MedicalDoctorVo;
import com.xczhihui.medical.doctor.vo.OeBxsArticleVo;
import com.xczhihui.medical.field.vo.MedicalFieldVo;

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
    public Page<MedicalDoctorVo> selectDoctorPage(Page<MedicalDoctorVo> page, Integer type, String hospitalId, String name, String field);

    public MedicalDoctorVo selectDoctorById(String id);

    List<MedicalFieldVo> getHotField();

    List<MedicalDoctorVo> selectRecDoctor();

    List<OeBxsArticleVo> getNewsReports(String doctorId);

    OeBxsArticleVo  getNewsReportByArticleId(String doctorId);

    List<OeBxsArticleVo> getSpecialColumnByDoctorId(String doctorId);

    OeBxsArticleVo getSpecialColumnDetailsById(String articleId);
}
